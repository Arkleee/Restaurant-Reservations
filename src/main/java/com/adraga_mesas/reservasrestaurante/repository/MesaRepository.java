package com.adraga_mesas.reservasrestaurante.repository;

import com.adraga_mesas.reservasrestaurante.model.Mesa;
import com.adraga_mesas.reservasrestaurante.model.StatusMesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByStatus(StatusMesa status);
}
    

