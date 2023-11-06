package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.UnidadeModel;
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
    public ResponseEntity<List<UnidadeModel>> findAll(){
        List<UnidadeModel> unidadeList = unidadeService.findAllUnidade();
        return ResponseEntity.ok().body(unidadeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeModel> find(@PathVariable("id") Integer id){
        UnidadeModel unidade = unidadeService.findByIdUnidade(id);
        return ResponseEntity.ok().body(unidade);
    }

    @PostMapping
    public ResponseEntity<UnidadeModel> insert(@Valid @RequestBody UnidadeForm unidadeForm, BindingResult br){

        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            UnidadeModel novaUnidade = unidadeService.insertUnidade(unidadeForm);
            return ResponseEntity.ok().body(novaUnidade);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeModel> update(@Valid @RequestBody UnidadeForm unidadeForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            UnidadeModel unidadeUpdate = unidadeService.updateUnidade(unidadeForm, id);
            return  ResponseEntity.ok().body(unidadeUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        unidadeService.deleteUnidade(id);
        return ResponseEntity.noContent().build();
    }
}
