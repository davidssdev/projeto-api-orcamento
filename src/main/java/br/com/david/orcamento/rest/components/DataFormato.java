package br.com.david.orcamento.rest.components;

import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@Data
public class DataFormato {

    public LocalDateTime formatarData(LocalDateTime localDateTime){
        try {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = localDateTime.format(formatador);
            LocalDateTime dataFormatOk = LocalDateTime.parse(dataFormatada, formatador);

            return dataFormatOk;
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Erro ao tentar a conversão");
        }
    }
}
