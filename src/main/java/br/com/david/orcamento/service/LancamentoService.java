package br.com.david.orcamento.service;

import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.repository.LancamentoRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.LancamentoDTo;
import br.com.david.orcamento.rest.form.LancamentoForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LancamentoService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<LancamentoDTo> findAllLancamento(){
        List<LancamentoModel> lancamentoListDto = lancamentoRepository.findAll();

        return convertLancListModelToDTo(lancamentoListDto);
    }

    public LancamentoDTo findByIdLancamento(Integer id){
        try
        {
            LancamentoModel lancamentoDto = lancamentoRepository.findById(id).get();

            return convertLancModelToDTo(lancamentoDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public LancamentoDTo insertLancamento(LancamentoForm lancamentoForm){
        try
        {
            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            LancamentoModel novoLancamento = convertLancFormToModel(lancamentoForm);

            novoLancamento.setData_lancamento(dataAtualFormatada);
            novoLancamento.setData_cadastro(dataAtualFormatada);
            novoLancamento = lancamentoRepository.save(novoLancamento);

            return convertLancModelToDTo(novoLancamento);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + LancamentoModel.class.getName());
        }
    }

    public  LancamentoDTo updateLancamento(LancamentoForm lancamentoForm, Integer id){
        try
        {
            Optional<LancamentoModel> lancamentoExist = lancamentoRepository.findById(id);
            DataFormato data = new DataFormato();

            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (lancamentoExist.isPresent())
            {
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
                lancamentoUpdate.setData_alteracao(dataAtualFormatada);
                lancamentoUpdate.setAno_orcamento(lancamentoForm.getAno_orcamento());

                lancamentoRepository.save(lancamentoUpdate);
                return convertLancModelToDTo(lancamentoUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteLancamento(Integer id){
        try
        {
            if (lancamentoRepository.existsById(id))
            {
                lancamentoRepository.deleteById(id);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }

    }

    public LancamentoModel convertLancFormToModel(LancamentoForm lancamentoForm){
        LancamentoModel convertLancamento = new LancamentoModel();
        convertLancamento = modelMapper.map(lancamentoForm, LancamentoModel.class);

        return convertLancamento;
    }

    public LancamentoDTo convertLancModelToDTo(LancamentoModel lancamentoModel){
        LancamentoDTo lancamentoDto = new LancamentoDTo();

        lancamentoDto.setLancamento_id(lancamentoModel.getId());
        lancamentoDto.setLacamento_invalido(lancamentoModel.getLacamento_invalido());
        lancamentoDto.setNumero_lancamento_id(lancamentoModel.getNumero_lancamento());
        lancamentoDto.setTipo_lancamento_id(lancamentoModel.getId_tipo_lancamento());
        lancamentoDto.setLancamento_pai_id(lancamentoModel.getId_lancamento_pai());
        lancamentoDto.setUnidade_id(lancamentoModel.getId_unidade());
        lancamentoDto.setDescricao(lancamentoModel.getDescricao());
        lancamentoDto.setUnidade_orcamentaria_id(lancamentoModel.getId_unidade_orcamentaria());
        lancamentoDto.setPrograma_id(lancamentoModel.getId_programa());
        lancamentoDto.setAcao_id(lancamentoModel.getId_acao());
        lancamentoDto.setFonte_recurso_id(lancamentoModel.getId_fonte_recurso());
        lancamentoDto.setModalidade_aplicacao_id(lancamentoModel.getId_modalidade_aplicacao());
        lancamentoDto.setElemento_despesa_id(lancamentoModel.getId_elemento_despesa());
        lancamentoDto.setSolicitante_id(lancamentoModel.getId_solicitante());
        lancamentoDto.setGed(lancamentoModel.getGed());
        lancamentoDto.setContratado(lancamentoModel.getContratado());
        lancamentoDto.setObjetivo_estrategico_id(lancamentoModel.getId_objetivo_estrategico());
        lancamentoDto.setValor(lancamentoModel.getValor());
        lancamentoDto.setTipo_transacao_id(lancamentoModel.getId_tipo_transacao());
        lancamentoDto.setDt_cadastro(lancamentoModel.getData_cadastro());
        lancamentoDto.setDt_alteracao(lancamentoModel.getData_alteracao());
        lancamentoDto.setDt_lancamento(lancamentoModel.getData_lancamento());
        lancamentoDto.setOrcamento_ano(lancamentoModel.getAno_orcamento());

        return lancamentoDto;
    }

    public List<LancamentoDTo> convertLancListModelToDTo(List<LancamentoModel> lancamenlList){
        List<LancamentoDTo> lancamentoDtoList = new ArrayList<>();

        for (LancamentoModel lancamento : lancamenlList)
        {
            LancamentoDTo lancamentoDto = new LancamentoDTo();
            lancamentoDto = convertLancModelToDTo(lancamento);

            lancamentoDtoList.add(lancamentoDto);
        }
        return lancamentoDtoList;
    }

    public List<Object> listLancamentos(){
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_ListLancamentos");
        List<Object> list = storedProcedureQuery.getResultList();

        return list;
    }
}
