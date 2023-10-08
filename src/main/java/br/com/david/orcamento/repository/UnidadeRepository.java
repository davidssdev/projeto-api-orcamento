package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.UnidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadeRepository extends JpaRepository<UnidadeModel, Integer> {

    Optional<UnidadeModel> findById(Integer id);

    List<UnidadeModel> findByOrderByNome();

    Optional<UnidadeModel> findAllByNome(String nome);
}
