package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.LancamentoModel;
import br.com.david.orcamento.rest.dto.LancamentoDTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface LancamentoRepository extends JpaRepository<LancamentoModel, Integer> {

    Optional<LancamentoModel> findByDescricao(String descricao);

}
