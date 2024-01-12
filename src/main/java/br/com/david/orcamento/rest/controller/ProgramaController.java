package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.ProgramaModel;
import br.com.david.orcamento.rest.dto.ProgramaDTo;
import br.com.david.orcamento.rest.form.ProgramaForm;
import br.com.david.orcamento.service.ProgramaService;
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
@RequestMapping("/programa")
public class ProgramaController {

    @Autowired
    ProgramaService programaService;

    @GetMapping
    public ResponseEntity<List<ProgramaDTo>> findAll(){
        List<ProgramaDTo> programaListDTo = programaService.findAllPrograma();
        return ResponseEntity.ok().body(programaListDTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaDTo> find(@PathVariable("id") Integer id){
        ProgramaDTo programaDTo = programaService.findByIdPrograma(id);
        return ResponseEntity.ok().body(programaDTo);
    }

    @PostMapping
    public ResponseEntity<ProgramaDTo> insert(@Valid @RequestBody ProgramaForm programaForm, BindingResult br){
        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            ProgramaDTo novoProgramaDTo = programaService.insertPrograma(programaForm);
            return ResponseEntity.ok().body(novoProgramaDTo);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramaDTo> update(@Valid @RequestBody ProgramaForm programaForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            ProgramaDTo programaUpdate = programaService.updatePrograma(programaForm,id);
            return  ResponseEntity.ok().body(programaUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        programaService.deletePrograma(id);
        return ResponseEntity.noContent().build();
    }
}
