package com.exemplo.repository;

import com.exemplo.model.Veiculo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByClienteId(Long clienteId);

}
