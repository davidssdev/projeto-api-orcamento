package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.ModalidadeAplicacaoModel;
import br.com.david.orcamento.rest.dto.ModalidadeAplicacaoDTo;
import br.com.david.orcamento.rest.form.ModalidadeAplicacaoForm;
import br.com.david.orcamento.service.ModalidadeAplicacaoService;
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
@RequestMapping("/modalidade-aplicacao")
public class ModalidadeAplicacaoController {

    @Autowired
    ModalidadeAplicacaoService modalidadeAplicacaoService;

    @GetMapping
    public ResponseEntity<List<ModalidadeAplicacaoDTo>> findAll(){
        List <ModalidadeAplicacaoDTo> modalidadeListDTo = modalidadeAplicacaoService.findAllModalidade();
        return ResponseEntity.ok().body(modalidadeListDTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModalidadeAplicacaoDTo> find(@PathVariable("id") Integer id){
        ModalidadeAplicacaoDTo modalidadeDTo = modalidadeAplicacaoService.findByIdModalidade(id);
        return ResponseEntity.ok().body(modalidadeDTo);
    }

    @PostMapping
    public ResponseEntity<ModalidadeAplicacaoDTo> insert(@Valid @RequestBody ModalidadeAplicacaoForm modalidadeAplicacaoForm, BindingResult br){
        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            ModalidadeAplicacaoDTo novaModalidade = modalidadeAplicacaoService.insertModalidade(modalidadeAplicacaoForm);
            return ResponseEntity.ok().body(novaModalidade);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModalidadeAplicacaoDTo> update(@Valid @RequestBody ModalidadeAplicacaoForm modalidadeAplicacaoForm, @PathVariable("id") Integer id, BindingResult br){
        if (br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            ModalidadeAplicacaoDTo modalidadeUpdate = modalidadeAplicacaoService.updateModalidade(modalidadeAplicacaoForm, id);
            return ResponseEntity.ok().body(modalidadeUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        modalidadeAplicacaoService.deleteModalidade(id);
        return ResponseEntity.noContent().build();
    }
}



