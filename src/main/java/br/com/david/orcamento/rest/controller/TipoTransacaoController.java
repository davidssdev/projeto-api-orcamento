package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.TipoTransacaoModel;
import br.com.david.orcamento.rest.dto.TipoTransacaoDTo;
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
    public ResponseEntity<List<TipoTransacaoDTo>> findAll(){
        List<TipoTransacaoDTo> tipoTransacaoListDTo = tipoTransacaoService.findAllTipoTransacao();
        return ResponseEntity.ok().body(tipoTransacaoListDTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTransacaoDTo> find(@PathVariable("id") Integer id){
        TipoTransacaoDTo tipoTransacaoDTo = tipoTransacaoService.findByIdTipoTransacao(id);
        return ResponseEntity.ok().body(tipoTransacaoDTo);
    }

    @PostMapping
    public ResponseEntity<TipoTransacaoDTo> insert(@Valid @RequestBody TipoTransacaoForm tipoTransacaoForm, BindingResult br){

        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            TipoTransacaoDTo novoTipoTransacaoDTo = tipoTransacaoService.insertTipoTransacao(tipoTransacaoForm);
            return ResponseEntity.ok().body(novoTipoTransacaoDTo);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTransacaoDTo> update(@Valid @RequestBody TipoTransacaoForm tipoTransacaoForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            TipoTransacaoDTo tipoTransacaoUpdateDTo = tipoTransacaoService.updateTipoTransacao(tipoTransacaoForm, id);
            return  ResponseEntity.ok().body(tipoTransacaoUpdateDTo);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        tipoTransacaoService.deleteTipoTransacao(id);
        return ResponseEntity.noContent().build();
    }
}
