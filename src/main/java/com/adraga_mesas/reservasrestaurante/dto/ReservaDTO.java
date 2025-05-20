package com.adraga_mesas.reservasrestaurante.dto;

import com.adraga_mesas.reservasrestaurante.model.StatusReserva;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaDTO {
    private Long id;
    private Long usuarioId;
    private Long mesaId;
    private LocalDateTime datahora;
    private StatusReserva status;
    private String numeroMesa;
    private int lugaresMesa;
} 