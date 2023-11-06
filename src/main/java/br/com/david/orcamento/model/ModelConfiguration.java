package br.com.david.orcamento.model;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ModelConfiguration {

    @Bean
    public ModelMapper getModel(){
        return  new ModelMapper();
    }
}
