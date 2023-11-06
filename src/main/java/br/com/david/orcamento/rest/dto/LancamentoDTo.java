package br.com.david.orcamento.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoDTo {

    private Integer lancamento_id;

    private Integer lacamento_invalido;

    private Integer numero_lancamento_id;

    private Integer tipo_lancamento_id;

    private Integer lancamento_pai_id;

    private Integer unidade_id;

    private String descricao;

    private Integer unidade_orcamentaria_id;

    private Integer programa_id;

    private Integer acao_id;

    private Integer fonte_recurso_id;

    private Integer grupo_despesa_id;

    private Integer modalidade_aplicacao_id;

    private Integer elemento_despesa_id;

    private Integer solicitante_id;

    private String ged;

    private String contratado;

    private Integer objetivo_estrategico_id;

    private Double valor;

    private Integer tipo_transacao_id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dt_cadastro;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dt_alteracao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dt_lancamento;

    private Integer orcamento_ano;
}
