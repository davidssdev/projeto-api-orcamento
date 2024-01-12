package br.com.david.orcamento.rest.controller;

import br.com.david.orcamento.model.ObjetivoEstrategicoModel;
import br.com.david.orcamento.rest.dto.ObjetivoEstrategicoDTo;
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
@CrossOrigin
@RequestMapping("/objetivos-estrategicos")
public class ObjetivoEstrategicoController {

    @Autowired
    ObjetivoEstrategicoService objetivosEstrategicosService;

    @GetMapping
    public ResponseEntity<List<ObjetivoEstrategicoDTo>> findAll(){
        List<ObjetivoEstrategicoDTo> objetivosListDTo = objetivosEstrategicosService.findAllObjetivo();
        return ResponseEntity.ok().body(objetivosListDTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjetivoEstrategicoDTo> find(@PathVariable("id") Integer id){
        ObjetivoEstrategicoDTo objetivos = objetivosEstrategicosService.findByIdObjetivo(id);
        return ResponseEntity.ok().body(objetivos);
    }

    @PostMapping
    public ResponseEntity<ObjetivoEstrategicoDTo> insert(@Valid @RequestBody ObjetivoEstrategicoForm objetivosEstrategicosForm, BindingResult br){
        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            ObjetivoEstrategicoDTo novaObjetivos = objetivosEstrategicosService.insertObjetivo(objetivosEstrategicosForm);
            return ResponseEntity.ok().body(novaObjetivos);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjetivoEstrategicoDTo> update(@Valid @RequestBody ObjetivoEstrategicoForm objetivosEstrategicosForm, @PathVariable("id") Integer id, BindingResult br){
        if(br.hasErrors())
        {
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        }else
        {
            ObjetivoEstrategicoDTo updateObjetivos = objetivosEstrategicosService.updateObjetivo(objetivosEstrategicosForm, id);
            return ResponseEntity.ok().body(updateObjetivos);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        objetivosEstrategicosService.deleteObjetivo(id);
        return ResponseEntity.noContent().build();
    }
}
