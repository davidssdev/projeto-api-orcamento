package br.com.david.orcamento.rest.controller;


import br.com.david.orcamento.model.SolicitanteModel;
import br.com.david.orcamento.rest.dto.SolicitanteDTo;
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
    public ResponseEntity<List<SolicitanteDTo>> findAll(){
        List<SolicitanteDTo> solicitanteListDTo = solicitanteService.findAllSolicitante();
        return ResponseEntity.ok().body(solicitanteListDTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitanteDTo> find(@PathVariable("id") Integer id){
        SolicitanteDTo solicitanteDTo = solicitanteService.findByIdSolicitante(id);
        return ResponseEntity.ok().body(solicitanteDTo);
    }

    @PostMapping
    public ResponseEntity<SolicitanteDTo> insert(@Valid @RequestBody SolicitanteForm solicitanteForm, BindingResult br){

        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            SolicitanteDTo novoSolicitanteDTo = solicitanteService.insertSolicitante(solicitanteForm);
            return ResponseEntity.ok().body(novoSolicitanteDTo);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitanteDTo> update(@Valid @RequestBody SolicitanteForm solicitanteForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            SolicitanteDTo solicitanteUpdateDTo = solicitanteService.updateSolicitante(solicitanteForm,id);
            return  ResponseEntity.ok().body(solicitanteUpdateDTo);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        solicitanteService.deleteSolicitante(id);
        return ResponseEntity.noContent().build();
    }
}
