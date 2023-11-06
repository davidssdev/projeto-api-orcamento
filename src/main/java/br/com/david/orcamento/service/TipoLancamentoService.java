package br.com.david.orcamento.service;

import br.com.david.orcamento.model.SolicitanteModel;
import br.com.david.orcamento.model.TipoLancamentoModel;
import br.com.david.orcamento.repository.TipoLancamentoRepository;
import br.com.david.orcamento.rest.form.SolicitanteForm;
import br.com.david.orcamento.rest.form.TipoLancamentoForm;
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
public class TipoLancamentoService {

    @Autowired
    TipoLancamentoRepository tipoLancamentoRepository;

    public TipoLancamentoModel findByIdTipoLancamento(Integer id){
        try{
            TipoLancamentoModel tipoLancamento = tipoLancamentoRepository.findById(id).get();
            return tipoLancamento;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<TipoLancamentoModel> findAllTipoLancamento(){
        List<TipoLancamentoModel> tipoLancamentoList = tipoLancamentoRepository.findAll();
        return tipoLancamentoList;
    }

    public TipoLancamentoModel insertTipoLancamento(TipoLancamentoForm tipoLancamentoForm) {
        try{
            TipoLancamentoModel novoTipoLancamento = convertTipoFormToTipoModel(tipoLancamentoForm);
            novoTipoLancamento = tipoLancamentoRepository.save(novoTipoLancamento);
            return novoTipoLancamento;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ TipoLancamentoModel.class.getName());
        }
    }

    public TipoLancamentoModel updateTipoLancamento(TipoLancamentoForm tipoLancamentoForm, Integer id){
        try{
            Optional<TipoLancamentoModel> tipoLancamentoExist = tipoLancamentoRepository.findById(id);
            var dtAtual = LocalDate.now();

            if(tipoLancamentoExist.isPresent()){
                TipoLancamentoModel tipoLancamentoUpdate = tipoLancamentoExist.get();
                tipoLancamentoUpdate.setNome(tipoLancamentoForm.getNome());
                tipoLancamentoUpdate.setData_alteracao(dtAtual);

                tipoLancamentoRepository.save(tipoLancamentoUpdate);
                return tipoLancamentoUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteTipoLancamento(Integer id){
        try{
            if(tipoLancamentoRepository.existsById(id)){
                tipoLancamentoRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public TipoLancamentoModel convertTipoFormToTipoModel(TipoLancamentoForm tipoLancamentoForm){
        TipoLancamentoModel convertTipoLancamento = new TipoLancamentoModel();
        var dtAtual = LocalDate.now();

        convertTipoLancamento.setNome(tipoLancamentoForm.getNome());
        convertTipoLancamento.setData_cadastro(dtAtual);

        return convertTipoLancamento;
    }
}
