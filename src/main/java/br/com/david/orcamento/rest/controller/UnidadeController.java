package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.UnidadeModel;
import br.com.david.orcamento.rest.dto.UnidadeDTo;
import br.com.david.orcamento.rest.form.UnidadeForm;
import br.com.david.orcamento.service.UnidadeService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/unidade")
public class UnidadeController {

    @Autowired
    UnidadeService unidadeService;

    @GetMapping
    public ResponseEntity<List<UnidadeDTo>> findAll(){
        List<UnidadeDTo> unidadeListDto = unidadeService.findAllUnidade();
        return ResponseEntity.ok().body(unidadeListDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeDTo> find(@PathVariable("id") Integer id){
        UnidadeDTo unidadeDto = unidadeService.findByIdUnidade(id);
        return ResponseEntity.ok().body(unidadeDto);
    }

    @PostMapping
    public ResponseEntity<UnidadeDTo> insert(@Valid @RequestBody UnidadeForm unidadeForm, BindingResult br){

        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            UnidadeDTo novaUnidadeDto = unidadeService.insertUnidade(unidadeForm);
            return ResponseEntity.ok().body(novaUnidadeDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeDTo> update(@Valid @RequestBody UnidadeForm unidadeForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            UnidadeDTo unidadeUpdateDto = unidadeService.updateUnidade(unidadeForm, id);
            return  ResponseEntity.ok().body(unidadeUpdateDto);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        unidadeService.deleteUnidade(id);
        return ResponseEntity.noContent().build();
    }
}
