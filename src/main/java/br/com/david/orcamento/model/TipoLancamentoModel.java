package br.com.david.orcamento.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.DateTimeException;
import java.time.LocalDate;
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

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "dataCadastro", nullable = false)
    private LocalDate dataCadastro;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "dataAlteracao", nullable = true)
    private LocalDate dataAlteracao;
}
