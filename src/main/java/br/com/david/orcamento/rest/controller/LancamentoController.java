package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.rest.dto.LancamentoDTo;
import br.com.david.orcamento.rest.form.LancamentoForm;
import br.com.david.orcamento.service.LancamentoService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("lancamento")
public class LancamentoController {

    @Autowired
    LancamentoService lancamentoService;

    @GetMapping
    public ResponseEntity<List<LancamentoDTo>> findAll(){
        List<LancamentoDTo> lancamentoDToListList = lancamentoService.findAllLancamento();

        return ResponseEntity.ok().body(lancamentoDToListList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoDTo> find(@PathVariable("id") Integer id){
        LancamentoDTo lancamentoDTo = lancamentoService.findByIdLancamento(id);

        return ResponseEntity.ok().body(lancamentoDTo);
    }

    @PostMapping
    public ResponseEntity<LancamentoDTo> insert(@Valid @RequestBody LancamentoForm lancamentoForm, BindingResult br){
        if (br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            LancamentoDTo novoLancamentoDTo = lancamentoService.insertLancamento(lancamentoForm);
            return ResponseEntity.ok().body(novoLancamentoDTo);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoDTo> update(@Valid @RequestBody LancamentoForm lancamentoForm, @PathVariable("id") Integer id, BindingResult br){
        if (br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            LancamentoDTo lancamentoUpdateDTo = lancamentoService.updateLancamento(lancamentoForm, id);
            return ResponseEntity.ok().body(lancamentoUpdateDTo);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        lancamentoService.deleteLancamento(id);
        return ResponseEntity.noContent().build();
    }
}
