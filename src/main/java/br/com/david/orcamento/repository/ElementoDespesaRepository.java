package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.ElementoDespesaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementoDespesaRepository extends JpaRepository<ElementoDespesaModel, Integer> {

    Optional<ElementoDespesaModel> findById(Integer id);

    List<ElementoDespesaModel> findAllByOrderByNomeDesc();
    Optional<ElementoDespesaModel> findAllByCodigo(Integer codigo);
}
