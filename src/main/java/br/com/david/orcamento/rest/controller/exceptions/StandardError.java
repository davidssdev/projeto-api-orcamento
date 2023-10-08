package br.com.david.orcamento.rest.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {

    private static final long serialVersionUID = 1L;
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
