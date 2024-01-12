package br.com.david.orcamento.service;

import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.model.SolicitanteModel;
import br.com.david.orcamento.model.TipoLancamentoModel;
import br.com.david.orcamento.repository.LancamentoRepository;
import br.com.david.orcamento.repository.TipoLancamentoRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.TipoLancamentoDTo;
import br.com.david.orcamento.rest.form.SolicitanteForm;
import br.com.david.orcamento.rest.form.TipoLancamentoForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TipoLancamentoService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TipoLancamentoRepository tipoLancamentoRepository;
    @Autowired
    LancamentoRepository lancamentoRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<TipoLancamentoDTo> findAllTipoLancamento(){
        List<TipoLancamentoModel> tipoLancamentoListDto = tipoLancamentoRepository.findAll();
        return convertListTipoLancModelToDTo(tipoLancamentoListDto);
    }

    public TipoLancamentoDTo findByIdTipoLancamento(Integer id){
        try
        {
            TipoLancamentoModel tipoLancamentoDto = tipoLancamentoRepository.findById(id).get();
            return convertTipoLancModelToDTo(tipoLancamentoDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public TipoLancamentoDTo insertTipoLancamento(TipoLancamentoForm tipoLancamentoForm) {
        try
        {
            TipoLancamentoModel novoTipoLancamento = convertTipoLancFormlToModel(tipoLancamentoForm);
            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            novoTipoLancamento.setData_cadastro(dataAtualFormatada);
            novoTipoLancamento = tipoLancamentoRepository.save(novoTipoLancamento);

            return convertTipoLancModelToDTo(novoTipoLancamento);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + TipoLancamentoModel.class.getName());
        }
    }

    public TipoLancamentoDTo updateTipoLancamento(TipoLancamentoForm tipoLancamentoForm, Integer id){
        try
        {
            Optional<TipoLancamentoModel> tipoLancamentoExist = tipoLancamentoRepository.findById(id);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (tipoLancamentoExist.isPresent())
            {
                TipoLancamentoModel tipoLancamentoUpdate = tipoLancamentoExist.get();
                tipoLancamentoUpdate.setNome(tipoLancamentoForm.getNome());
                tipoLancamentoUpdate.setData_alteracao(dataAtualFormatada);

                tipoLancamentoRepository.save(tipoLancamentoUpdate);
                return convertTipoLancModelToDTo(tipoLancamentoUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Possíveis campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteTipoLancamento(Integer id){
        try
        {
            if (tipoLancamentoRepository.existsById(id))
            {
                List<LancamentoModel> lancamentos = lancamentoRepository.findAll();

                for (LancamentoModel lancamento : lancamentos ) {
                    if (!(lancamento.getId_tipo_lancamento().equals(id))){
                        tipoLancamentoRepository.deleteById(id);
                    } else {
                        throw new DataIntegrityException("Este tp lançamento esta contido em um lançamento!");
                    }
                }

            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id +" não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public TipoLancamentoModel convertTipoLancFormlToModel(TipoLancamentoForm tipoLancamentoForm){
        TipoLancamentoModel tipoLancamentoModel = new TipoLancamentoModel();
        tipoLancamentoModel = modelMapper.map(tipoLancamentoForm, TipoLancamentoModel.class);

        return tipoLancamentoModel;
    }

    public TipoLancamentoDTo convertTipoLancModelToDTo(TipoLancamentoModel lancamentoModel){
        TipoLancamentoDTo tipoLancamentoDto = new TipoLancamentoDTo();

        tipoLancamentoDto.setTipo_lanc_id(lancamentoModel.getId());
        tipoLancamentoDto.setTipo_lanc_nome(lancamentoModel.getNome());
        tipoLancamentoDto.setDt_cadastro(lancamentoModel.getData_cadastro());
        tipoLancamentoDto.setDt_alteracao(lancamentoModel.getData_alteracao());

        return tipoLancamentoDto;
    }

    public List<TipoLancamentoDTo> convertListTipoLancModelToDTo(List<TipoLancamentoModel> tipoLancamentoList){
        List<TipoLancamentoDTo> tipoLancamentoDtoList = new ArrayList<>();

        for (TipoLancamentoModel tipolancamento : tipoLancamentoList )
        {
            TipoLancamentoDTo tipolancamentoDto = new TipoLancamentoDTo();
            tipolancamentoDto = convertTipoLancModelToDTo(tipolancamento);

            tipoLancamentoDtoList.add(tipolancamentoDto);
        }

        return tipoLancamentoDtoList;
    }

    public List<Object> listTipoLancamento(){
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_ListTipoLancamento");
        List<Object> list = storedProcedureQuery.getResultList();
        return list;
    }
}
