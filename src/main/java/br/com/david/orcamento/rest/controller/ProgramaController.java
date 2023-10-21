package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.ProgramaModel;
import br.com.david.orcamento.rest.form.ProgramaForm;
import br.com.david.orcamento.service.ProgramaService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transaction;
import javax.transaction.TransactionScoped;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/programa")
public class ProgramaController {

    @Autowired
    ProgramaService programaService;

    @GetMapping
    public ResponseEntity<List<ProgramaModel>> findAll(){
        List<ProgramaModel> programaList = programaService.findAllPrograma();
        return ResponseEntity.ok().body(programaList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaModel> find(@PathVariable("id") Integer id){
        ProgramaModel programa = programaService.findByIdPrograma(id);
        return ResponseEntity.ok().body(programa);
    }

    @PostMapping
    public ResponseEntity<ProgramaModel> insert(@Valid @RequestBody ProgramaForm programaForm, BindingResult br){

        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ProgramaModel novoPrograma = programaService.insertPrograma(programaForm);
            return ResponseEntity.ok().body(novoPrograma);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramaModel> update(@Valid @RequestBody ProgramaForm programaForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ProgramaModel programaUpdate = programaService.updatePrograma(programaForm,id);
            return  ResponseEntity.ok().body(programaUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        programaService.deletePrograma(id);
        return ResponseEntity.noContent().build();
    }
}
