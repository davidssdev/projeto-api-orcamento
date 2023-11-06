package br.com.david.orcamento.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "lancamentos")
public class LancamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lancamentoInvalido", nullable = false)
    private Byte lancamentoInvalido;

    @Column(name = "numeroLancamento", nullable = true)
    private Integer numeroLancamento;

    @Column(name = "idLancamento", nullable = false)
    private Integer idTipoLancamento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_lancamento", nullable = false)
    private LocalDate data_lancamento;

    @Column(name = "idLancamentoPai", nullable = true)
    private Integer idLancamentoPai;

    @Column(name = "idUnidade", nullable = false)
    private Integer idUnidade;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "idUnidadeOrcamentaria", nullable = false)
    private Integer idUnidadeOrcamentaria;

    @Column(name = "idPrograma", nullable = false)
    private Integer idPrograma;

    @Column(name = "idAcao", nullable = false)
    private Integer idAcao;

    @Column(name = "idFonteRecurso", nullable = false)
    private Integer idFonteRecurso;

    @Column(name = "idGrupoDespesa", nullable = false)
    private Integer idGrupoDespesa;

    @Column(name = "idModalidadeAplicacao", nullable = false)
    private Integer idModalidadeAplicacao;

    @Column(name = "idElementoDespesa", nullable = false)
    private Integer idElementoDespesa;

    @Column(name = "idSolicitante", nullable = true)
    private Integer idSolicitante;

    @Column(name = "ged", nullable = true, length = 27)
    private String ged;

    @Column(name = "contrato", nullable = false, length = 255)
    private String contratado;

    @Column(name = "idObjetivoEstrategico", nullable = true)
    private Integer idObjetivoEstrategico;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "idTipoTransacao", nullable = false)
    private Integer idTipoTransacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate data_cadastro;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_alteracao", nullable = true)
    private LocalDate data_alteracao;

    @Column(name = "anoOrcamento", nullable = false)
    private Integer  anoOrcamento;
}
