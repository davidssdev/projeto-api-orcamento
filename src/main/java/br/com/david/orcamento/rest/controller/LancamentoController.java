package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.rest.form.LancamentoForm;
import br.com.david.orcamento.service.LancamentoService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("lancamento")
public class LancamentoController {

    @Autowired
    LancamentoService lancamentoService;

    @GetMapping
    public ResponseEntity<List<LancamentoModel>> findAll(){
        List<LancamentoModel> lancamentoList = lancamentoService.findAllLancamento();
        return ResponseEntity.ok().body(lancamentoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoModel> find(@PathVariable("id") Integer id){
        LancamentoModel lancamento = lancamentoService.findByIdLancamento(id);
        return ResponseEntity.ok().body(lancamento);
    }

    @PostMapping
    public ResponseEntity<LancamentoModel> insert(@RequestBody LancamentoForm lancamentoForm, BindingResult br){
        if (br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            LancamentoModel novoLancamento = lancamentoService.insertLancamento(lancamentoForm);
            return ResponseEntity.ok().body(novoLancamento);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoModel> update(@RequestBody LancamentoForm lancamentoForm, @PathVariable("id") Integer id, BindingResult br){
        if (br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            LancamentoModel lancamentoUpdate = lancamentoService.updateLancamento(lancamentoForm, id);
            return ResponseEntity.ok().body(lancamentoUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        lancamentoService.deleteLancamento(id);
        return ResponseEntity.noContent().build();
    }
}
