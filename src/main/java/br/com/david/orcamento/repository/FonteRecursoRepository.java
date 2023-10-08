package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.FonteRecursoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FonteRecursoRepository extends JpaRepository<FonteRecursoModel, Integer> {

    Optional<FonteRecursoModel> findById(Integer id);

    List<FonteRecursoModel> findAllByOrderByNome();

    Optional<FonteRecursoModel> findAllByCodigo(Integer codigo);

}
