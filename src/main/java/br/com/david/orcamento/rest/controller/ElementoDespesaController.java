package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.ElementoDespesaModel;
import br.com.david.orcamento.rest.dto.ElementoDespesaDTo;
import br.com.david.orcamento.rest.form.ElementoDespesaForm;
import br.com.david.orcamento.service.ElementoDespesaService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/elemento-despesa")
public class ElementoDespesaController {

    @Autowired
    ElementoDespesaService elementoDespesaService;

    @GetMapping
    public ResponseEntity<List<ElementoDespesaDTo>> findAll(){
        List<ElementoDespesaDTo> elementoList = elementoDespesaService.findAllElemento();
        return ResponseEntity.ok().body(elementoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElementoDespesaDTo> find(@PathVariable("id") Integer id){
        ElementoDespesaDTo elemento = elementoDespesaService.findByIdElemento(id);
        return ResponseEntity.ok().body(elemento);
    }

    @PostMapping
    public ResponseEntity<ElementoDespesaDTo> insert(@Valid @RequestBody ElementoDespesaForm elementoDespesaForm, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ElementoDespesaDTo novoElemento = elementoDespesaService.insertElemento(elementoDespesaForm);
            return ResponseEntity.ok().body(novoElemento);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ElementoDespesaDTo> update(@Valid @RequestBody ElementoDespesaForm elementoDespesaForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ElementoDespesaDTo updateElemento = elementoDespesaService.updateElemento(elementoDespesaForm, id);
            return ResponseEntity.ok().body(updateElemento);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        elementoDespesaService.deleteElemento(id);
        return ResponseEntity.noContent().build();
    }
}
