package com.exemplo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Double preco;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Agendamento> agendamentos = new ArrayList<>();
}