package br.com.david.orcamento.rest.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ObjetivosEstrategicosForm {

    @NotEmpty
    @NotBlank(message = "O nome não pode ser em branco!")
    @Size(max = 600)
    private String nome;
}
