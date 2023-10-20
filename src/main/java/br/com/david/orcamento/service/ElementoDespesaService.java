package br.com.david.orcamento.service;

import br.com.david.orcamento.model.ElementoDespesaModel;
import br.com.david.orcamento.repository.ElementoDespesaRepository;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.ElementoDespesaForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ElementoDespesaService {

    @Autowired
    ElementoDespesaRepository elementoDespesaRepository;

    public ElementoDespesaModel findByIdElemento(Integer id){
        try{
            ElementoDespesaModel elemento = elementoDespesaRepository.findById(id).get();
            return elemento;
        }catch (NoSuchElementException e ){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<ElementoDespesaModel> findAllElemento(){
        List<ElementoDespesaModel> elementoList = elementoDespesaRepository.findAll();
        return elementoList;
    }

    public ElementoDespesaModel insertElemento(@RequestBody ElementoDespesaForm elementoDespesaForm){
        try{
            ElementoDespesaModel novoElemento = convertElementoFormToElementoForm(elementoDespesaForm);
            novoElemento = elementoDespesaRepository.save(novoElemento);

            return novoElemento;
        }catch (DataIntegrityException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ AcaoForm.class.getName());
        }
    }

    public ElementoDespesaModel updateElemento(ElementoDespesaForm elementoDespesaForm,Integer id){
        try{
            Optional<ElementoDespesaModel> elementoExist = elementoDespesaRepository.findById(id);
            var dtAtual = LocalDate.now();

            if (elementoExist.isPresent()){
                ElementoDespesaModel elementoUpdate = elementoExist.get();
                elementoUpdate.setCodigo(elementoDespesaForm.getCodigo());
                elementoUpdate.setNome(elementoDespesaForm.getNome());
                elementoUpdate.setDataAlteracao(dtAtual);

                elementoDespesaRepository.save(elementoUpdate);
                return elementoUpdate;
            }else {
                throw new DataIntegrityException("Objeto não encontrado!");
            }
        }catch (DataIntegrityException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteElemento(Integer id){
        try{
            if(elementoDespesaRepository.existsById(id)){
                elementoDespesaRepository.deleteById(id);
            }
        }catch (DataIntegrityException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public ElementoDespesaModel convertElementoFormToElementoForm(ElementoDespesaForm elementoDespesaForm){

        var dtAtual = LocalDate.now();
        ElementoDespesaModel elementoModel = new ElementoDespesaModel();

        elementoModel.setCodigo(elementoDespesaForm.getCodigo());
        elementoModel.setNome(elementoDespesaForm.getNome());
        elementoModel.setDataCadastro(dtAtual);

        return elementoModel;
    }
}
