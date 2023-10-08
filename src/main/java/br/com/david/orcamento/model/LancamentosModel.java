package br.com.david.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "lancamentos")
public class LancamentosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lancamentoInvalido", nullable = false)
    private Byte lancamentoInvalido;

    @Column(name = "numeroLancamento", nullable = false)
    private Integer numeroLancamento;

    @Column(name = "idLancamento", nullable = false)
    private Integer idLancamento;

    @Column(name = "dataLancamento", nullable = false)
    private Date dataLancamento;

    @Column(name = "idLancamentoPai", nullable = false)
    private Integer idLancamentoPai;

    @Column(name = "idUnidade", nullable = false)
    private Integer idUnidade;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "idUnidadeOrcamento", nullable = false)
    private Integer idUnidadeOrcamento;

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

    @Column(name = "idSolicitante", nullable = false)
    private Integer idSolicitante;

    @Column(name = "ged", nullable = false, length = 27)
    private char ged;

    @Column(name = "contrato", nullable = false, length = 255)
    private String contrato;

    @Column(name = "idObjetivoEstrategico", nullable = false)
    private Integer idObjetivoEstrategico;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "idTipoTransacao", nullable = false)
    private Integer idTipoTransacao;

    @Column(name = "dataCadastro", nullable = false)
    private Date dataCadastro;

    @Column(name = "dataAlteracao", nullable = false)
    private Date dataAlteracao;

    @Column(name = "anoOrcamento", nullable = false)
    private Integer anoOrcamento;
}
