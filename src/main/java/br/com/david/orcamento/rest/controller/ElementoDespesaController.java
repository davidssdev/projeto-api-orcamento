package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.ElementoDespesaModel;
import br.com.david.orcamento.model.ElementoDespesaModel;
import br.com.david.orcamento.rest.form.AcaoForm;
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
    public ResponseEntity<List<ElementoDespesaModel>> findAll(){
        List<ElementoDespesaModel> elementoList = elementoDespesaService.findAllElemento();
        return ResponseEntity.ok().body(elementoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElementoDespesaModel> find(@PathVariable("id") Integer id){
        ElementoDespesaModel elemento = elementoDespesaService.findByIdElemento(id);
        return ResponseEntity.ok().body(elemento);
    }

    @PostMapping
    public ResponseEntity<ElementoDespesaModel> insert(@Valid @RequestBody ElementoDespesaForm elementoDespesaForm, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ElementoDespesaModel novoElemento = elementoDespesaService.insertElemento(elementoDespesaForm);
            return ResponseEntity.ok().body(novoElemento);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ElementoDespesaModel> update(@Valid @RequestBody ElementoDespesaForm elementoDespesaForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ElementoDespesaModel updateElemento = elementoDespesaService.updateElemento(elementoDespesaForm, id);
            return ResponseEntity.ok().body(updateElemento);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        elementoDespesaService.deleteElemento(id);
        return ResponseEntity.noContent().build();
    }
}
