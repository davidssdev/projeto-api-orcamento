package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.FonteRecursoModel;
import br.com.david.orcamento.model.GrupoDespesaModel;
import br.com.david.orcamento.repository.GrupoDespesaRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.FonteRecursoDTo;
import br.com.david.orcamento.rest.dto.GrupoDespesaDTo;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.FonteRecursoForm;
import br.com.david.orcamento.rest.form.GrupoDespesaForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import io.swagger.models.auth.In;
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
public class GrupoDespesaService {

    @Autowired
    GrupoDespesaRepository grupoDespesaRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<GrupoDespesaDTo> findAllGrupo(){
        List<GrupoDespesaModel> grupoListDto = grupoDespesaRepository.findAll();

        return convertListGrupoModelToDTo(grupoListDto);
    }

    public GrupoDespesaDTo findByIdGrupo(Integer id){
        try
        {
            GrupoDespesaModel grupoDto = grupoDespesaRepository.findById(id).get();

            return convertGrupoModelToDTo(grupoDto);
        }catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public GrupoDespesaDTo insertGrupo(GrupoDespesaForm grupoDespesaForm){
        try
        {
            GrupoDespesaModel novoGrupo = convertGrupoFormToModel(grupoDespesaForm);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            novoGrupo.setData_cadastro(dataAtualFormatada);
            novoGrupo = grupoDespesaRepository.save(novoGrupo);

            return convertGrupoModelToDTo(novoGrupo);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + GrupoDespesaModel.class.getName());
        }
    }

    public GrupoDespesaDTo updateGrupo(GrupoDespesaForm grupoDespesaForm, Integer id){
        try
        {
            Optional<GrupoDespesaModel> grupoExist = grupoDespesaRepository.findById(id);
            DataFormato data = new DataFormato();

            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (grupoExist.isPresent())
            {
                GrupoDespesaModel grupoUpdate = grupoExist.get();
                grupoUpdate.setCodigo(grupoDespesaForm.getCodigo());
                grupoUpdate.setNome(grupoDespesaForm.getNome());
                grupoUpdate.setData_alteracao(dataAtualFormatada);

                grupoDespesaRepository.save(grupoUpdate);
                return convertGrupoModelToDTo(grupoUpdate);
            } else
            {
                throw new DataIntegrityException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteGrupo(Integer id){
        try
        {
            if (grupoDespesaRepository.existsById(id))
            {
                grupoDespesaRepository.deleteById(id);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public GrupoDespesaModel convertGrupoFormToModel(GrupoDespesaForm grupoDespesaForm){
        GrupoDespesaModel convertGrupo = new GrupoDespesaModel();
        convertGrupo = modelMapper.map(grupoDespesaForm, GrupoDespesaModel.class);

        return convertGrupo;
    }

    public GrupoDespesaDTo convertGrupoModelToDTo(GrupoDespesaModel grupoDespesaModel){
        GrupoDespesaDTo grupoDespesaDto = new GrupoDespesaDTo();

        grupoDespesaDto.setGrupo_despesa_id(grupoDespesaModel.getId());
        grupoDespesaDto.setGrupo_desp_codigo(grupoDespesaModel.getCodigo());
        grupoDespesaDto.setGrupo_desp_nome(grupoDespesaModel.getNome());
        grupoDespesaDto.setDt_cadastro(grupoDespesaModel.getData_cadastro());
        grupoDespesaDto.setDt_alteracao(grupoDespesaModel.getData_alteracao());

        return grupoDespesaDto;
    }

    public List<GrupoDespesaDTo> convertListGrupoModelToDTo(List<GrupoDespesaModel> listGrupoDespesa){
        List<GrupoDespesaDTo> grupoDespesaListDto = new ArrayList<>();

        for (GrupoDespesaModel grupo: listGrupoDespesa)
        {
            GrupoDespesaDTo grupoDto = new GrupoDespesaDTo();
            grupoDto = convertGrupoModelToDTo(grupo);

            grupoDespesaListDto.add(grupoDto);
        }

        return grupoDespesaListDto;
    }
}
