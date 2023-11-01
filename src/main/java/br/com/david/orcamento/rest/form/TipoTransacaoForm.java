package br.com.david.orcamento.rest.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class TipoTransacaoForm {

    @NotEmpty
    @NotBlank(message = "O nome não pode ser em branco!")
    @Size(max = 600)
    private String nome;

    private LocalDate dataCadastro;

    private LocalDate dataAlteracao;
}
