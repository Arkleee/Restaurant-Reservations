package com.adraga_mesas.reservasrestaurante.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data e hora são obrigatórios")
    @Future(message = "A data da reserva deve ser futura")
    private LocalDateTime datahora;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status é obrigatório")
    private StatusReserva status = StatusReserva.ATIVA;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull(message = "Usuário é obrigatório")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    @NotNull(message = "Mesa é obrigatória")
    private Mesa mesa;
}
