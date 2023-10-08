package br.com.david.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "elementoDespesa")
public class ElementoDespesaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "dataCadastro", nullable = false)
    private Date dataCadastro;

    @Column(name = "dataAlteracao", nullable = true)
    private Date dataAlteracao;
}
