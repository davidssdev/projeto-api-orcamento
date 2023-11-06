package br.com.david.orcamento.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "objetivo_estrategico")
public class ObjetivoEstrategicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime data_cadastro;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_alteracao", nullable = true)
    private LocalDateTime data_alteracao;
}
