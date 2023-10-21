package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.ObjetivoEstrategicoModel;
import br.com.david.orcamento.rest.form.ObjetivoEstrategicoForm;
import br.com.david.orcamento.service.ObjetivoEstrategicoService;
import br.com.david.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/objetivos-estrategicos")
public class ObjetivoEstrategicoController {

    @Autowired
    ObjetivoEstrategicoService objetivosEstrategicosService;

    @GetMapping
    public ResponseEntity<List<ObjetivoEstrategicoModel>> findAll(){
        List<ObjetivoEstrategicoModel> objetivosList = objetivosEstrategicosService.findAllObjetivos();
        return ResponseEntity.ok().body(objetivosList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjetivoEstrategicoModel> find(@PathVariable("id") Integer id){
        ObjetivoEstrategicoModel objetivos = objetivosEstrategicosService.findByIdObjetivos(id);
        return ResponseEntity.ok().body(objetivos);
    }

    @PostMapping
    public ResponseEntity<ObjetivoEstrategicoModel> insert(@Valid @RequestBody ObjetivoEstrategicoForm objetivosEstrategicosForm, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ObjetivoEstrategicoModel novaObjetivos = objetivosEstrategicosService.insertObjetivos(objetivosEstrategicosForm);
            return ResponseEntity.ok().body(novaObjetivos);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjetivoEstrategicoModel> update(@Valid @RequestBody ObjetivoEstrategicoForm objetivosEstrategicosForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors()){
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else {
            ObjetivoEstrategicoModel updateObjetivos = objetivosEstrategicosService.updateObjetivos(objetivosEstrategicosForm, id);
            return ResponseEntity.ok().body(updateObjetivos);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        objetivosEstrategicosService.deleteObjetivos(id);
        return ResponseEntity.noContent().build();
    }
}
