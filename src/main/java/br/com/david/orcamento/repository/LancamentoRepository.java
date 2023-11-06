package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.LancamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


@Repository
public interface LancamentoRepository extends JpaRepository<LancamentoModel, Integer> {

    Optional<LancamentoModel> findByDescricao(String descricao);

    Optional<LancamentoModel> findByNumeroLancamento(Integer numero_lancamento);

}
