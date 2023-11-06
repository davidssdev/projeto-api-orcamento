package br.com.david.orcamento.service;

import br.com.david.orcamento.model.ProgramaModel;
import br.com.david.orcamento.repository.ProgramaRepository;
import br.com.david.orcamento.rest.form.ProgramaForm;
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
public class ProgramaService {

    @Autowired
    ProgramaRepository programaRepository;

    public List<ProgramaModel> findAllPrograma(){
        List<ProgramaModel> programaList = programaRepository.findAll();
        return programaList;
    }

    public ProgramaModel findByIdPrograma(Integer id){
        try{
            ProgramaModel programa = programaRepository.findById(id).get();
            return programa;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public ProgramaModel insertPrograma(ProgramaForm programaForm){
        try{
            ProgramaModel novoPrograma = convertProgFormProgModel(programaForm);
            novoPrograma = programaRepository.save(novoPrograma);
            return novoPrograma;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ ProgramaModel.class.getName());
        }
    }

    public ProgramaModel updatePrograma(ProgramaForm programaForm, Integer id){
        try{
            Optional<ProgramaModel> programaExist = programaRepository.findById(id);
            var dtAtual = LocalDate.now();

            if(programaExist.isPresent()){
                ProgramaModel programaUpdate = programaExist.get();

                programaUpdate.setCodigo(programaForm.getCodigo());
                programaUpdate.setNome(programaForm.getNome());
                programaUpdate.setData_alteracao(dtAtual);

                programaRepository.save(programaUpdate);
                return programaUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deletePrograma(Integer id){
        try{
            if(programaRepository.existsById(id)){
                programaRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public ProgramaModel convertProgFormProgModel(ProgramaForm programaForm){
        ProgramaModel convertPrograma = new ProgramaModel();
        var dtAtual = LocalDate.now();

        convertPrograma.setCodigo(programaForm.getCodigo());
        convertPrograma.setNome(programaForm.getNome());
        convertPrograma.setData_cadastro(dtAtual);

        return convertPrograma;
    }
}
