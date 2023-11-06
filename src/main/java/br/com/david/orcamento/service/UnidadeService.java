package br.com.david.orcamento.service;

import br.com.david.orcamento.model.SolicitanteModel;
import br.com.david.orcamento.model.UnidadeModel;
import br.com.david.orcamento.repository.UnidadeRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.UnidadeDTo;
import br.com.david.orcamento.rest.form.SolicitanteForm;
import br.com.david.orcamento.rest.form.UnidadeForm;
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
public class UnidadeService {

    @Autowired
    UnidadeRepository unidadeRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<UnidadeDTo> findAllUnidade(){
        List<UnidadeModel> unidadeListDto = unidadeRepository.findAll();
        return convertListUndModelToDTo(unidadeListDto);
    }

    public UnidadeDTo findByIdUnidade(Integer id){
        try
        {
            UnidadeModel unidadeDto = unidadeRepository.findById(id).get();
            return convertUndModelToDto(unidadeDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public UnidadeDTo insertUnidade(UnidadeForm unidadeForm) {
        try
        {
            UnidadeModel novaUnidade = convertUndFormToModel(unidadeForm);
            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            novaUnidade.setData_cadastro(dataAtualFormatada);
            novaUnidade = unidadeRepository.save(novaUnidade);

            return convertUndModelToDto(novaUnidade);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + UnidadeModel.class.getName());
        }
    }

    public UnidadeDTo updateUnidade(UnidadeForm unidadeForm, Integer id){
        try
        {
            Optional<UnidadeModel> unidadeExist = unidadeRepository.findById(id);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (unidadeExist.isPresent())
            {
                UnidadeModel unidadeUpdate = unidadeExist.get();
                unidadeUpdate.setNome(unidadeForm.getNome());
                unidadeUpdate.setData_alteracao(dataAtualFormatada);

                unidadeRepository.save(unidadeUpdate);
                return convertUndModelToDto(unidadeUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Possíveis campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteUnidade(Integer id){
        try
        {
            if (unidadeRepository.existsById(id))
            {
                unidadeRepository.deleteById(id);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public UnidadeModel convertUndFormToModel(UnidadeForm unidadeForm){
        UnidadeModel unidadeModel = new UnidadeModel();
        unidadeModel = modelMapper.map(unidadeForm, UnidadeModel.class);

        return unidadeModel;
    }

    public UnidadeDTo convertUndModelToDto(UnidadeModel unidadeModel){
        UnidadeDTo unidadeDto = new UnidadeDTo();

        unidadeDto.setUnidade_id(unidadeModel.getId());
        unidadeDto.setUnidade_nome(unidadeModel.getNome());
        unidadeDto.setDt_cadastro(unidadeModel.getData_cadastro());
        unidadeDto.setDt_alteracao(unidadeModel.getData_alteracao());

        return unidadeDto;
    }

    public List<UnidadeDTo> convertListUndModelToDTo(List<UnidadeModel> unidadeModelList){
        List<UnidadeDTo> unidadeDtoList = new ArrayList<>();

        for (UnidadeModel unidade : unidadeModelList)
        {
            UnidadeDTo unidadeDto = new UnidadeDTo();
            unidadeDto = convertUndModelToDto(unidade);

            unidadeDtoList.add(unidadeDto);
        }

        return unidadeDtoList;
    }

}
