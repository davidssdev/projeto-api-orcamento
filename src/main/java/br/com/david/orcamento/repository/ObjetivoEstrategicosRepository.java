package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.ObjetivoEstrategicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjetivoEstrategicosRepository extends JpaRepository<ObjetivoEstrategicoModel, Integer> {

    Optional<ObjetivoEstrategicoModel> findById(Integer id);

    List<ObjetivoEstrategicoModel> findByOrderByNome();

    Optional<ObjetivoEstrategicoModel> findAllByNome(String nome);
}
