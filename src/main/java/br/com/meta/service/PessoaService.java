package br.com.meta.service;

import org.springframework.stereotype.Service;

import br.com.meta.entity.Pessoa;
import br.com.meta.exceptions.DuplicateEntry;
import br.com.meta.repository.PessoaRepository;

/**
 * @author samuel-cruz
 *
 */
@Service
public class PessoaService extends BaseService<Pessoa> {

	private final PessoaRepository pessoaRepository;

	public PessoaService(final PessoaRepository repository) {
		super(repository);
		pessoaRepository = repository;
	}

	@Override
	protected Pessoa antesDeSavar(final Pessoa ent) {
		if (pessoaRepository.quantasPessoasComCPF(ent.getId(), ent.getCpf()) > 0)
			throw DuplicateEntry.of("Já existe um cliente com o CPF informado");

		return super.antesDeSavar(ent);
	}
}
