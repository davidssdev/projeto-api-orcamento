package br.com.david.orcamento.service;

import br.com.david.orcamento.model.SolicitanteModel;
import br.com.david.orcamento.repository.SolicitanteRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.SolicitanteDTo;
import br.com.david.orcamento.rest.form.SolicitanteForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SolicitanteService {

    @Autowired
    SolicitanteRepository solicitanteRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<SolicitanteDTo> findAllSolicitante(){
        List<SolicitanteModel> solicitanteListDto = solicitanteRepository.findAll();
        return convertListSolicModelToDTo(solicitanteListDto);
    }

    public SolicitanteDTo findByIdSolicitante(Integer id){
        try
        {
            SolicitanteModel solicitanteDto = solicitanteRepository.findById(id).get();
            return convertSolicModelToDTo(solicitanteDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public SolicitanteDTo insertSolicitante(SolicitanteForm solicitanteForm) {
        try
        {
            SolicitanteModel novoSolicitante = convertSolicFormToModel(solicitanteForm);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            novoSolicitante.setData_cadastro(dataAtualFormatada);
            novoSolicitante = solicitanteRepository.save(novoSolicitante);

            return convertSolicModelToDTo(novoSolicitante);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + SolicitanteModel.class.getName());
        }
    }

    public SolicitanteDTo updateSolicitante(SolicitanteForm solicitanteForm, Integer id){
        try
        {
            Optional<SolicitanteModel> solicitanteExist = solicitanteRepository.findById(id);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (solicitanteExist.isPresent())
            {
                SolicitanteModel solicitanteUpdate = solicitanteExist.get();
                solicitanteUpdate.setNome(solicitanteForm.getNome());
                solicitanteUpdate.setData_alteracao(dataAtualFormatada);

                solicitanteRepository.save(solicitanteUpdate);
                return convertSolicModelToDTo(solicitanteUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteSolicitante(Integer id){
        try
        {
            if(solicitanteRepository.existsById(id))
            {
                solicitanteRepository.deleteById(id);
            }else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public SolicitanteModel convertSolicFormToModel(SolicitanteForm solicitanteForm){
        SolicitanteModel solicitanteModel = new SolicitanteModel();
        solicitanteModel = modelMapper.map(solicitanteForm, SolicitanteModel.class);

        return solicitanteModel;
    }

    public SolicitanteDTo convertSolicModelToDTo(SolicitanteModel solicitanteModel){
        SolicitanteDTo solicitanteDto = new SolicitanteDTo();

        solicitanteDto.setSolicitante_id(solicitanteModel.getId());
        solicitanteDto.setSolicitante_nome(solicitanteModel.getNome());
        solicitanteDto.setDt_cadastro(solicitanteModel.getData_cadastro());
        solicitanteDto.setDt_alteracao(solicitanteModel.getData_alteracao());

        return solicitanteDto;
    }

    public List<SolicitanteDTo> convertListSolicModelToDTo(List<SolicitanteModel> solicitanteList){
        List<SolicitanteDTo> solicitanteDtoList = new ArrayList<>();

        for (SolicitanteModel solicitante : solicitanteList)
        {
            SolicitanteDTo solicitanteDto = new SolicitanteDTo();
            solicitanteDto = convertSolicModelToDTo(solicitante);

            solicitanteDtoList.add(solicitanteDto);
        }

        return solicitanteDtoList;
    }
}
