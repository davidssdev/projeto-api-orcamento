package br.com.david.orcamento.service;

import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.model.ProgramaModel;
import br.com.david.orcamento.repository.LancamentoRepository;
import br.com.david.orcamento.repository.ProgramaRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.ProgramaDTo;
import br.com.david.orcamento.rest.form.ProgramaForm;
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
public class ProgramaService {

    @Autowired
    ProgramaRepository programaRepository;
    @Autowired
    LancamentoRepository lancamentoRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ProgramaDTo> findAllPrograma(){
        List<ProgramaModel> programaListDto = programaRepository.findAll();
        return convertListProgramModelToDTo(programaListDto);
    }

    public ProgramaDTo findByIdPrograma(Integer id){
        try
        {
            ProgramaModel programaDto = programaRepository.findById(id).get();
            return convertProgramModelToDTo(programaDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public ProgramaDTo insertPrograma(ProgramaForm programaForm){
        try
        {
            ProgramaModel novoPrograma = convertProgramFormToModel(programaForm);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            novoPrograma.setData_cadastro(dataAtualFormatada);
            novoPrograma = programaRepository.save(novoPrograma);

            return convertProgramModelToDTo(novoPrograma);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + ProgramaModel.class.getName());
        }
    }

    public ProgramaDTo updatePrograma(ProgramaForm programaForm, Integer id){
        try
        {
            Optional<ProgramaModel> programaExist = programaRepository.findById(id);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (programaExist.isPresent())
            {
                ProgramaModel programaUpdate = programaExist.get();

                programaUpdate.setCodigo(programaForm.getCodigo());
                programaUpdate.setNome(programaForm.getNome());
                programaUpdate.setData_alteracao(dataAtualFormatada);
                programaRepository.save(programaUpdate);

                return convertProgramModelToDTo(programaUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Possíveis campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deletePrograma(Integer id){
        try
        {
            if (programaRepository.existsById(id))
            {
                List<LancamentoModel> lancamentos = lancamentoRepository.findAll();

                for (LancamentoModel lancamento : lancamentos ) {
                    if (!(lancamento.getId_programa().equals(id))){
                        programaRepository.deleteById(id);
                    } else {
                        throw new DataIntegrityException("Este programa esta contido em um lançamento!");
                    }
                }

            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public ProgramaModel convertProgramFormToModel(ProgramaForm programaForm){
        ProgramaModel programaModel = new ProgramaModel();
        programaModel = modelMapper.map(programaForm, ProgramaModel.class);

        return programaModel;
    }

    public ProgramaDTo convertProgramModelToDTo(ProgramaModel programaModel){
        ProgramaDTo programaDTo = new ProgramaDTo();

        programaDTo.setPrograma_id(programaModel.getId());
        programaDTo.setPrograma_codigo(programaModel.getCodigo());
        programaDTo.setPrograma_nome(programaModel.getNome());
        programaDTo.setDt_cadastro(programaModel.getData_cadastro());
        programaDTo.setDt_alteracao(programaModel.getData_alteracao());

        return programaDTo;
    }

    public List<ProgramaDTo> convertListProgramModelToDTo(List<ProgramaModel> programaList){
        List<ProgramaDTo> programaListDTo = new ArrayList<>();

        for (ProgramaModel programa : programaList)
        {
            ProgramaDTo programaDTo = new ProgramaDTo();
            programaDTo = convertProgramModelToDTo(programa);

            programaListDTo.add(programaDTo);
        }

        return programaListDTo;
    }
}
