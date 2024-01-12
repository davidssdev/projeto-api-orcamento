package br.com.david.orcamento.service;

import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.model.TipoLancamentoModel;
import br.com.david.orcamento.model.TipoTransacaoModel;
import br.com.david.orcamento.repository.LancamentoRepository;
import br.com.david.orcamento.repository.TipoTransacaoRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.TipoLancamentoDTo;
import br.com.david.orcamento.rest.dto.TipoTransacaoDTo;
import br.com.david.orcamento.rest.form.TipoLancamentoForm;
import br.com.david.orcamento.rest.form.TipoTransacaoForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TipoTransacaoService {

    @Autowired
    TipoTransacaoRepository tipoTransacaoRepository;
    @Autowired
    LancamentoRepository lancamentoRepository;
    @Autowired
    ModelMapper modelMapper;


    public List<TipoTransacaoDTo> findAllTipoTransacao(){
        List<TipoTransacaoModel> tipoTransacaoListDto = tipoTransacaoRepository.findAll();
        return convertListTipoTranscModelToDTo(tipoTransacaoListDto);
    }

    public TipoTransacaoDTo findByIdTipoTransacao(Integer id){
        try
        {
            TipoTransacaoModel tipoTransacaoDto = tipoTransacaoRepository.findById(id).get();
            return convertTipoTransacModelToDTo(tipoTransacaoDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public TipoTransacaoDTo insertTipoTransacao(TipoTransacaoForm tipoTransacaoForm) {
        try
        {
            TipoTransacaoModel novoTipoTransacao = convertTipoTransacFormToModel(tipoTransacaoForm);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            novoTipoTransacao.setData_cadastro(dataAtualFormatada);
            novoTipoTransacao = tipoTransacaoRepository.save(novoTipoTransacao);

            return convertTipoTransacModelToDTo(novoTipoTransacao);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ TipoTransacaoModel.class.getName());
        }
    }

    public TipoTransacaoDTo updateTipoTransacao(TipoTransacaoForm tipoTransacaoForm, Integer id){
        try
        {
            Optional<TipoTransacaoModel> tipoTransacaoExist = tipoTransacaoRepository.findById(id);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (tipoTransacaoExist.isPresent())
            {
                TipoTransacaoModel tipoTransacaoUpdate = tipoTransacaoExist.get();

                tipoTransacaoUpdate.setNome(tipoTransacaoForm.getNome());
                tipoTransacaoUpdate.setData_alteracao(dataAtualFormatada);
                tipoTransacaoRepository.save(tipoTransacaoUpdate);

                return convertTipoTransacModelToDTo(tipoTransacaoUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Possíveis campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteTipoTransacao(Integer id){
        try
        {
            if (tipoTransacaoRepository.existsById(id))
            {
                List<LancamentoModel> lancamentos = lancamentoRepository.findAll();

                for (LancamentoModel lancamento : lancamentos ) {
                    if (!(lancamento.getId_tipo_transacao().equals(id))){
                        tipoTransacaoRepository.deleteById(id);
                    } else {
                        throw new DataIntegrityException("Este tp transação esta contido em um lançamento!");
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

    public TipoTransacaoModel convertTipoTransacFormToModel(TipoTransacaoForm tipoTransacaoForm){
        TipoTransacaoModel tipoTransacaoModel = new TipoTransacaoModel();
        tipoTransacaoModel = modelMapper.map(tipoTransacaoForm, TipoTransacaoModel.class);

        return tipoTransacaoModel;
    }

    public TipoTransacaoDTo convertTipoTransacModelToDTo(TipoTransacaoModel tipoTransacaoModel){
        TipoTransacaoDTo tipoTransacaoDto = new TipoTransacaoDTo();

        tipoTransacaoDto.setTipo_transac_id(tipoTransacaoModel.getId());
        tipoTransacaoDto.setTipo_transac_nome(tipoTransacaoModel.getNome());
        tipoTransacaoDto.setDt_cadastro(tipoTransacaoModel.getData_cadastro());
        tipoTransacaoDto.setDt_alteracao(tipoTransacaoModel.getData_alteracao());

        return tipoTransacaoDto;
    }

    public List<TipoTransacaoDTo> convertListTipoTranscModelToDTo(List<TipoTransacaoModel> tipoTransacaoList){
        List<TipoTransacaoDTo> tipoTransacaoDtoList = new ArrayList<>();

        for (TipoTransacaoModel tipoTransacao : tipoTransacaoList)
        {
            TipoTransacaoDTo tipoTransacaoDto = new TipoTransacaoDTo();
            tipoTransacaoDto = convertTipoTransacModelToDTo(tipoTransacao);

            tipoTransacaoDtoList.add(tipoTransacaoDto);
        }

        return tipoTransacaoDtoList;
    }
}
