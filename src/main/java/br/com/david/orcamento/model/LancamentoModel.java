package br.com.david.orcamento.model;

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

    @Column(name = "dataLancamento", nullable = false)
    private LocalDate dataLancamento;

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

    @Column(name = "dataCadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "dataAlteracao", nullable = true)
    private LocalDate dataAlteracao;

    @Column(name = "anoOrcamento", nullable = false)
    private Integer  anoOrcamento;
}
