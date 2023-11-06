package br.com.david.orcamento.service;

import br.com.david.orcamento.model.AcaoModel;
import br.com.david.orcamento.model.ModalidadeAplicacaoModel;
import br.com.david.orcamento.repository.ModalidadeAplicacaoRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.ModalidadeAplicacaoDTo;
import br.com.david.orcamento.rest.form.AcaoForm;
import br.com.david.orcamento.rest.form.ModalidadeAplicacaoForm;
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
public class ModalidadeAplicacaoService {

    @Autowired
    ModalidadeAplicacaoRepository modalidadeAplicacaoRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<ModalidadeAplicacaoDTo> findAllModalidade(){
        List<ModalidadeAplicacaoModel> modalidadeList = modalidadeAplicacaoRepository.findAll();

        return convertListModModelToDTo(modalidadeList);
    }

    public ModalidadeAplicacaoDTo findByIdModalidade(Integer id){
        try
        {
            ModalidadeAplicacaoModel modalidade = modalidadeAplicacaoRepository.findById(id).get();
            return convertModalidModelToDTo(modalidade);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public ModalidadeAplicacaoDTo insertModalidade(ModalidadeAplicacaoForm modalidadeAplicacaoForm){
        try
        {
            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            ModalidadeAplicacaoModel novaModalidade = convertModalidFormToModel(modalidadeAplicacaoForm);

            novaModalidade.setData_cadastro(dataAtualFormatada);
            novaModalidade = modalidadeAplicacaoRepository.save(novaModalidade);

            return convertModalidModelToDTo(novaModalidade);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + ModalidadeAplicacaoModel.class.getName());
        }
    }

    public ModalidadeAplicacaoDTo updateModalidade(ModalidadeAplicacaoForm modalidadeAplicacaoForm, Integer id){
        try {
            Optional<ModalidadeAplicacaoModel> modalidadeExist = modalidadeAplicacaoRepository.findById(id);
            DataFormato data = new DataFormato();

            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (modalidadeExist.isPresent())
            {
                ModalidadeAplicacaoModel modalidadeUpdate = modalidadeExist.get();

                modalidadeUpdate.setCodigo(modalidadeAplicacaoForm.getCodigo());
                modalidadeUpdate.setNome(modalidadeAplicacaoForm.getNome());
                modalidadeUpdate.setData_alteracao(dataAtualFormatada);

                modalidadeAplicacaoRepository.save(modalidadeUpdate);
                return convertModalidModelToDTo(modalidadeUpdate);
            }else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteModalidade(Integer id){
        try
        {
            if(modalidadeAplicacaoRepository.existsById(id))
            {
                modalidadeAplicacaoRepository.deleteById(id);
            }else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public ModalidadeAplicacaoModel convertModalidFormToModel(ModalidadeAplicacaoForm modalidadeAplicacaoForm){
        ModalidadeAplicacaoModel modaliadeModel = modelMapper.map(modalidadeAplicacaoForm, ModalidadeAplicacaoModel.class);

        return modaliadeModel;
    }

    public ModalidadeAplicacaoDTo convertModalidModelToDTo(ModalidadeAplicacaoModel modalidadeAplicacaoModel){
        ModalidadeAplicacaoDTo modalidadeDTo = new ModalidadeAplicacaoDTo();

        modalidadeDTo.setModalidade_id(modalidadeAplicacaoModel.getId());
        modalidadeDTo.setModalidade_codigo(modalidadeAplicacaoModel.getCodigo());
        modalidadeDTo.setModalidade_nome(modalidadeAplicacaoModel.getNome());
        modalidadeDTo.setDt_cadastro(modalidadeAplicacaoModel.getData_cadastro());
        modalidadeDTo.setDt_alteracao(modalidadeAplicacaoModel.getData_alteracao());

        return modalidadeDTo;
    }

    public List<ModalidadeAplicacaoDTo> convertListModModelToDTo(List<ModalidadeAplicacaoModel> modalidadeList){
        List<ModalidadeAplicacaoDTo> modalidadeListDTo = new ArrayList<>();

        for (ModalidadeAplicacaoModel modalidade: modalidadeList)
        {
            ModalidadeAplicacaoDTo modalidadeDTo = new ModalidadeAplicacaoDTo();
            modalidadeDTo = convertModalidModelToDTo(modalidade);

            modalidadeListDTo.add(modalidadeDTo);
        }

        return modalidadeListDTo;
    }
}
