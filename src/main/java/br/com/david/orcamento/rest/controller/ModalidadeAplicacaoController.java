package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.ModalidadeAplicacaoModel;
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
@RequestMapping("/modalidade-aplicacao")
public class ModalidadeAplicacaoController {

    @Autowired
    ModalidadeAplicacaoService modalidadeAplicacaoService;

    @GetMapping
    public ResponseEntity<List<ModalidadeAplicacaoModel>> findAll(){
        List <ModalidadeAplicacaoModel> modalidadeList = modalidadeAplicacaoService.findAllModalidade();
        return ResponseEntity.ok().body(modalidadeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModalidadeAplicacaoModel> find(@PathVariable("id") Integer id){
        ModalidadeAplicacaoModel modalidade = modalidadeAplicacaoService.findByIdModalidade(id);
        return ResponseEntity.ok().body(modalidade);
    }

    @PostMapping
    public ResponseEntity<ModalidadeAplicacaoModel> insert(@Valid @RequestBody ModalidadeAplicacaoForm modalidadeAplicacaoForm, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ModalidadeAplicacaoModel novaModalidade = modalidadeAplicacaoService.insertModalidade(modalidadeAplicacaoForm);
            return ResponseEntity.ok().body(novaModalidade);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModalidadeAplicacaoModel> update(@Valid @RequestBody ModalidadeAplicacaoForm modalidadeAplicacaoForm, @PathVariable("id") Integer id, BindingResult br){
        if (br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ModalidadeAplicacaoModel modalidadeUpdate = modalidadeAplicacaoService.updateModalidade(modalidadeAplicacaoForm, id);
                return ResponseEntity.ok().body(modalidadeUpdate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        modalidadeAplicacaoService.deleteModalidade(id);
        return ResponseEntity.noContent().build();
    }
}



