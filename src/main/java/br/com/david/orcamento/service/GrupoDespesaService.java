package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.GrupoDespesaModel;
import br.com.david.orcamento.repository.GrupoDespesaRepository;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.GrupoDespesaForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GrupoDespesaService {

    @Autowired
    GrupoDespesaRepository grupoDespesaRepository;

    public GrupoDespesaModel findByIdGrupo(Integer id){
        try{
            GrupoDespesaModel grupo = grupoDespesaRepository.findById(id).get();
            return grupo;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<GrupoDespesaModel> findAllGrupo(){
        List<GrupoDespesaModel> grupoList = grupoDespesaRepository.findAll();
        return grupoList;
    }

    public GrupoDespesaModel insertGrupo(GrupoDespesaForm grupoDespesaForm){
        try{
            GrupoDespesaModel novoGrupo = convertGrupoFormToGrupoModel(grupoDespesaForm);
            novoGrupo = grupoDespesaRepository.save(novoGrupo);
            return novoGrupo;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ GrupoDespesaModel.class.getName());
        }
    }

    public GrupoDespesaModel updateGrupo(GrupoDespesaForm grupoDespesaForm, Integer id){
        try{
            Optional<GrupoDespesaModel> grupoExist = grupoDespesaRepository.findById(id);
            var dtAtual = LocalDate.now();

            if(grupoExist.isPresent()){
                GrupoDespesaModel grupoUpdate = grupoExist.get();
                grupoUpdate.setCodigo(grupoDespesaForm.getCodigo());
                grupoUpdate.setNome(grupoDespesaForm.getNome());
                grupoUpdate.setData_alteracao(dtAtual);

                grupoDespesaRepository.save(grupoUpdate);
                return grupoUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteGrupo(Integer id){
        try{
            if(grupoDespesaRepository.existsById(id)){
                grupoDespesaRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public GrupoDespesaModel convertGrupoFormToGrupoModel(GrupoDespesaForm grupoDespesaForm){
        GrupoDespesaModel convertGrupo = new GrupoDespesaModel();
        var dtAtual = LocalDate.now();

        convertGrupo.setCodigo(grupoDespesaForm.getCodigo());
        convertGrupo.setNome(grupoDespesaForm.getNome());
        convertGrupo.setData_cadastro(dtAtual);

        return convertGrupo;
    }
}
