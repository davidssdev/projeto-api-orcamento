package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.SolicitanteModel;
import br.com.david.orcamento.model.TipoLancamentoModel;
import br.com.david.orcamento.repository.TipoLancamentoRepository;
import br.com.david.orcamento.rest.form.SolicitanteForm;
import br.com.david.orcamento.rest.form.TipoLancamentoForm;
import br.com.david.orcamento.service.TipoLancamentoService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicyBase;
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
    public ResponseEntity<List<TipoLancamentoModel>> findAll(){
        List<TipoLancamentoModel> tipoLancamentoList = tipoLancamentoService.findAllTipoLancamento();
        return ResponseEntity.ok().body(tipoLancamentoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoLancamentoModel> find(@PathVariable("id") Integer id){
        TipoLancamentoModel tipoLancamento = tipoLancamentoService.findByIdTipoLancamento(id);
        return ResponseEntity.ok().body(tipoLancamento);
    }

    @PostMapping
    public ResponseEntity<TipoLancamentoModel> insert(@Valid @RequestBody TipoLancamentoForm tipoLancamentoForm, BindingResult br){

        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            TipoLancamentoModel novoTipoLancamento= tipoLancamentoService.insertTipoLancamento(tipoLancamentoForm);
            return ResponseEntity.ok().body(novoTipoLancamento);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoLancamentoModel> update(@Valid @RequestBody TipoLancamentoForm tipoLancamentoForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            TipoLancamentoModel tipoLancamentoUpdate = tipoLancamentoService.updateTipoLancamento(tipoLancamentoForm, id);
            return  ResponseEntity.ok().body(tipoLancamentoUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        tipoLancamentoService.deleteTipoLancamento(id);
        return ResponseEntity.noContent().build();
    }
}
