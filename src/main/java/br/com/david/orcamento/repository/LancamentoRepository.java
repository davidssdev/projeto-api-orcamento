package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.LancamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;


@Repository
public interface LancamentoRepository extends JpaRepository<LancamentoModel, Integer> {

    Optional<LancamentoModel> findById(Integer id);

    Optional<LancamentoModel> findByDescricao(String descricao);

    //Filtragem a partir da data de cadastro
    Optional<LancamentoModel> findByDataCadastro(Date dataCadastro);

    Optional<LancamentoModel> findByAnoOrcamento(Integer anoOrcamento);
}
