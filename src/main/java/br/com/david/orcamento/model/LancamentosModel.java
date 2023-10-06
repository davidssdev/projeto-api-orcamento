package br.com.david.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "Lancamentos")
public class LancamentosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "LancamentoInvalido", nullable = false)
    private byte LancamentoInvalido;

    @Column(name = "NumeroLancamento", nullable = false)
    private int NumeroLancamento;

    @Column(name = "IDLancamento", nullable = false)
    private int IDLancamento;

    @Column(name = "DataLancamento", nullable = false)
    private Date DataLancamento;

    @Column(name = "IDLancamentoPai", nullable = false)
    private int IDLancamentoPai;

    @Column(name = "IDUnidade", nullable = false)
    private int IDUnidade;

    @Column(name = "Descricao", nullable = false, length = 255)
    private String Descricao;

    @Column(name = "IDUnidadeOrcamento", nullable = false)
    private int IDUnidadeOrcamento;

    @Column(name = "IDPrograma", nullable = false)
    private int IDPrograma;

    @Column(name = "IDAcao", nullable = false)
    private int IDAcao;

    @Column(name = "IDFonteRecurso", nullable = false)
    private int IDFonteRecurso;

    @Column(name = "IDGrupoDespesa", nullable = false)
    private int IDGrupoDespesa;

    @Column(name = "IDModalidadeAplicacao", nullable = false)
    private int IDModalidadeAplicacao;

    @Column(name = "IDElementoDespesa", nullable = false)
    private int IDElementoDespesa;

    @Column(name = "IDSolicitante", nullable = false)
    private int IDSolicitante;

    @Column(name = "GED", nullable = false, length = 27)
    private char GED;

    @Column(name = "Contrato", nullable = false, length = 255)
    private String Contrato;

    @Column(name = "IDObjetivoEstrategico", nullable = false)
    private int IDobjetivoEstrategico;

    @Column(name = "Valor", nullable = false)
    private double Valor;

    @Column(name = "IDTipoTransacao", nullable = false)
    private int IDTipoTransacao;

    @Column(name = "DataCadastro", nullable = false)
    private Date DataCadastro;

    @Column(name = "DataAlteracao", nullable = false)
    private Date DataAlteracao;

    @Column(name = "AnoOrcamento", nullable = false)
    private int AnoOrcamento;
}
