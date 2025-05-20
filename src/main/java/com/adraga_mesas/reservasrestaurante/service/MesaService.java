package com.adraga_mesas.reservasrestaurante.service;

import com.adraga_mesas.reservasrestaurante.model.Mesa;
import com.adraga_mesas.reservasrestaurante.model.StatusMesa;
import com.adraga_mesas.reservasrestaurante.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    public List<Mesa> listarTodasMesas() {
        return mesaRepository.findAll();
    }

    public List<Mesa> listarDisponiveis() {
        return mesaRepository.findByStatus(StatusMesa.DISPONIVEL);
    }

    public Mesa buscarMesa(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));
    }

    @Transactional
    public Mesa criarMesa(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    @Transactional
    public Mesa atualizarMesa(Mesa mesa) {
        Mesa mesaExistente = buscarMesa(mesa.getId());
        mesaExistente.setNumero(mesa.getNumero());
        mesaExistente.setLugares(mesa.getLugares());
        mesaExistente.setStatus(mesa.getStatus());
        return mesaRepository.save(mesaExistente);
    }

    @Transactional
    public Mesa atualizarStatus(Long id, StatusMesa status) {
        Mesa mesa = buscarMesa(id);
        mesa.setStatus(status);
        return mesaRepository.save(mesa);
    }

    @Transactional
    public void deletarMesa(Long id) {
        mesaRepository.deleteById(id);
    }
} 