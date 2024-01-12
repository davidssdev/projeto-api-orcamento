package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.GrupoDespesaModel;
import br.com.david.orcamento.rest.dto.GrupoDespesaDTo;
import br.com.david.orcamento.rest.form.GrupoDespesaForm;
import br.com.david.orcamento.service.GrupoDespesaService;
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
@RequestMapping("/grupo-despesa")
public class GrupoDespesaController {

    @Autowired
    GrupoDespesaService grupoDespesaService;

    @GetMapping
    public ResponseEntity<List<GrupoDespesaDTo>> findAll(){
        List<GrupoDespesaDTo> grupoList = grupoDespesaService.findAllGrupo();
        return ResponseEntity.ok().body(grupoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoDespesaDTo> find(@PathVariable("id") Integer id){
        GrupoDespesaDTo grupo = grupoDespesaService.findByIdGrupo(id);
        return ResponseEntity.ok().body(grupo);
    }

    @PostMapping
    public ResponseEntity<GrupoDespesaDTo> insert(@Valid @RequestBody GrupoDespesaForm grupoDespesaForm, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            GrupoDespesaDTo novoGrupo = grupoDespesaService.insertGrupo(grupoDespesaForm);
            return ResponseEntity.ok().body(novoGrupo);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoDespesaDTo> update(@Valid @RequestBody GrupoDespesaForm grupoDespesaForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else{
            GrupoDespesaDTo grupoUpdate = grupoDespesaService.updateGrupo(grupoDespesaForm, id);
            return ResponseEntity.ok().body(grupoUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        grupoDespesaService.deleteGrupo(id);
        return ResponseEntity.noContent().build();
    }

}
