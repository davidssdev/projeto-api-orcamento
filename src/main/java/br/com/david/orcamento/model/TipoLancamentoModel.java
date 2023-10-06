package br.com.david.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Table(name="TipoLancamento")
public class TipoLancamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "Nome", nullable = false)
    private String Nome;

    @Column(name = "DataCadastro", nullable = false)
    private Date DataCadastro;

    @Column(name = "DataAlteracao", nullable = true)
    private Date DataAlteracao;
}