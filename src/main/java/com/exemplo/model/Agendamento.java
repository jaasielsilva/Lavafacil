package com.exemplo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;
    private String hora;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    @JsonBackReference
    private Servico servico;

    // Métodos utilitários
    public String getNomeCliente() {
        return cliente != null ? cliente.getNome() : "";
    }

    public String getTelefone() {
        return cliente != null ? cliente.getTelefone() : "";
    }

    public String getEmail() {
        return cliente != null ? cliente.getEmail() : "";
    }
}
