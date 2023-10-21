package br.com.david.orcamento.service;

import br.com.david.orcamento.model.GrupoDespesaModel;
import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.repository.LancamentoRepository;
import br.com.david.orcamento.rest.form.LancamentoForm;
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
            var dtAtual = LocalDate.now();

            if( lancamentoExist.isPresent()){
                LancamentoModel lancamentoUpdate = lancamentoExist.get();

                lancamentoUpdate.setLancamentoInvalido(lancamentoForm.getLancamentoInvalido());
                lancamentoUpdate.setNumeroLancamento(lancamentoForm.getNumeroLancamento());
                lancamentoUpdate.setIdTipoLancamento(lancamentoForm.getIdTipoLancamento());
                lancamentoUpdate.setDataLancamento(dtAtual);
                lancamentoUpdate.setIdLancamentoPai(lancamentoForm.getIdLancamentoPai());
                lancamentoUpdate.setIdUnidade(lancamentoForm.getIdUnidade());
                lancamentoUpdate.setDescricao(lancamentoForm.getDescricao());
                lancamentoUpdate.setIdUnidadeOrcamentaria(lancamentoForm.getIdUnidadeOrcamentaria());
                lancamentoUpdate.setIdPrograma(lancamentoForm.getIdPrograma());
                lancamentoUpdate.setIdAcao(lancamentoForm.getIdAcao());
                lancamentoUpdate.setIdFonteRecurso(lancamentoForm.getIdFonteRecurso());
                lancamentoUpdate.setIdGrupoDespesa(lancamentoForm.getIdGrupoDespesa());
                lancamentoUpdate.setIdModalidadeAplicacao(lancamentoForm.getIdModalidadeAplicacao());
                lancamentoUpdate.setIdElementoDespesa(lancamentoForm.getIdElementoDespesa());
                lancamentoUpdate.setIdSolicitante(lancamentoForm.getIdSolicitante());
                lancamentoUpdate.setGed(lancamentoForm.getGed());
                lancamentoUpdate.setContratado(lancamentoForm.getContratado());
                lancamentoUpdate.setIdObjetivoEstrategico(lancamentoForm.getIdObjetivoEstrategico());
                lancamentoUpdate.setValor(lancamentoForm.getValor());
                lancamentoUpdate.setIdTipoTransacao(lancamentoForm.getIdTipoTransacao());
                lancamentoUpdate.setDataAlteracao(dtAtual);
                lancamentoUpdate.setAnoOrcamento(lancamentoForm.getAnoOrcamento());

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
        var dtAtual = LocalDate.now();

        ConvertLancamento.setLancamentoInvalido(lancamentoForm.getLancamentoInvalido());
        ConvertLancamento.setNumeroLancamento(lancamentoForm.getNumeroLancamento());
        ConvertLancamento.setIdTipoLancamento(lancamentoForm.getIdTipoLancamento());
        ConvertLancamento.setDataLancamento(dtAtual);
        ConvertLancamento.setIdLancamentoPai(lancamentoForm.getIdLancamentoPai());
        ConvertLancamento.setIdUnidade(lancamentoForm.getIdUnidade());
        ConvertLancamento.setDescricao(lancamentoForm.getDescricao());
        ConvertLancamento.setIdUnidadeOrcamentaria(lancamentoForm.getIdUnidadeOrcamentaria());
        ConvertLancamento.setIdPrograma(lancamentoForm.getIdPrograma());
        ConvertLancamento.setIdAcao(lancamentoForm.getIdAcao());
        ConvertLancamento.setIdFonteRecurso(lancamentoForm.getIdFonteRecurso());
        ConvertLancamento.setIdGrupoDespesa(lancamentoForm.getIdGrupoDespesa());
        ConvertLancamento.setIdModalidadeAplicacao(lancamentoForm.getIdModalidadeAplicacao());
        ConvertLancamento.setIdElementoDespesa(lancamentoForm.getIdElementoDespesa());
        ConvertLancamento.setIdSolicitante(lancamentoForm.getIdSolicitante());
        ConvertLancamento.setGed(lancamentoForm.getGed());
        ConvertLancamento.setContratado(lancamentoForm.getContratado());
        ConvertLancamento.setIdObjetivoEstrategico(lancamentoForm.getIdObjetivoEstrategico());
        ConvertLancamento.setValor(lancamentoForm.getValor());
        ConvertLancamento.setIdTipoTransacao(lancamentoForm.getIdTipoTransacao());
        ConvertLancamento.setDataCadastro(dtAtual);
        ConvertLancamento.setAnoOrcamento(lancamentoForm.getAnoOrcamento());

        return ConvertLancamento;
    }
}
