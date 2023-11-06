package br.com.david.orcamento.service;

import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.repository.LancamentoRepository;
import br.com.david.orcamento.rest.form.LancamentoForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    LancamentoRepository lancamentoRepository;

    public List<LancamentoModel> findAllLancamento(){
        List<LancamentoModel> lancamentoList = lancamentoRepository.findAll();
        return lancamentoList;
    }

    public LancamentoModel findByIdLancamento(Integer id){
        try{
            LancamentoModel lancamento = lancamentoRepository.findById(id).get();
            return lancamento;
        }catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public LancamentoModel insertLancamento(LancamentoForm lancamentoForm){
        try{
            LancamentoModel novoLancamento = covertLancFormToLancModel(lancamentoForm);
            novoLancamento = lancamentoRepository.save(novoLancamento);
            return novoLancamento;
        }
        catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ LancamentoModel.class.getName());
        }
    }

    public  LancamentoModel updateLancamento(LancamentoForm lancamentoForm, Integer id){
        try{
            Optional<LancamentoModel> lancamentoExist = lancamentoRepository.findById(id);
            var dtAtual = LocalDateTime.now();

            if( lancamentoExist.isPresent()){
                LancamentoModel lancamentoUpdate = lancamentoExist.get();

                lancamentoUpdate.setLacamento_invalido(lancamentoForm.getLacamento_invalido());
                lancamentoUpdate.setNumero_lancamento(lancamentoForm.getNumero_lancamento());
                lancamentoUpdate.setId_tipo_lancamento(lancamentoForm.getId_tipo_lancamento());
                lancamentoUpdate.setId_lancamento_pai(lancamentoForm.getId_lancamento_pai());
                lancamentoUpdate.setId_unidade(lancamentoForm.getId_unidade());
                lancamentoUpdate.setDescricao(lancamentoForm.getDescricao());
                lancamentoUpdate.setId_unidade_orcamentaria(lancamentoForm.getId_unidade_orcamentaria());
                lancamentoUpdate.setId_programa(lancamentoForm.getId_programa());
                lancamentoUpdate.setId_acao(lancamentoForm.getId_acao());
                lancamentoUpdate.setId_fonte_recurso(lancamentoForm.getId_fonte_recurso());
                lancamentoUpdate.setId_grupo_despesa(lancamentoForm.getId_grupo_despesa());
                lancamentoUpdate.setId_modalidade_aplicacao(lancamentoForm.getId_modalidade_aplicacao());
                lancamentoUpdate.setId_elemento_despesa(lancamentoForm.getId_elemento_despesa());
                lancamentoUpdate.setId_solicitante(lancamentoForm.getId_solicitante());
                lancamentoUpdate.setGed(lancamentoForm.getGed());
                lancamentoUpdate.setContratado(lancamentoForm.getContratado());
                lancamentoUpdate.setId_objetivo_estrategico(lancamentoForm.getId_objetivo_estrategico());
                lancamentoUpdate.setValor(lancamentoForm.getValor());
                lancamentoUpdate.setId_tipo_transacao(lancamentoForm.getId_tipo_transacao());
                lancamentoUpdate.setData_alteracao(dtAtual);
                lancamentoUpdate.setAno_orcamento(lancamentoForm.getAno_orcamento());

                lancamentoRepository.save(lancamentoUpdate);
                return lancamentoUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteLancamento(Integer id){
        try{
            if(lancamentoRepository.existsById(id)){
                lancamentoRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }

    }

    public LancamentoModel covertLancFormToLancModel(LancamentoForm lancamentoForm){
        LancamentoModel ConvertLancamento = new LancamentoModel();
        var dtAtual = LocalDateTime.now();

        ConvertLancamento.setLacamento_invalido(lancamentoForm.getLacamento_invalido());
        ConvertLancamento.setNumero_lancamento(lancamentoForm.getNumero_lancamento());
        ConvertLancamento.setId_tipo_lancamento(lancamentoForm.getId_tipo_lancamento());
        ConvertLancamento.setData_lancamento(dtAtual);
        ConvertLancamento.setId_lancamento_pai(lancamentoForm.getId_lancamento_pai());
        ConvertLancamento.setId_unidade(lancamentoForm.getId_unidade());
        ConvertLancamento.setDescricao(lancamentoForm.getDescricao());
        ConvertLancamento.setId_unidade_orcamentaria(lancamentoForm.getId_unidade_orcamentaria());
        ConvertLancamento.setId_programa(lancamentoForm.getId_programa());
        ConvertLancamento.setId_acao(lancamentoForm.getId_acao());
        ConvertLancamento.setId_fonte_recurso(lancamentoForm.getId_fonte_recurso());
        ConvertLancamento.setId_grupo_despesa(lancamentoForm.getId_grupo_despesa());
        ConvertLancamento.setId_modalidade_aplicacao(lancamentoForm.getId_modalidade_aplicacao());
        ConvertLancamento.setId_elemento_despesa(lancamentoForm.getId_elemento_despesa());
        ConvertLancamento.setId_solicitante(lancamentoForm.getId_solicitante());
        ConvertLancamento.setGed(lancamentoForm.getGed());
        ConvertLancamento.setContratado(lancamentoForm.getContratado());
        ConvertLancamento.setId_objetivo_estrategico(lancamentoForm.getId_objetivo_estrategico());
        ConvertLancamento.setValor(lancamentoForm.getValor());
        ConvertLancamento.setId_tipo_transacao(lancamentoForm.getId_tipo_transacao());
        ConvertLancamento.setData_cadastro(dtAtual);
        ConvertLancamento.setAno_orcamento(lancamentoForm.getAno_orcamento());

        return ConvertLancamento;
    }
}
