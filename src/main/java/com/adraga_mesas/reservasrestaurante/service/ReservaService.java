package com.adraga_mesas.reservasrestaurante.service;

import com.adraga_mesas.reservasrestaurante.config.RestaurantConfig;
import com.adraga_mesas.reservasrestaurante.model.*;
import com.adraga_mesas.reservasrestaurante.repository.MesaRepository;
import com.adraga_mesas.reservasrestaurante.repository.ReservaRepository;
import com.adraga_mesas.reservasrestaurante.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestaurantConfig restaurantConfig;

    @Transactional
    public Reserva criarReserva(Long usuarioId, Long mesaId, LocalDateTime datahora) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + usuarioId));
        
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new IllegalArgumentException("Mesa não encontrada: " + mesaId));

        validarReserva(mesa, datahora);

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setMesa(mesa);
        reserva.setDatahora(datahora);
        reserva.setStatus(StatusReserva.ATIVA);

        mesa.setStatus(StatusMesa.RESERVADA);
        mesaRepository.save(mesa);

        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva cancelarReserva(Long reservaId, Long usuarioId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada: " + reservaId));

        if (!reserva.getUsuario().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("Apenas o usuário que fez a reserva pode cancelá-la");
        }

        if (reserva.getStatus() != StatusReserva.ATIVA) {
            throw new IllegalArgumentException("Apenas reservas ativas podem ser canceladas");
        }

        reserva.setStatus(StatusReserva.CANCELADA);
        reserva.getMesa().setStatus(StatusMesa.DISPONIVEL);
        mesaRepository.save(reserva.getMesa());

        return reservaRepository.save(reserva);
    }

    public List<Reserva> listarReservasUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new IllegalArgumentException("Usuário não encontrado: " + usuarioId);
        }
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public List<Reserva> listarReservasMesa(Long mesaId) {
        if (!mesaRepository.existsById(mesaId)) {
            throw new IllegalArgumentException("Mesa não encontrada: " + mesaId);
        }
        return reservaRepository.findByMesaId(mesaId);
    }

    private void validarReserva(Mesa mesa, LocalDateTime datahora) {
        // Validar horário de funcionamento
        LocalTime horarioReserva = datahora.toLocalTime();
        if (horarioReserva.isBefore(restaurantConfig.getOpeningTime()) || 
            horarioReserva.isAfter(restaurantConfig.getClosingTime())) {
            throw new IllegalArgumentException("Horário fora do funcionamento do restaurante");
        }

        // Validar antecedência mínima
        LocalDateTime agora = LocalDateTime.now();
        if (datahora.isBefore(agora.plusHours(restaurantConfig.getMinReservationNoticeHours()))) {
            throw new IllegalArgumentException("Reserva deve ser feita com pelo menos " + 
                restaurantConfig.getMinReservationNoticeHours() + " hora(s) de antecedência");
        }

        // Validar disponibilidade da mesa
        if (mesa.getStatus() != StatusMesa.DISPONIVEL) {
            throw new IllegalArgumentException("Mesa não está disponível");
        }

        // Validar sobreposição de reservas
        LocalDateTime fimReserva = datahora.plusHours(restaurantConfig.getMaxReservationDurationHours());
        List<Reserva> reservasExistentes = reservaRepository.findByMesaAndStatusAndDatahoraBetween(
            mesa, StatusReserva.ATIVA, datahora, fimReserva);
        
        if (!reservasExistentes.isEmpty()) {
            throw new IllegalArgumentException("Já existe uma reserva para este horário");
        }
    }
}


