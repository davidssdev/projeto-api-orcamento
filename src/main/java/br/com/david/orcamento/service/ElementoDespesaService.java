package br.com.david.orcamento.service;

import br.com.david.orcamento.model.ElementoDespesaModel;
import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.repository.ElementoDespesaRepository;
import br.com.david.orcamento.repository.LancamentoRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.ElementoDespesaDTo;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.ElementoDespesaForm;
import br.com.david.orcamento.service.exceptions.DataIntegrityException;
import br.com.david.orcamento.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ElementoDespesaService {

    @Autowired
    ElementoDespesaRepository elementoDespesaRepository;
    @Autowired
    LancamentoRepository lancamentoRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ElementoDespesaDTo> findAllElemento(){
        List<ElementoDespesaModel> elementoListDto = elementoDespesaRepository.findAll();

        return convertListElementoModelToDTo(elementoListDto);
    }

    public ElementoDespesaDTo findByIdElemento(Integer id){
        try
        {
            ElementoDespesaModel elementoDto = elementoDespesaRepository.findById(id).get();

            return convertElementoModelToDTo(elementoDto);
        }catch (NoSuchElementException e )
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public ElementoDespesaDTo insertElemento(ElementoDespesaForm elementoDespesaForm){
        try
        {
            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            ElementoDespesaModel novoElemento = convertElementoFormToModel(elementoDespesaForm);
            novoElemento.setData_cadastro(dataAtualFormatada);
            novoElemento = elementoDespesaRepository.save(novoElemento);

            return convertElementoModelToDTo(novoElemento);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + ElementoDespesaModel.class.getName());
        }
    }

    public ElementoDespesaDTo updateElemento(ElementoDespesaForm elementoDespesaForm, Integer id){
        try
        {
            Optional<ElementoDespesaModel> elementoExist = elementoDespesaRepository.findById(id);
            DataFormato data = new DataFormato();

            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (elementoExist.isPresent())
            {
                ElementoDespesaModel elementoUpdate = elementoExist.get();
                elementoUpdate.setCodigo(elementoDespesaForm.getCodigo());
                elementoUpdate.setNome(elementoDespesaForm.getNome());
                elementoUpdate.setData_alteracao(dataAtualFormatada);

                elementoDespesaRepository.save(elementoUpdate);
                return convertElementoModelToDTo(elementoUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteElemento(Integer id){
        try
        {
            if (elementoDespesaRepository.existsById(id))
            {
                List<LancamentoModel> lancamentos = lancamentoRepository.findAll();

                for (LancamentoModel lancamento : lancamentos ) {
                    if (!(lancamento.getId_elemento_despesa().equals(id))){
                        elementoDespesaRepository.deleteById(id);
                    } else {
                        throw new DataIntegrityException("Este elemento esta contido em um lançamento!");
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

    public ElementoDespesaModel convertElementoFormToModel (ElementoDespesaForm elementoDespesaForm){
        ElementoDespesaModel elementoModel = new ElementoDespesaModel();
        elementoModel = modelMapper.map(elementoDespesaForm, ElementoDespesaModel.class);

        return elementoModel;
    }

    public ElementoDespesaDTo convertElementoModelToDTo(ElementoDespesaModel elementoDespesaModel){
        ElementoDespesaDTo elementoDespesaDto = new ElementoDespesaDTo();

        elementoDespesaDto.setElemento_despesa_id(elementoDespesaModel.getId());
        elementoDespesaDto.setElemento_codigo(elementoDespesaModel.getCodigo());
        elementoDespesaDto.setElemento_nome(elementoDespesaModel.getNome());
        elementoDespesaDto.setDt_cadastro(elementoDespesaModel.getData_cadastro());
        elementoDespesaDto.setDt_alteracao(elementoDespesaModel.getData_alteracao());

        return elementoDespesaDto;
    }

    public List<ElementoDespesaDTo> convertListElementoModelToDTo(List<ElementoDespesaModel> elementoDespesaModelList){
        List<ElementoDespesaDTo> elementoDespesaDtoList = new ArrayList<>();

        for (ElementoDespesaModel elemento: elementoDespesaModelList)
        {
            ElementoDespesaDTo elementoDespesaDto = new ElementoDespesaDTo();
            elementoDespesaDto = convertElementoModelToDTo(elemento);

            elementoDespesaDtoList.add(elementoDespesaDto);
        }

        return elementoDespesaDtoList;
    }
}
