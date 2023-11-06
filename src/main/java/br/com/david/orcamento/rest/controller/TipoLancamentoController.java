package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.TipoLancamentoModel;
import br.com.david.orcamento.rest.dto.TipoLancamentoDTo;
import br.com.david.orcamento.rest.form.TipoLancamentoForm;
import br.com.david.orcamento.service.TipoLancamentoService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("tipo-lancamento")
public class TipoLancamentoController {

    @Autowired
    TipoLancamentoService tipoLancamentoService;

    @GetMapping
    public ResponseEntity<List<TipoLancamentoDTo>> findAll(){
        List<TipoLancamentoDTo> tipoLancamentoListDTo = tipoLancamentoService.findAllTipoLancamento();
        return ResponseEntity.ok().body(tipoLancamentoListDTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoLancamentoDTo> find(@PathVariable("id") Integer id){
        TipoLancamentoDTo tipoLancamentoDTo = tipoLancamentoService.findByIdTipoLancamento(id);
        return ResponseEntity.ok().body(tipoLancamentoDTo);
    }

    @PostMapping
    public ResponseEntity<TipoLancamentoDTo> insert(@Valid @RequestBody TipoLancamentoForm tipoLancamentoForm, BindingResult br){

        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            TipoLancamentoDTo novoTipoLancamentoDTo = tipoLancamentoService.insertTipoLancamento(tipoLancamentoForm);
            return ResponseEntity.ok().body(novoTipoLancamentoDTo);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoLancamentoDTo> update(@Valid @RequestBody TipoLancamentoForm tipoLancamentoForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            TipoLancamentoDTo tipoLancamentoUpdateDTo = tipoLancamentoService.updateTipoLancamento(tipoLancamentoForm, id);
            return  ResponseEntity.ok().body(tipoLancamentoUpdateDTo);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        tipoLancamentoService.deleteTipoLancamento(id);
        return ResponseEntity.noContent().build();
    }
}
