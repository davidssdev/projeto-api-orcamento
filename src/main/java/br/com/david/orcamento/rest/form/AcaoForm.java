package br.com.david.orcamento.rest.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class AcaoForm {

    @NotNull(message = "O código não pode ser nulo!")
    private Integer codigo;

    @NotEmpty
    @NotBlank(message = "O nome não pode ser em branco!")
    @Size(max = 600)
    private String nome;

    //Data de cadastro e atualização pré definida na API
    private LocalDate dataCadastro;

    private LocalDate dataAlteracao;
}
