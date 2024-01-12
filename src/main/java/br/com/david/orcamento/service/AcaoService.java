package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.repository.AcaoRepository;
import br.com.david.orcamento.repository.LancamentoRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.AcaoDTo;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class AcaoService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    AcaoRepository acaoRepository;

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    ModelMapper modelMapper = new ModelMapper();

    public List<AcaoDTo> findAllAcao(){
        List<AcaoModel> acaoListDto = acaoRepository.findAll();

        return convertListAcaotoDTo(acaoListDto);
    }

    public AcaoDTo findByIdAcao(Integer id){
        try
        {
            AcaoModel acaoModelDto = acaoRepository.findById(id).get();

            return convertAcaoModelToDto(acaoModelDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public AcaoDTo insertAcao(AcaoForm acaoForm){
        try
        {
            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            AcaoModel novaAcao = convertAcaoFormToModel(acaoForm);
            novaAcao.setData_cadastro(dataAtualFormatada);
            novaAcao = acaoRepository.save(novaAcao);

            return convertAcaoModelToDto(novaAcao);

        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + AcaoModel.class.getName());
        }
    }

    public AcaoDTo updateAcao(AcaoForm acaoForm, Integer id){
        try
        {
            Optional<AcaoModel> acaoExist = acaoRepository.findById(id);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (acaoExist.isPresent())
            {
                AcaoModel acaoUpdate = acaoExist.get();

                acaoUpdate.setCodigo(acaoForm.getCodigo());
                acaoUpdate.setNome(acaoForm.getNome());
                acaoUpdate.setData_alteracao(dataAtualFormatada);

                acaoRepository.save(acaoUpdate);

                return convertAcaoModelToDto(acaoUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Possíveis campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteAcao(Integer id){
        try
        {
            if (acaoRepository.existsById(id))
            {
                List<LancamentoModel> lancamentos = lancamentoRepository.findAll();

                for (LancamentoModel lancamento : lancamentos ) {
                    if (!(lancamento.getId_acao().equals(id))){
                        acaoRepository.deleteById(id);
                    } else {
                      throw new DataIntegrityException("Esta ação esta contida em um lançamento!");
                    }
                }

            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public AcaoModel convertAcaoFormToModel(AcaoForm acaoForm){
        AcaoModel acaoModel = new AcaoModel();
        acaoModel = modelMapper.map(acaoForm, AcaoModel.class);

        return acaoModel;
    }

    public AcaoDTo convertAcaoModelToDto(AcaoModel acaoModel){
        AcaoDTo acaoDto = new AcaoDTo();

        acaoDto.setAcao_id(acaoModel.getId());
        acaoDto.setAcao_codigo(acaoModel.getCodigo());
        acaoDto.setAcao_nome(acaoModel.getNome());
        acaoDto.setDt_cadastro(acaoModel.getData_cadastro());
        acaoDto.setDt_alteracao(acaoModel.getData_alteracao());

        return acaoDto;
    }

    public List<AcaoDTo> convertListAcaotoDTo(List<AcaoModel> listAcao){
        List<AcaoDTo> listAcaoDto = new ArrayList<>();

        for (AcaoModel acao: listAcao)
        {
            AcaoDTo acaoDto = new AcaoDTo();
            acaoDto = convertAcaoModelToDto(acao);

            listAcaoDto.add(acaoDto);
        }

        return listAcaoDto;
    }

    public List<AcaoDTo> consultAcoes(){
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_ListAcao");
        query.execute();

        return query.getResultList();
    }

}
