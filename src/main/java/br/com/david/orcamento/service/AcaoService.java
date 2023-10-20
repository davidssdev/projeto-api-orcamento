package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.repository.AcaoRepository;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AcaoService {

    @Autowired
    AcaoRepository acaoRepository;

    public AcaoModel findByIdAcao(Integer id){
        try{
            AcaoModel acaoModel = acaoRepository.findById(id).get();
            return acaoModel;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<AcaoModel> findAllAcao(){
        List<AcaoModel> acaoList = acaoRepository.findAll();
        return acaoList;
    }

    public AcaoModel insertAcao(AcaoForm acaoForm){
        try{
            AcaoModel novaAcao = convertAcaoFormToAcaoModel(acaoForm);
            novaAcao = acaoRepository.save(novaAcao);
            return novaAcao;
        }catch (DataIntegrityException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ AcaoForm.class.getName());
        }
    }

    public AcaoModel updateAcao(AcaoForm acaoForm, Integer id){
        try{
            Optional<AcaoModel> acaoExist = acaoRepository.findById(id);
            var dtAtual = LocalDate.now();

            if(acaoExist.isPresent()){
                AcaoModel acaoUpdate = acaoExist.get();
                acaoUpdate.setCodigo(acaoForm.getCodigo());
                acaoUpdate.setNome(acaoForm.getNome());
                acaoUpdate.setDataAlteracao(dtAtual);

                acaoRepository.save(acaoUpdate);
                return acaoUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteAcao(Integer id){
        try{
            if(acaoRepository.existsById(id)){
                acaoRepository.deleteById(id);
            }
        }catch (DataIntegrityException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public AcaoModel convertAcaoFormToAcaoModel(AcaoForm acaoForm){
        var dtAtual = LocalDate.now();
        AcaoModel novaAcao = new AcaoModel();

        novaAcao.setCodigo(acaoForm.getCodigo());
        novaAcao.setNome(acaoForm.getNome());
        novaAcao.setDataCadastro(dtAtual);

        return novaAcao;
    }
}
