package br.com.david.orcamento.rest.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
public class LancamentoForm {

    @NotNull(message = "O campo Lancamento Inválido não pode ser nulo!")
    private Byte lancamentoInvalido;

    private Integer numeroLancamento;

    @NotNull(message = "O campo Tipo Lancamento não pode ser nulo!")
    private Integer idTipoLancamento;

    private Integer idLancamentoPai;

    @NotNull(message = "O campo Unidade não pode ser nulo!")
    private Integer idUnidade;

    @NotEmpty
    @NotBlank(message = "A Descrição não pode ser vazia!")
    @Size(max = 600)
    private String descricao;

    @NotNull(message = "O campo Unidade Orcamentaria não pode ser vazio!")
    private Integer idUnidadeOrcamentaria;

    @NotNull(message = "O Programa não pode ser nulo")
    private Integer idPrograma;

    @NotNull(message = "O campo Ação não pode ser vazio!")
    private Integer idAcao;

    @NotNull(message = "O campo Fonte de recurso não pode ser vazio!")
    private Integer idFonteRecurso;

    @NotNull(message = "O campo Grupo Despesa não pode ser vazio!")
    private Integer idGrupoDespesa;

    @NotNull(message = "O campo Modalidade Aplicação não pode ser vazio!")
    private Integer idModalidadeAplicacao;

    @NotNull(message = "O campo Elemento Despesa não pode ser vazio!")
    private Integer idElementoDespesa;

    @NotNull(message = "O campo Solicitante não pode ser vazio!")
    private Integer idSolicitante;

    @Size(max = 27)// Verificar se a validação vai dar erro;
    private String ged;

    @Size(max = 600)
    private String contratado;

    private Integer idObjetivoEstrategico;

    @NotNull
    private Double valor;

    @NotNull(message = "O Tipo de Transação não pode ser vazio!")
    private Integer idTipoTransacao;

    @NotNull(message = "O Ano do Orcamento não pode ser nulo")
    private Integer anoOrcamento;

    private LocalDate dataCadastro;

    private LocalDate dataAlteracao;
    //Data do lancamento esta igual a dataCadastro, aguardando regra para modificação
    private LocalDate dataLancamento;
}
