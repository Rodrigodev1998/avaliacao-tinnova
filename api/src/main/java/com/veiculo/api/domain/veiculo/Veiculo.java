package com.veiculo.api.domain.veiculo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name="veiculos")
@Table(name = "veiculos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String veiculo;
    @Enumerated(EnumType.STRING)
    private Marca marca;
    private String cor;
    private Integer ano;
    private String descricao;
    private boolean vendido;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Veiculo(RequestVeiculo requestVeiculo){
        this.veiculo = requestVeiculo.veiculo();
        this.marca = requestVeiculo.marca();
        this.ano = requestVeiculo.ano();
        this.cor = requestVeiculo.cor();
        this.descricao = requestVeiculo.descricao();
        this.vendido = requestVeiculo.vendido();
        this.created = requestVeiculo.created();
        this.updated = requestVeiculo.update();
    }
}
