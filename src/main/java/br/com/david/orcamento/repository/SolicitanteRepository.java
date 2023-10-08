package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.SolicitanteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitanteRepository extends JpaRepository<SolicitanteModel, Integer> {

    Optional<SolicitanteModel> findById(Integer id);

    List<SolicitanteModel> findByOrderByNome();

    //Buscando através do nome
    Optional<SolicitanteModel> findAllByNome(String nome);
}
