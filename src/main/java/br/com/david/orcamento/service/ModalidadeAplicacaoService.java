package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.ModalidadeAplicacaoModel;
import br.com.david.orcamento.repository.ModalidadeAplicacaoRepository;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.ModalidadeAplicacaoForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ModalidadeAplicacaoService {

    @Autowired
    ModalidadeAplicacaoRepository modalidadeAplicacaoRepository;

    public ModalidadeAplicacaoModel findByIdModalidade(Integer id){
        try{
            ModalidadeAplicacaoModel modalidade = modalidadeAplicacaoRepository.findById(id).get();
            return modalidade;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<ModalidadeAplicacaoModel> findAllModalidade(){
        List<ModalidadeAplicacaoModel> modalidadeList = modalidadeAplicacaoRepository.findAll();
        return modalidadeList;
    }

    public ModalidadeAplicacaoModel insertModalidade(ModalidadeAplicacaoForm modalidadeAplicacaoForm){
        try{
            ModalidadeAplicacaoModel novaModalidade = convertModaFormToModaModel(modalidadeAplicacaoForm);
            novaModalidade = modalidadeAplicacaoRepository.save(novaModalidade);
            return novaModalidade;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ ModalidadeAplicacaoModel.class.getName());
        }
    }

    public ModalidadeAplicacaoModel updateModalidade(ModalidadeAplicacaoForm modalidadeAplicacaoForm, Integer id){
        try{
            Optional<ModalidadeAplicacaoModel> modalidadeExist = modalidadeAplicacaoRepository.findById(id);
            var dtAtual = LocalDate.now();

            if(modalidadeExist.isPresent()){
                ModalidadeAplicacaoModel modalidadeUpdate = modalidadeExist.get();
                modalidadeUpdate.setCodigo(modalidadeAplicacaoForm.getCodigo());
                modalidadeUpdate.setNome(modalidadeAplicacaoForm.getNome());
                modalidadeUpdate.setData_alteracao(dtAtual);

                modalidadeAplicacaoRepository.save(modalidadeUpdate);
                return modalidadeUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteModalidade(Integer id){
        try{
            if(modalidadeAplicacaoRepository.existsById(id)){
                modalidadeAplicacaoRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public ModalidadeAplicacaoModel convertModaFormToModaModel(ModalidadeAplicacaoForm modalidadeAplicacaoForm){
        ModalidadeAplicacaoModel convertModalidade = new ModalidadeAplicacaoModel();
        var dtAtual = LocalDate.now();

        convertModalidade.setCodigo(modalidadeAplicacaoForm.getCodigo());
        convertModalidade.setNome(modalidadeAplicacaoForm.getNome());
        convertModalidade.setData_cadastro(dtAtual);

        return convertModalidade;
    }
}
