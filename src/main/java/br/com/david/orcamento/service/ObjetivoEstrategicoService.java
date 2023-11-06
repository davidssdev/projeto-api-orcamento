package br.com.david.orcamento.service;

import br.com.david.orcamento.model.ObjetivoEstrategicoModel;
import br.com.david.orcamento.repository.ObjetivoEstrategicosRepository;
import br.com.david.orcamento.rest.components.DataFormato;
import br.com.david.orcamento.rest.dto.ObjetivoEstrategicoDTo;
import br.com.david.orcamento.rest.form.ObjetivoEstrategicoForm;
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
public class ObjetivoEstrategicoService {

    @Autowired
    ObjetivoEstrategicosRepository objetivoEstrategicosRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<ObjetivoEstrategicoDTo> findAllObjetivo(){
        List<ObjetivoEstrategicoModel> objetivosListDto = objetivoEstrategicosRepository.findAll();
        return convertListObjetivModelToDTo(objetivosListDto);
    }

    public ObjetivoEstrategicoDTo findByIdObjetivo(Integer id){
        try
        {
            ObjetivoEstrategicoModel objetivoDto = objetivoEstrategicosRepository.findById(id).get();
            return convertObjetivModelToDTo(objetivoDto);
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
        }
    }

    public ObjetivoEstrategicoDTo insertObjetivo(ObjetivoEstrategicoForm objetivosEstrategicosForm){
        try
        {
            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            ObjetivoEstrategicoModel novoObjetivo = convertObjetivFormToModel(objetivosEstrategicosForm);
            novoObjetivo.setData_cadastro(dataAtualFormatada);
            novoObjetivo = objetivoEstrategicosRepository.save(novoObjetivo);

            return convertObjetivModelToDTo(novoObjetivo);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: " + ObjetivoEstrategicoModel.class.getName());
        }
    }

    public ObjetivoEstrategicoDTo updateObjetivo(ObjetivoEstrategicoForm objetivosEstrategicosForm, Integer id){
        try
        {
            Optional<ObjetivoEstrategicoModel> objetivosExist = objetivoEstrategicosRepository.findById(id);

            DataFormato data = new DataFormato();
            LocalDateTime dtAtual = LocalDateTime.now();
            LocalDateTime dataAtualFormatada = data.formatarData(dtAtual);

            if (objetivosExist.isPresent())
            {
                ObjetivoEstrategicoModel objetivosUpdate = objetivosExist.get();
                objetivosUpdate.setNome(objetivosEstrategicosForm.getNome());
                objetivosUpdate.setData_alteracao(dataAtualFormatada);
                objetivoEstrategicosRepository.save(objetivosUpdate);

                return convertObjetivModelToDTo(objetivosUpdate);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (DataIntegrityViolationException e)
        {
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteObjetivo(Integer id){
        try
        {
            if (objetivoEstrategicosRepository.existsById(id))
            {
                objetivoEstrategicosRepository.deleteById(id);
            } else
            {
                throw new ObjectNotFoundException("Códido de ID: " + id + " não encontrado!");
            }
        }catch (NoSuchElementException e)
        {
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public ObjetivoEstrategicoModel convertObjetivFormToModel(ObjetivoEstrategicoForm objetivoEstrategicoForm){
        ObjetivoEstrategicoModel objetivoEstrategicoModel = new ObjetivoEstrategicoModel();
        objetivoEstrategicoModel = modelMapper.map(objetivoEstrategicoForm, ObjetivoEstrategicoModel.class);

        return objetivoEstrategicoModel;
    }

    public ObjetivoEstrategicoDTo convertObjetivModelToDTo(ObjetivoEstrategicoModel objetivoEstrategicoModel){
        ObjetivoEstrategicoDTo objetivoEstrategicoDTo = new ObjetivoEstrategicoDTo();

        objetivoEstrategicoDTo.setObjetivo_id(objetivoEstrategicoModel.getId());
        objetivoEstrategicoDTo.setObjetivo_nome(objetivoEstrategicoModel.getNome());
        objetivoEstrategicoDTo.setDt_cadastro(objetivoEstrategicoModel.getData_cadastro());
        objetivoEstrategicoDTo.setDt_alteracao(objetivoEstrategicoModel.getData_alteracao());

        return objetivoEstrategicoDTo;
    }

    public List<ObjetivoEstrategicoDTo> convertListObjetivModelToDTo(List<ObjetivoEstrategicoModel> objetivoEstrategicoList){
        List<ObjetivoEstrategicoDTo> objetivoEstrategicoListDTo = new ArrayList<>();

        for (ObjetivoEstrategicoModel objetivo : objetivoEstrategicoList)
        {
            ObjetivoEstrategicoDTo objetivoDTo = new ObjetivoEstrategicoDTo();
            objetivoDTo = convertObjetivModelToDTo(objetivo);

            objetivoEstrategicoListDTo.add(objetivoDTo);
        }

        return objetivoEstrategicoListDTo;
    }
}
