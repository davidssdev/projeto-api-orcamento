package br.com.david.orcamento.service;

import br.com.david.orcamento.model.SolicitanteModel;
import br.com.david.orcamento.model.UnidadeModel;
import br.com.david.orcamento.repository.UnidadeRepository;
import br.com.david.orcamento.rest.form.SolicitanteForm;
import br.com.david.orcamento.rest.form.UnidadeForm;
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
public class UnidadeService {

    @Autowired
    UnidadeRepository unidadeRepository;

    public UnidadeModel findByIdUnidade(Integer id){
        try{
            UnidadeModel unidade = unidadeRepository.findById(id).get();
            return unidade;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<UnidadeModel> findAllUnidade(){
        List<UnidadeModel> unidadeList = unidadeRepository.findAll();
        return unidadeList;
    }

    public UnidadeModel insertUnidade(UnidadeForm unidadeForm) {
        try{
            UnidadeModel novaUnidade = convertUnidFormToUnidModel(unidadeForm);
            novaUnidade = unidadeRepository.save(novaUnidade);
            return novaUnidade;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ UnidadeModel.class.getName());
        }
    }

    public UnidadeModel updateUnidade(UnidadeForm unidadeForm, Integer id){
        try{
            Optional<UnidadeModel> unidadeExist = unidadeRepository.findById(id);
            var dtAtual = LocalDateTime.now();

            if(unidadeExist.isPresent()){
                UnidadeModel unidadeUpdate = unidadeExist.get();
                unidadeUpdate.setNome(unidadeForm.getNome());
                unidadeUpdate.setData_alteracao(dtAtual);

                unidadeRepository.save(unidadeUpdate);
                return unidadeUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteUnidade(Integer id){
        try{
            if(unidadeRepository.existsById(id)){
                unidadeRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public UnidadeModel convertUnidFormToUnidModel(UnidadeForm unidadeForm){
        UnidadeModel convertUnidade = new UnidadeModel();
        var dtAtual = LocalDateTime.now();

        convertUnidade.setNome(unidadeForm.getNome());
        convertUnidade.setData_cadastro(dtAtual);

        return convertUnidade;
    }
}
