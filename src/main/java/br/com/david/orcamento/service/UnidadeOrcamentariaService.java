package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.UnidadeOrcamentariaModel;
import br.com.david.orcamento.repository.UnidadeOrcamentoRepository;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.UnidadeOrcamentariaForm;
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
public class UnidadeOrcamentariaService {

    @Autowired
    UnidadeOrcamentoRepository unidadeOrcamentoRepository;

    public UnidadeOrcamentariaModel findByIdUnidadeOrcamentaria(Integer id){
        try{
            UnidadeOrcamentariaModel unidadeOrcamentaria = unidadeOrcamentoRepository.findById(id).get();
            return unidadeOrcamentaria;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<UnidadeOrcamentariaModel> findAllUnidadeOrcamentaria(){
        List<UnidadeOrcamentariaModel> unidadeOrcamentariaList = unidadeOrcamentoRepository.findAll();
        return unidadeOrcamentariaList;
    }

    public UnidadeOrcamentariaModel insertUnidadeOrcamentaria(UnidadeOrcamentariaForm unidadeOrcamentariaForm){
        try{
            UnidadeOrcamentariaModel novaUnidadeOrcamentaria = convertUnidOrcamentariaFormToUnidOrcamentariaModel(unidadeOrcamentariaForm);
            novaUnidadeOrcamentaria = unidadeOrcamentoRepository.save(novaUnidadeOrcamentaria);
            return novaUnidadeOrcamentaria;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ UnidadeOrcamentariaModel.class.getName());
        }
    }

    public UnidadeOrcamentariaModel updateUndOrcamentaria(UnidadeOrcamentariaForm unidadeOrcamentariaForm, Integer id){
        try{
            Optional<UnidadeOrcamentariaModel> unidadeOrcamentariaExist = unidadeOrcamentoRepository.findById(id);
            var dtAtual = LocalDateTime.now();

            if(unidadeOrcamentariaExist.isPresent()){
                UnidadeOrcamentariaModel undOrcamentariaUpdate = unidadeOrcamentariaExist.get();
                undOrcamentariaUpdate.setCodigo(unidadeOrcamentariaForm.getCodigo());
                undOrcamentariaUpdate.setNome(unidadeOrcamentariaForm.getNome());
                undOrcamentariaUpdate.setData_alteracao(dtAtual);

                unidadeOrcamentoRepository.save(undOrcamentariaUpdate);
                return undOrcamentariaUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteUndOrcamentaria(Integer id){
        try{
            if(unidadeOrcamentoRepository.existsById(id)){
                unidadeOrcamentoRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public UnidadeOrcamentariaModel convertUnidOrcamentariaFormToUnidOrcamentariaModel(UnidadeOrcamentariaForm unidadeOrcamentariaForm){
        UnidadeOrcamentariaModel convertUndOrcamentaria = new UnidadeOrcamentariaModel();
        var dtAtual = LocalDateTime.now();

        convertUndOrcamentaria.setCodigo(unidadeOrcamentariaForm.getCodigo());
        convertUndOrcamentaria.setNome(unidadeOrcamentariaForm.getNome());
        convertUndOrcamentaria.setData_cadastro(dtAtual);

        return convertUndOrcamentaria;
    }
}
