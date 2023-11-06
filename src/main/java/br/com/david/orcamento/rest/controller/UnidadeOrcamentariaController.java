package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.UnidadeOrcamentariaModel;
import br.com.david.orcamento.rest.dto.UnidadeOrcamentariaDTo;
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
    public ResponseEntity<List<UnidadeOrcamentariaDTo>> findAll(){
        List<UnidadeOrcamentariaDTo> unidadeOrcamentariaListDTo = unidadeOrcamentariaService.findAllUnidadeOrcamentaria();
        return ResponseEntity.ok().body(unidadeOrcamentariaListDTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeOrcamentariaDTo> find(@PathVariable("id") Integer id){
        UnidadeOrcamentariaDTo unidadeOrcamentariaDTo = unidadeOrcamentariaService.findByIdUnidadeOrcamentaria(id);
        return ResponseEntity.ok().body(unidadeOrcamentariaDTo);
    }

    @PostMapping
    public ResponseEntity<UnidadeOrcamentariaDTo> insert(@Valid @RequestBody UnidadeOrcamentariaForm unidadeOrcamentariaForm, BindingResult br){

        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            UnidadeOrcamentariaDTo novaUnidadeOrcamentariaDTo = unidadeOrcamentariaService.insertUnidadeOrcamentaria(unidadeOrcamentariaForm);
            return ResponseEntity.ok().body(novaUnidadeOrcamentariaDTo);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeOrcamentariaDTo> update(@Valid @RequestBody UnidadeOrcamentariaForm unidadeOrcamentariaForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            UnidadeOrcamentariaDTo unidadeOrcamentariaUpdateDTo = unidadeOrcamentariaService.updateUndOrcamentaria(unidadeOrcamentariaForm, id);
            return  ResponseEntity.ok().body(unidadeOrcamentariaUpdateDTo);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        unidadeOrcamentariaService.deleteUndOrcamentaria(id);
        return ResponseEntity.noContent().build();
    }
}
