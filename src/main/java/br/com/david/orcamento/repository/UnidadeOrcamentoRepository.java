package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.UnidadeOrcamentariaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadeOrcamentoRepository extends JpaRepository<UnidadeOrcamentariaModel, Integer> {

    Optional<UnidadeOrcamentariaModel> findById(Integer id);

    List<UnidadeOrcamentariaModel> findByOrderByNome();

    Optional<UnidadeOrcamentariaModel> findAllByCodigo(Integer integer);
}
