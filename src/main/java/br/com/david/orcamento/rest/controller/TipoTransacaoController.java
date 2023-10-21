package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.TipoLancamentoModel;
import br.com.david.orcamento.model.TipoTransacaoModel;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.TipoLancamentoForm;
import br.com.david.orcamento.rest.form.TipoTransacaoForm;
import br.com.david.orcamento.service.TipoTransacaoService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tipo-transacao")
public class TipoTransacaoController {

    @Autowired
    TipoTransacaoService tipoTransacaoService;

    @GetMapping
    public ResponseEntity<List<TipoTransacaoModel>> findAll(){
        List<TipoTransacaoModel> tipoTransacaoList = tipoTransacaoService.findAllTipoTransacao();
        return ResponseEntity.ok().body(tipoTransacaoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTransacaoModel> find(@PathVariable("id") Integer id){
        TipoTransacaoModel tipoTransacao = tipoTransacaoService.findByIdTipoTransacao(id);
        return ResponseEntity.ok().body(tipoTransacao);
    }

    @PostMapping
    public ResponseEntity<TipoTransacaoModel> insert(@Valid @RequestBody TipoTransacaoForm tipoTransacaoForm, BindingResult br){

        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            TipoTransacaoModel novoTipoTransacao = tipoTransacaoService.insertTipoTransacao(tipoTransacaoForm);
            return ResponseEntity.ok().body(novoTipoTransacao);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTransacaoModel> update(@Valid @RequestBody TipoTransacaoForm tipoTransacaoForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            TipoTransacaoModel tipoTransacaoUpdate = tipoTransacaoService.updateTipoTransacao(tipoTransacaoForm, id);
            return  ResponseEntity.ok().body(tipoTransacaoUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        tipoTransacaoService.deleteTipoTransacao(id);
        return ResponseEntity.noContent().build();
    }
}
