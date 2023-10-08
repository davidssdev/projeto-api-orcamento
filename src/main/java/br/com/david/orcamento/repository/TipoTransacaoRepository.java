package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.TipoTransacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoTransacaoRepository extends JpaRepository<TipoTransacaoModel, Integer> {

    Optional<TipoTransacaoModel> findById(Integer id);

    List<TipoTransacaoModel> findByOrderByNome();

    Optional<TipoTransacaoModel> findAllByNome(String nome);
}
