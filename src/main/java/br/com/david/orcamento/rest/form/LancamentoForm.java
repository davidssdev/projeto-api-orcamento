package br.com.david.orcamento.rest.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class LancamentoForm {

    @NotNull(message = "O campo Lancamento Inválido não pode ser nulo!")
    private Integer lacamento_invalido;

    private Integer numero_lancamento;

    @NotNull(message = "O campo Tipo Lancamento não pode ser nulo!")
    private Integer id_tipo_lancamento;

    private Integer id_lancamento_pai;

    @NotNull(message = "O campo Unidade não pode ser nulo!")
    private Integer id_unidade;

    @NotEmpty
    @NotBlank(message = "A Descrição não pode ser vazia!")
    @Size(max = 600)
    private String descricao;

    @NotNull(message = "O campo Unidade Orcamentaria não pode ser vazio!")
    private Integer id_unidade_orcamentaria;

    @NotNull(message = "O Programa não pode ser nulo")
    private Integer id_programa;

    @NotNull(message = "O campo Ação não pode ser vazio!")
    private Integer id_acao;

    @NotNull(message = "O campo Fonte de recurso não pode ser vazio!")
    private Integer id_fonte_recurso;

    @NotNull(message = "O campo Grupo Despesa não pode ser vazio!")
    private Integer id_grupo_despesa;

    @NotNull(message = "O campo Modalidade Aplicação não pode ser vazio!")
    private Integer id_modalidade_aplicacao;

    @NotNull(message = "O campo Elemento Despesa não pode ser vazio!")
    private Integer id_elemento_despesa;

    @NotNull(message = "O campo Solicitante não pode ser vazio!")
    private Integer id_solicitante;

    @Size(max = 27)// Verificar se a validação vai dar erro;
    private String ged;

    @Size(max = 600)
    private String contratado;

    private Integer id_objetivo_estrategico;

    @NotNull
    private Double valor;

    @NotNull(message = "O Tipo de Transação não pode ser vazio!")
    private Integer id_tipo_transacao;

    @NotNull(message = "O Ano do Orcamento não pode ser nulo")
    private Integer ano_orcamento;

    private LocalDateTime data_cadastro;

    private LocalDateTime data_alteracao;
    //Data do lancamento esta igual a dataCadastro, aguardando regra para modificação
    private LocalDateTime data_lancamento;
}
