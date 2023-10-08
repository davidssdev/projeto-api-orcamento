package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.ModalidadeAplicacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModalidadeAplicacaoRepository extends JpaRepository<ModalidadeAplicacaoModel, Integer> {

    Optional<ModalidadeAplicacaoModel> findById(Integer id);

    List<ModalidadeAplicacaoModel> findByOrderByNome();

    Optional<ModalidadeAplicacaoModel> findAllByCodigo(Integer codigo);
}
