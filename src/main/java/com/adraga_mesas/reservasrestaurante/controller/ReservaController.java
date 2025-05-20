package com.adraga_mesas.reservasrestaurante.controller;

import com.adraga_mesas.reservasrestaurante.dto.ReservaDTO;
import com.adraga_mesas.reservasrestaurante.model.Reserva;
import com.adraga_mesas.reservasrestaurante.model.Usuario;
import com.adraga_mesas.reservasrestaurante.service.ReservaService;
import com.adraga_mesas.reservasrestaurante.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public String testprotectedRoute () {
        return "This is a protected route!";
    }
    
    @PostMapping
    public ResponseEntity<ReservaDTO> criarReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        // Get current user from security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) usuarioService.loadUserByUsername(auth.getName());
        
        Reserva reserva = reservaService.criarReserva(
            usuario.getId(),
            reservaDTO.getMesaId(),
            reservaDTO.getDatahora()
        );
        return ResponseEntity.ok(converterParaDTO(reserva));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaDTO>> listarReservasUsuario(@PathVariable Long usuarioId) {
        List<Reserva> reservas = reservaService.listarReservasUsuario(usuarioId);
        List<ReservaDTO> reservasDTO = reservas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservasDTO);
    }

    @GetMapping("/mesa/{mesaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservaDTO>> listarReservasMesa(@PathVariable Long mesaId) {
        List<Reserva> reservas = reservaService.listarReservasMesa(mesaId);
        List<ReservaDTO> reservasDTO = reservas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservasDTO);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaDTO> cancelarReserva(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {
        Reserva reserva = reservaService.cancelarReserva(id, usuarioId);
        return ResponseEntity.ok(converterParaDTO(reserva));
    }

    private ReservaDTO converterParaDTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setUsuarioId(reserva.getUsuario().getId());
        dto.setMesaId(reserva.getMesa().getId());
        dto.setDatahora(reserva.getDatahora());
        dto.setStatus(reserva.getStatus());
        dto.setNumeroMesa(String.valueOf(reserva.getMesa().getNumero()));
        dto.setLugaresMesa(reserva.getMesa().getLugares());
        return dto;
    }
}
