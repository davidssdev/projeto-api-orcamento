package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.model.UnidadeOrcamentariaModel;
import br.com.david.orcamento.repository.LancamentoRepository;
import br.com.david.orcamento.repository.UnidadeOrcamentoRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.UnidadeOrcamentariaDTo;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.UnidadeOrcamentariaForm;
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
public class UnidadeOrcamentariaService {

    @Autowired
    UnidadeOrcamentoRepository unidadeOrcamentoRepository;
    @Autowired
    LancamentoRepository lancamentoRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<UnidadeOrcamentariaDTo> findAllUnidadeOrcamentaria(){
        List<UnidadeOrcamentariaModel> unidadeOrcamentariaListDto = unidadeOrcamentoRepository.findAll();
        return convertListUndOrcamentModelToDTo(unidadeOrcamentariaListDto);
    }

    public UnidadeOrcamentariaDTo findByIdUnidadeOrcamentaria(Integer id){
        try
        {
            UnidadeOrcamentariaModel unidadeOrcamentariaDto = unidadeOrcamentoRepository.findById(id).get();
            return convertUndOrcamentModelToDTo(unidadeOrcamentariaDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public UnidadeOrcamentariaDTo insertUnidadeOrcamentaria(UnidadeOrcamentariaForm unidadeOrcamentariaForm){
        try
        {
            UnidadeOrcamentariaModel novaUnidadeOrcamentaria = convertUndOrcamentFormToModel(unidadeOrcamentariaForm);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            novaUnidadeOrcamentaria.setData_cadastro(dataAtualFormatada);
            novaUnidadeOrcamentaria = unidadeOrcamentoRepository.save(novaUnidadeOrcamentaria);

            return convertUndOrcamentModelToDTo(novaUnidadeOrcamentaria);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + UnidadeOrcamentariaModel.class.getName());
        }
    }

    public UnidadeOrcamentariaDTo updateUndOrcamentaria(UnidadeOrcamentariaForm unidadeOrcamentariaForm, Integer id){
        try
        {
            Optional<UnidadeOrcamentariaModel> unidadeOrcamentariaExist = unidadeOrcamentoRepository.findById(id);
            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (unidadeOrcamentariaExist.isPresent())
            {
                UnidadeOrcamentariaModel undOrcamentariaUpdate = unidadeOrcamentariaExist.get();

                undOrcamentariaUpdate.setCodigo(unidadeOrcamentariaForm.getCodigo());
                undOrcamentariaUpdate.setNome(unidadeOrcamentariaForm.getNome());
                undOrcamentariaUpdate.setData_alteracao(dataAtualFormatada);
                unidadeOrcamentoRepository.save(undOrcamentariaUpdate);

                return  convertUndOrcamentModelToDTo(undOrcamentariaUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Possíveis campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteUndOrcamentaria(Integer id){
        try
        {
            if (unidadeOrcamentoRepository.existsById(id))
            {
                List<LancamentoModel> lancamentos = lancamentoRepository.findAll();

                for (LancamentoModel lancamento : lancamentos ) {
                    if (!(lancamento.getId_unidade_orcamentaria().equals(id))){
                        unidadeOrcamentoRepository.deleteById(id);
                    } else {
                        throw new DataIntegrityException("Esta UND Orçamentaria esta contida em um lançamento!");
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

    public UnidadeOrcamentariaModel convertUndOrcamentFormToModel(UnidadeOrcamentariaForm unidadeOrcamentariaForm){
        UnidadeOrcamentariaModel unidadeOrcamentariaModel = new UnidadeOrcamentariaModel();
        unidadeOrcamentariaModel = modelMapper.map(unidadeOrcamentariaForm, UnidadeOrcamentariaModel.class);

        return unidadeOrcamentariaModel;
    }

    public UnidadeOrcamentariaDTo convertUndOrcamentModelToDTo(UnidadeOrcamentariaModel unidadeOrcamentariaModel){
        UnidadeOrcamentariaDTo unidadeOrcamentariaDto = new UnidadeOrcamentariaDTo();

        unidadeOrcamentariaDto.setUnd_orcamentaria_id(unidadeOrcamentariaModel.getId());
        unidadeOrcamentariaDto.setUnd_orcamentaria_codigo(unidadeOrcamentariaModel.getCodigo());
        unidadeOrcamentariaDto.setUnd_orcamentaria_nome(unidadeOrcamentariaModel.getNome());
        unidadeOrcamentariaDto.setDt_cadastro(unidadeOrcamentariaModel.getData_cadastro());
        unidadeOrcamentariaDto.setDt_alteracao(unidadeOrcamentariaModel.getData_alteracao());

        return unidadeOrcamentariaDto;
    }

    public List<UnidadeOrcamentariaDTo> convertListUndOrcamentModelToDTo(List<UnidadeOrcamentariaModel> unidadeOrcamentariaList){
        List<UnidadeOrcamentariaDTo> unidadeOrcamentariaDtoList = new ArrayList<>();

        for (UnidadeOrcamentariaModel unidadeOrc : unidadeOrcamentariaList)
        {
            UnidadeOrcamentariaDTo unidadeOrcDto = new UnidadeOrcamentariaDTo();
            unidadeOrcDto = convertUndOrcamentModelToDTo(unidadeOrc);

            unidadeOrcamentariaDtoList.add(unidadeOrcDto);
        }

        return unidadeOrcamentariaDtoList;
    }
}
