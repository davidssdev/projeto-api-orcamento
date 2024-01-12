package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.FonteRecursoModel;
import br.com.david.orcamento.rest.dto.FonteRecursoDTo;
import br.com.david.orcamento.rest.form.FonteRecursoForm;
import br.com.david.orcamento.service.FonteRecursoService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/fonte-recurso")
public class FonteRecursoController {

    @Autowired
    FonteRecursoService fonteRecursoService;

    @GetMapping
    public ResponseEntity<List<FonteRecursoDTo>> findAll(){
        List<FonteRecursoDTo> fonteList = fonteRecursoService.findAllFonte();
        return ResponseEntity.ok().body(fonteList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FonteRecursoDTo> find(@PathVariable("id") Integer id){
        FonteRecursoDTo fonte = fonteRecursoService.findByIdFonte(id);
        return ResponseEntity.ok().body(fonte);
    }

    @PostMapping
    public ResponseEntity<FonteRecursoDTo> insert(@Valid @RequestBody FonteRecursoForm fonteRecursoForm, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            FonteRecursoDTo novaFonte = fonteRecursoService.insertFonte(fonteRecursoForm);
            return ResponseEntity.ok().body(novaFonte);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FonteRecursoDTo> update(@Valid @RequestBody FonteRecursoForm fonteRecursoForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            FonteRecursoDTo updateFonte = fonteRecursoService.updateFonte(fonteRecursoForm, id);
            return ResponseEntity.ok().body(updateFonte);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        fonteRecursoService.deleteFonte(id);
        return ResponseEntity.noContent().build();
    }
}
