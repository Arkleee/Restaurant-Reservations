package com.adraga_mesas.reservasrestaurante.dto;

import com.adraga_mesas.reservasrestaurante.model.StatusMesa;
import lombok.Data;

@Data
public class MesaDTO {
    private Long id;
    private int numero;
    private int lugares;
    private StatusMesa status;
} 