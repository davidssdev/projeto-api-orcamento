package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.rest.dto.AcaoDTo;
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
    public ResponseEntity<List<AcaoDTo>> findAll(){
        List<AcaoDTo> acaoDtolList = acaoServcie.findAllAcao();

        return ResponseEntity.ok().body(acaoDtolList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcaoDTo> find(@PathVariable("id") Integer id){
        AcaoDTo acaoDto = acaoServcie.findByIdAcao(id);

        return ResponseEntity.ok().body(acaoDto);
    }

    @PostMapping
    public ResponseEntity<AcaoDTo> insert(@Valid @RequestBody AcaoForm acaoForm, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            AcaoDTo novaAcao = acaoServcie.insertAcao(acaoForm);

            return ResponseEntity.ok().body(novaAcao);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcaoDTo> update(@Valid @RequestBody AcaoForm acaoForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            AcaoDTo updateAcao = acaoServcie.updateAcao(acaoForm, id);
            return ResponseEntity.ok().body(updateAcao);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        acaoServcie.deleteAcao(id);
        return ResponseEntity.noContent().build();
    }
}
