package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.service.AcaoService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/acoes")
public class AcaoController {

    @Autowired
    private AcaoService acaoServcie;

    @GetMapping
    public ResponseEntity<List<AcaoModel>> findAll(){
        List<AcaoModel> acaolList = acaoServcie.findAllAcao();
        return ResponseEntity.ok().body(acaolList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcaoModel> find(@PathVariable("id") Integer id){
        AcaoModel acao = acaoServcie.findByIdAcao(id);
        return ResponseEntity.ok().body(acao);
    }

    @PostMapping
    public ResponseEntity<AcaoModel> insert(@Valid @RequestBody AcaoForm acaoForm, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            AcaoModel novaAcao = acaoServcie.insertAcao(acaoForm);
            return ResponseEntity.ok().body(novaAcao);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcaoModel> update(@Valid @RequestBody AcaoForm acaoForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            AcaoModel updateAcao = acaoServcie.updateAcao(acaoForm, id);
            return ResponseEntity.ok().body(updateAcao);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        acaoServcie.deleteAcao(id);

        return ResponseEntity.noContent().build();
    }
}
