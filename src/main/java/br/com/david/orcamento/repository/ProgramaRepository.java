package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.ProgramaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramaRepository extends JpaRepository<ProgramaModel, Integer> {

    Optional<ProgramaModel> findById(Integer id);

    List<ProgramaModel> findByOrderByNome();

    Optional<ProgramaModel> findAllByCodigo(Integer codigo);
}
