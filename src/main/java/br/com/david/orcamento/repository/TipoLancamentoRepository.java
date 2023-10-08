package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.TipoLancamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoLancamentoRepository extends JpaRepository<TipoLancamentoModel, Integer> {

    Optional<TipoLancamentoModel> findById(Integer id);

    List<TipoLancamentoModel> findByOrderByNome();

    //Buscando através do nome
    Optional<TipoLancamentoModel> findAllByNome(String nome);
}

