package br.com.david.orcamento.rest.controller;


import br.com.david.orcamento.model.SolicitanteModel;
import br.com.david.orcamento.rest.form.SolicitanteForm;
import br.com.david.orcamento.service.SolicitanteService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/solicitante")
public class SolicitanteController {

    @Autowired
    SolicitanteService solicitanteService;

    @GetMapping
    public ResponseEntity<List<SolicitanteModel>> findAll(){
        List<SolicitanteModel> solicitanteList = solicitanteService.findAllSolicitante();
        return ResponseEntity.ok().body(solicitanteList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitanteModel> find(@PathVariable("id") Integer id){
        SolicitanteModel solicitante = solicitanteService.findByIdSolicitante(id);
        return ResponseEntity.ok().body(solicitante);
    }

    @PostMapping
    public ResponseEntity<SolicitanteModel> insert(@Valid @RequestBody SolicitanteForm solicitanteForm, BindingResult br){

        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            SolicitanteModel novoSolicitante = solicitanteService.insertSolicitante(solicitanteForm);
            return ResponseEntity.ok().body(novoSolicitante);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitanteModel> update(@Valid @RequestBody SolicitanteForm solicitanteForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            SolicitanteModel solicitanteUpdate = solicitanteService.updateSolicitante(solicitanteForm,id);
            return  ResponseEntity.ok().body(solicitanteUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        solicitanteService.deleteSolicitante(id);
        return ResponseEntity.noContent().build();
    }
}
