package br.com.david.orcamento.repository;

import br.com.david.orcamento.model.AcaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcaoRepository extends JpaRepository<AcaoModel, Integer> {

    //Seleciona a acao apartir do seu ID
    Optional<AcaoModel> findById(Integer id);

    //Seleciona todas as acoes em ordem Desc
    List<AcaoModel> findByOrderByNomeDesc();

    //Seleciona todas as acaoes pelo seu Codigo de ifentificacao
    Optional<AcaoModel> findAllByCodigo(Integer codigo);

    //Seleciona a partir de uma string(Operador LIKE)
    List<AcaoModel> findByNome(String nome);
}
