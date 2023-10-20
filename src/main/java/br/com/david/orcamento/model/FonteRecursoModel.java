package br.com.david.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "fonteRecurso")
public class FonteRecursoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "dataCadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "dataAlteracao", nullable = true)
    private LocalDate dataAlteracao;
}
