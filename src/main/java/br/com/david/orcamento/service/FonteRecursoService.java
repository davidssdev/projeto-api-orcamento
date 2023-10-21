package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.FonteRecursoModel;
import br.com.david.orcamento.repository.FonteRecursoRepository;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.FonteRecursoForm;
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
public class FonteRecursoService {

    @Autowired
    FonteRecursoRepository fonteRecursoRepository;

    public FonteRecursoModel findByIdFonte(Integer id){
        try{
            FonteRecursoModel fonte = fonteRecursoRepository.findById(id).get();
            return fonte;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<FonteRecursoModel> findAllFonte(){
        List<FonteRecursoModel> fonteList = fonteRecursoRepository.findAll();
        return fonteList;
    }

    public FonteRecursoModel insertFonte(FonteRecursoForm fonteRecursoForm){
        try{
            FonteRecursoModel novaFonte = convertFonteFormToFontModel(fonteRecursoForm);
            novaFonte = fonteRecursoRepository.save(novaFonte);
            return novaFonte;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ FonteRecursoModel.class.getName());
        }
    }

    public FonteRecursoModel updateFonte(FonteRecursoForm fonteRecursoForm, Integer id){
        try{
            Optional<FonteRecursoModel> acaoExist = fonteRecursoRepository.findById(id);
            var dtAtual = LocalDate.now();

            if(acaoExist.isPresent()){
                FonteRecursoModel fonteUpdate = acaoExist.get();
                fonteUpdate.setCodigo(fonteRecursoForm.getCodigo());
                fonteUpdate.setNome(fonteRecursoForm.getNome());
                fonteUpdate.setDataAlteracao(dtAtual);

                fonteRecursoRepository.save(fonteUpdate);
                return fonteUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteFonte(Integer id){
        try{
            if(fonteRecursoRepository.existsById(id)){
                fonteRecursoRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public FonteRecursoModel convertFonteFormToFontModel(FonteRecursoForm fonteRecursoForm){
        FonteRecursoModel convertFonte = new FonteRecursoModel();
        var dtAtual = LocalDate.now();

        convertFonte.setCodigo(fonteRecursoForm.getCodigo());
        convertFonte.setNome(fonteRecursoForm.getNome());
        convertFonte.setDataCadastro(dtAtual);

        return convertFonte;
    }
}
