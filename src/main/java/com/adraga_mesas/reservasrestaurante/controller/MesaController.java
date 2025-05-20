package com.adraga_mesas.reservasrestaurante.controller;

import com.adraga_mesas.reservasrestaurante.dto.MesaDTO;
import com.adraga_mesas.reservasrestaurante.model.Mesa;
import com.adraga_mesas.reservasrestaurante.model.StatusMesa;
import com.adraga_mesas.reservasrestaurante.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public ResponseEntity<List<MesaDTO>> listarMesas() {
        List<Mesa> mesas = mesaService.listarTodasMesas();
        List<MesaDTO> mesasDTO = mesas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mesasDTO);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<MesaDTO>> listarMesasDisponiveis() {
        List<Mesa> mesas = mesaService.listarDisponiveis();
        List<MesaDTO> mesasDTO = mesas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mesasDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MesaDTO> criarMesa(@Valid @RequestBody MesaDTO mesaDTO) {
        Mesa mesa = converterParaEntidade(mesaDTO);
        Mesa mesaSalva = mesaService.criarMesa(mesa);
        return ResponseEntity.ok(converterParaDTO(mesaSalva));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MesaDTO> atualizarMesa(
            @PathVariable Long id,
            @Valid @RequestBody MesaDTO mesaDTO) {
        Mesa mesa = converterParaEntidade(mesaDTO);
        mesa.setId(id);
        Mesa mesaAtualizada = mesaService.atualizarMesa(mesa);
        return ResponseEntity.ok(converterParaDTO(mesaAtualizada));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<MesaDTO> atualizarStatusMesa(
            @PathVariable Long id,
            @RequestParam StatusMesa status) {
        Mesa mesaAtualizada = mesaService.atualizarStatus(id, status);
        return ResponseEntity.ok(converterParaDTO(mesaAtualizada));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMesa(@PathVariable Long id) {
        mesaService.deletarMesa(id);
        return ResponseEntity.noContent().build();
    }

    private MesaDTO converterParaDTO(Mesa mesa) {
        MesaDTO dto = new MesaDTO();
        dto.setId(mesa.getId());
        dto.setNumero(mesa.getNumero());
        dto.setLugares(mesa.getLugares());
        dto.setStatus(mesa.getStatus());
        return dto;
    }

    private Mesa converterParaEntidade(MesaDTO dto) {
        Mesa mesa = new Mesa();
        mesa.setNumero(dto.getNumero());
        mesa.setLugares(dto.getLugares());
        mesa.setStatus(dto.getStatus());
        return mesa;
    }
} 