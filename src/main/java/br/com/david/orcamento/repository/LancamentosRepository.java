package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.LancamentosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;


@Repository
public interface LancamentosRepository extends JpaRepository<LancamentosModel, Integer> {

    Optional<LancamentosModel> findById(Integer id);

    Optional<LancamentosModel> findByDescricao(String descricao);

    //Filtragem a partir da data de cadastro
    Optional<LancamentosModel> findByDataCadastro(Date dataCadastro);
}
