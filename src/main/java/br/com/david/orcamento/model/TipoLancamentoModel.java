package br.com.david.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Table(name="tipoLancamento")
public class TipoLancamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "dataCadastro", nullable = false)
    private Date dataCadastro;

    @Column(name = "dataAlteracao", nullable = true)
    private Date dataAlteracao;
}
