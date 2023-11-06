package br.com.david.orcamento.service;

import br.com.david.orcamento.model.TipoLancamentoModel;
import br.com.david.orcamento.model.TipoTransacaoModel;
import br.com.david.orcamento.repository.TipoTransacaoRepository;
import br.com.david.orcamento.rest.form.TipoLancamentoForm;
import br.com.david.orcamento.rest.form.TipoTransacaoForm;
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
public class TipoTransacaoService {

    @Autowired
    TipoTransacaoRepository tipoTransacaoRepository;

    public TipoTransacaoModel findByIdTipoTransacao(Integer id){
        try{
            TipoTransacaoModel tipoTransacao = tipoTransacaoRepository.findById(id).get();
            return tipoTransacao;
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
    }

    public List<TipoTransacaoModel> findAllTipoTransacao(){
        List<TipoTransacaoModel> tipoTransacaoList = tipoTransacaoRepository.findAll();
        return tipoTransacaoList;
    }

    public TipoTransacaoModel insertTipoTransacao(TipoTransacaoForm tipoTransacaoForm) {
        try{
            TipoTransacaoModel novoTipoTransacao= convertTipoTransFormToTipoTransModel(tipoTransacaoForm);
            novoTipoTransacao = tipoTransacaoRepository.save(novoTipoTransacao);
            return novoTipoTransacao;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro ao tentar realizar o cadastro do tipo: "+ TipoTransacaoModel.class.getName());
        }
    }

    public TipoTransacaoModel updateTipoTransacao(TipoTransacaoForm tipoTransacaoForm, Integer id){
        try{
            Optional<TipoTransacaoModel> tipoTransacaoExist = tipoTransacaoRepository.findById(id);
            var dtAtual = LocalDate.now();

            if(tipoTransacaoExist.isPresent()){
                TipoTransacaoModel tipoTransacaoUpdate = tipoTransacaoExist.get();
                tipoTransacaoUpdate.setNome(tipoTransacaoForm.getNome());
                tipoTransacaoUpdate.setData_alteracao(dtAtual);

                tipoTransacaoRepository.save(tipoTransacaoUpdate);
                return tipoTransacaoUpdate;
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
        }
    }

    public void deleteTipoTransacao(Integer id){
        try{
            if(tipoTransacaoRepository.existsById(id)){
                tipoTransacaoRepository.deleteById(id);
            }else {
                throw new DataIntegrityException("Códido de ID: "+id+" não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Objeto não encontrado!");
        }
    }

    public TipoTransacaoModel convertTipoTransFormToTipoTransModel(TipoTransacaoForm tipoTransacaoForm){
        TipoTransacaoModel convertTipoTransacao= new TipoTransacaoModel();
        var dtAtual = LocalDate.now();

        convertTipoTransacao.setNome(tipoTransacaoForm.getNome());
        convertTipoTransacao.setData_cadastro(dtAtual);

        return convertTipoTransacao;
    }
}
