package com.adraga_mesas.reservasrestaurante.repository;

import com.adraga_mesas.reservasrestaurante.model.Mesa;
import com.adraga_mesas.reservasrestaurante.model.Reserva;
import com.adraga_mesas.reservasrestaurante.model.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByMesaAndStatusAndDatahoraBetween(
        Mesa mesa, 
        StatusReserva status, 
        LocalDateTime inicio, 
        LocalDateTime fim
    );

    List<Reserva> findByUsuarioId(Long usuarioId);
    
    List<Reserva> findByMesaId(Long mesaId);
}
    

