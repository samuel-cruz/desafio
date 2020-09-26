package br.com.meta.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.meta.exceptions.DeleteError;
import br.com.meta.exceptions.DuplicateEntry;
import br.com.meta.exceptions.EntityNotFound;
import br.com.meta.exceptions.InsertError;
import br.com.meta.exceptions.InternalServerError;
import br.com.meta.exceptions.UpdateError;
import br.com.meta.repository.BaseRepository;

/**
 * @author samuel-cruz
 *
 * @param <E> Entity
 */
public abstract class BaseService<E> {

	protected final BaseRepository<E> repository;

	public BaseService(final BaseRepository<E> repository) {
		this.repository = repository;
	}

	public Page<E> listarRegistros(final Pageable pageRequest) {
		return repository.findAll(pageRequest);
	}

	public Optional<E> listarRegistro(final Integer id) {
		try {
			return repository.findById(id);
		} catch (final Exception e) {
			throw InternalServerError.of(e.getMessage());
		}
	}

	public E retornarRegistro(final Integer id) {
		if (id == null)
			throw registroNaoEncontrado(id);

		return listarRegistro(id).orElseThrow(() -> registroNaoEncontrado(id));
	}

	private EntityNotFound registroNaoEncontrado(final Integer id) {
		return EntityNotFound.of("Registro com código {0} não encontrado.", id);
	}

	public E inserirRegistro(final E ent) {
		try {
			return repository.save(antesDeSavar(ent));
		} catch (final Exception e) {
			if (e instanceof DuplicateEntry)
				throw e;
			else
				throw InsertError.of(e.getMessage());
		}
	}

	public E atualizarRegistro(final E ent) {
		try {
			return repository.save(antesDeSavar(ent));
		} catch (final Exception e) {
			throw UpdateError.of(e.getMessage());
		}
	}

	public boolean excluirRegistroPorId(final Integer id) {
		try {
			repository.deleteById(id);
			return true;
		} catch (final RuntimeException e) {
			throw DeleteError.of(e.getMessage());
		}
	}

	protected E antesDeSavar(final E ent) {
		return ent;
	}
}
