package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.UnidadeModel;
import br.com.david.orcamento.model.UnidadeOrcamentariaModel;
import br.com.david.orcamento.repository.UnidadeOrcamentoRepository;
import br.com.david.orcamento.rest.form.UnidadeForm;
import br.com.david.orcamento.rest.form.UnidadeOrcamentariaForm;
import br.com.david.orcamento.service.UnidadeOrcamentariaService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/unidade-orcamentaria")
public class UnidadeOrcamentariaController {

    @Autowired
    UnidadeOrcamentariaService unidadeOrcamentariaService;

    @GetMapping
    public ResponseEntity<List<UnidadeOrcamentariaModel>> findAll(){
        List<UnidadeOrcamentariaModel> unidadeOrcamentariaList = unidadeOrcamentariaService.findAllUnidadeOrcamentaria();
        return ResponseEntity.ok().body(unidadeOrcamentariaList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeOrcamentariaModel> find(@PathVariable("id") Integer id){
        UnidadeOrcamentariaModel unidadeOrcamentaria = unidadeOrcamentariaService.findByIdUnidadeOrcamentaria(id);
        return ResponseEntity.ok().body(unidadeOrcamentaria);
    }

    @PostMapping
    public ResponseEntity<UnidadeOrcamentariaModel> insert(@Valid @RequestBody UnidadeOrcamentariaForm unidadeOrcamentariaForm, BindingResult br){

        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            UnidadeOrcamentariaModel novaUnidadeOrcamentaria = unidadeOrcamentariaService.insertUnidadeOrcamentaria(unidadeOrcamentariaForm);
            return ResponseEntity.ok().body(novaUnidadeOrcamentaria);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeOrcamentariaModel> update(@Valid @RequestBody UnidadeOrcamentariaForm unidadeOrcamentariaForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            UnidadeOrcamentariaModel unidadeOrcamentariaUpdate = unidadeOrcamentariaService.updateUndOrcamentaria(unidadeOrcamentariaForm, id);
            return  ResponseEntity.ok().body(unidadeOrcamentariaUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        unidadeOrcamentariaService.deleteUndOrcamentaria(id);
        return ResponseEntity.noContent().build();
    }
}
