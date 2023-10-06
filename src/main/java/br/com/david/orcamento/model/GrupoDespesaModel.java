package br.com.david.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "GrupoDespesa")
public class GrupoDespesaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "Codigo", nullable = false)
    private double Codigo;

    @Column(name = "Nome", nullable = false)
    private String Nome;

    @Column(name = "DataCadastro", nullable = false)
    private Date DataCadastro;

    @Column(name = "DataAlteracao", nullable = true)
    private Date DataAlteracao;
}
