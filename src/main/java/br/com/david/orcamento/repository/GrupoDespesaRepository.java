package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.GrupoDespesaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoDespesaRepository extends JpaRepository<GrupoDespesaModel, Integer> {

    Optional<GrupoDespesaModel> findById(Integer id);

    List<GrupoDespesaModel> findByOrderByNome();

    Optional<GrupoDespesaModel> findAllByCodigo(Double codigo);
}
