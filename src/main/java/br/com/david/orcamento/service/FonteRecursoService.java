package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.FonteRecursoModel;
import br.com.david.orcamento.repository.FonteRecursoRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.FonteRecursoDTo;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.FonteRecursoForm;
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
public class FonteRecursoService {

    @Autowired
    FonteRecursoRepository fonteRecursoRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<FonteRecursoDTo> findAllFonte(){
        List<FonteRecursoModel> fonteListDto = fonteRecursoRepository.findAll();

        return convertListFonteModelToDTo(fonteListDto);
    }

    public FonteRecursoDTo findByIdFonte(Integer id){
        try
        {
            FonteRecursoModel fonteDto = fonteRecursoRepository.findById(id).get();

            return convertFonteModelToDTo(fonteDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public FonteRecursoDTo insertFonte(FonteRecursoForm fonteRecursoForm){
        try
        {
            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            FonteRecursoModel novaFonte = convertFonteFormToModel(fonteRecursoForm);
            novaFonte.setData_cadastro(dataAtualFormatada);
            novaFonte = fonteRecursoRepository.save(novaFonte);

            return convertFonteModelToDTo(novaFonte);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + FonteRecursoModel.class.getName());
        }
    }

    public FonteRecursoDTo updateFonte(FonteRecursoForm fonteRecursoForm, Integer id){
        try
        {
            Optional<FonteRecursoModel> acaoExist = fonteRecursoRepository.findById(id);
            DataFormato data = new DataFormato();

            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (acaoExist.isPresent())
            {
                FonteRecursoModel fonteUpdate = acaoExist.get();
                fonteUpdate.setCodigo(fonteRecursoForm.getCodigo());
                fonteUpdate.setNome(fonteRecursoForm.getNome());
                fonteUpdate.setData_alteracao(dataAtualFormatada);

                fonteRecursoRepository.save(fonteUpdate);

                return convertFonteModelToDTo(fonteUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw  new ObjectNotFoundException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteFonte(Integer id){
        try
        {
            if (fonteRecursoRepository.existsById(id))
            {
                fonteRecursoRepository.deleteById(id);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public FonteRecursoModel convertFonteFormToModel(FonteRecursoForm fonteRecursoForm){
       FonteRecursoModel convertFonte = new FonteRecursoModel();
       convertFonte = modelMapper.map(fonteRecursoForm, FonteRecursoModel.class);

       return convertFonte;
    }

    public FonteRecursoDTo convertFonteModelToDTo(FonteRecursoModel fonteRecursoModel){
        FonteRecursoDTo fonteRecursoDto = new FonteRecursoDTo();

        fonteRecursoDto.setFonte_rescurso_id(fonteRecursoModel.getId());
        fonteRecursoDto.setFonte_recurso_codigo(fonteRecursoModel.getCodigo());
        fonteRecursoDto.setFonte_recurso_nome(fonteRecursoModel.getNome());
        fonteRecursoDto.setDt_cadastro(fonteRecursoModel.getData_cadastro());
        fonteRecursoDto.setDt_alteracao(fonteRecursoModel.getData_alteracao());

        return fonteRecursoDto;
    }

    public List<FonteRecursoDTo> convertListFonteModelToDTo(List<FonteRecursoModel> listFonteRecurso){
        List<FonteRecursoDTo> fonteRecursoDtoList = new ArrayList<>();

        for (FonteRecursoModel fonte: listFonteRecurso)
        {
            FonteRecursoDTo fonteDto = new FonteRecursoDTo();
            fonteDto = convertFonteModelToDTo(fonte);

            fonteRecursoDtoList.add(fonteDto);
        }

        return fonteRecursoDtoList;
    }
}
