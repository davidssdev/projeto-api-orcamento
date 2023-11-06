package br.com.david.orcamento.service;

import br.com.david.orcamento.model.ObjetivoEstrategicoModel;
import br.com.david.orcamento.repository.ObjetivoEstrategicosRepository;
import br.com.david.orcamento.rest.form.ObjetivoEstrategicoForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ObjetivoEstrategicoService {

    @Autowired
    ObjetivoEstrategicosRepository objetivoEstrategicosRepository;

    public ObjetivoEstrategicoModel findByIdObjetivos(Integer id){
        try{
            ObjetivoEstrategicoModel objetivo = objetivoEstrategicosRepository.findById(id).get();
            return objetivo;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<ObjetivoEstrategicoModel> findAllObjetivos(){
        List<ObjetivoEstrategicoModel> objetivosList = objetivoEstrategicosRepository.findAll();
        return objetivosList;
    }

    public ObjetivoEstrategicoModel insertObjetivos(ObjetivoEstrategicoForm objetivosEstrategicosForm){
        try{
            ObjetivoEstrategicoModel novoObjetivos = convertObjetiFormToObjetiModel(objetivosEstrategicosForm);
            novoObjetivos = objetivoEstrategicosRepository.save(novoObjetivos);
            return novoObjetivos;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ ObjetivoEstrategicoModel.class.getName());
        }
    }

    public ObjetivoEstrategicoModel updateObjetivos(ObjetivoEstrategicoForm objetivosEstrategicosForm, Integer id){
        try{
            Optional<ObjetivoEstrategicoModel> objetivosExist = objetivoEstrategicosRepository.findById(id);
            var dtAtual = LocalDateTime.now();

            if(objetivosExist.isPresent()){
                ObjetivoEstrategicoModel objetivosUpdate = objetivosExist.get();
                objetivosUpdate.setNome(objetivosEstrategicosForm.getNome());
                objetivosUpdate.setData_alteracao(dtAtual);

                objetivoEstrategicosRepository.save(objetivosUpdate);
                return objetivosUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteObjetivos(Integer id){
        try{
            if(objetivoEstrategicosRepository.existsById(id)){
                objetivoEstrategicosRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public ObjetivoEstrategicoModel convertObjetiFormToObjetiModel(ObjetivoEstrategicoForm objetivosEstrategicosForm){
        ObjetivoEstrategicoModel convertObjetivos = new ObjetivoEstrategicoModel();
        var dtAtual = LocalDateTime.now();

        convertObjetivos.setNome(objetivosEstrategicosForm.getNome());
        convertObjetivos.setData_cadastro(dtAtual);

        return convertObjetivos;
    }
}
