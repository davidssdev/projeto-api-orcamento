package br.com.david.orcamento.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "lancamentos")
public class LancamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lacamento_invalido", nullable = false)
    private Integer lacamento_invalido;

    @Column(name = "numero_lancamento", nullable = true)
    private Integer numero_lancamento;

    @Column(name = "id_tipo_lancamento", nullable = false)
    private Integer id_tipo_lancamento;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "data_lancamento", nullable = false)
    private LocalDateTime data_lancamento;

    @Column(name = "id_lancamento_pai", nullable = true)
    private Integer id_lancamento_pai;

    @Column(name = "id_unidade", nullable = false)
    private Integer id_unidade;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "id_unidade_orcamentaria", nullable = false)
    private Integer id_unidade_orcamentaria;

    @Column(name = "id_programa", nullable = false)
    private Integer id_programa;

    @Column(name = "id_acao", nullable = false)
    private Integer id_acao;

    @Column(name = "id_fonte_recurso", nullable = false)
    private Integer id_fonte_recurso;

    @Column(name = "id_grupo_despesa", nullable = false)
    private Integer id_grupo_despesa;

    @Column(name = "id_modalidade_aplicacao", nullable = false)
    private Integer id_modalidade_aplicacao;

    @Column(name = "id_elemento_despesa", nullable = false)
    private Integer id_elemento_despesa;

    @Column(name = "id_solicitante", nullable = true)
    private Integer id_solicitante;

    @Column(name = "ged", nullable = true, length = 27)
    private String ged;

    @Column(name = "contratado", nullable = false, length = 255)
    private String contratado;

    @Column(name = "id_objetivo_estrategico", nullable = true)
    private Integer id_objetivo_estrategico;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "id_tipo_transacao", nullable = false)
    private Integer id_tipo_transacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime data_cadastro;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "data_alteracao", nullable = true)
    private LocalDateTime data_alteracao;

    @Column(name = "ano_orcamento", nullable = false)
    private Integer ano_orcamento;
}
