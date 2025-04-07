package com.exemplo.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;

    // Cliente.java
@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Veiculo> veiculos = new ArrayList<>();

}