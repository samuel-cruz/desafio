package br.com.meta.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meta.entity.Pessoa;

/**
 * @author samuel-cruz
 *
 */
@Repository
public interface PessoaRepository extends BaseRepository<Pessoa> {

	@Query("select count(tb) from Pessoa tb where tb.id <> coalesce(:id, 0) and tb.cpf = :cpf")
	Long quantasPessoasComCPF(@Param("id") Integer id, @Param("cpf") String cpf);
}