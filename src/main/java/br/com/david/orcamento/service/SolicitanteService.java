package br.com.david.orcamento.service;

import br.com.david.orcamento.model.ModalidadeAplicacaoModel;
import br.com.david.orcamento.model.SolicitanteModel;
import br.com.david.orcamento.repository.SolicitanteRepository;
import br.com.david.orcamento.rest.form.ModalidadeAplicacaoForm;
import br.com.david.orcamento.rest.form.SolicitanteForm;
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
public class SolicitanteService {

    @Autowired
    SolicitanteRepository solicitanteRepository;
    public SolicitanteModel findByIdSolicitante(Integer id){
        try{
            SolicitanteModel solicitante = solicitanteRepository.findById(id).get();
            return solicitante;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<SolicitanteModel> findAllSolicitante(){
        List<SolicitanteModel> solicitanteList = solicitanteRepository.findAll();
        return solicitanteList;
    }

    public SolicitanteModel insertSolicitante(SolicitanteForm solicitanteForm) {
        try{
            SolicitanteModel novoSolicitante = convertSolicFormToSolicModel(solicitanteForm);
            novoSolicitante = solicitanteRepository.save(novoSolicitante);
            return novoSolicitante;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ SolicitanteModel.class.getName());
        }
    }

    public SolicitanteModel updateSolicitante(SolicitanteForm solicitanteForm, Integer id){
        try{
            Optional<SolicitanteModel> solicitanteExist = solicitanteRepository.findById(id);
            var dtAtual = LocalDateTime.now();

            if(solicitanteExist.isPresent()){
                SolicitanteModel solicitanteUpdate = solicitanteExist.get();
                solicitanteUpdate.setNome(solicitanteForm.getNome());
                solicitanteUpdate.setData_alteracao(dtAtual);

                solicitanteRepository.save(solicitanteUpdate);
                return solicitanteUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteSolicitante(Integer id){
        try{
            if(solicitanteRepository.existsById(id)){
                solicitanteRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public SolicitanteModel convertSolicFormToSolicModel(SolicitanteForm solicitanteForm){
        SolicitanteModel convertSolicitante = new SolicitanteModel();
        var dtAtual = LocalDateTime.now();

        convertSolicitante.setNome(solicitanteForm.getNome());
        convertSolicitante.setData_cadastro(dtAtual);

        return convertSolicitante;
    }
}
