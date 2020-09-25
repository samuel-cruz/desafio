package br.com.meta.converter;

import java.util.Optional;

import br.com.meta.dto.BaseDTO;
import br.com.meta.entity.BaseEntity;

/**
 * @author samuel-cruz
 *
 * @param <E> Entity
 * @param <D> Data Transfer Object
 */
public abstract class BaseConverter<E extends BaseEntity, D extends BaseDTO> {

	public final Optional<D> converterEntidadeParaDTO(final E ent, final D dto) {
		dto.setId(ent.getId());
		dto.setDataCriacao(ent.getDataCriacao().toLocalDateTime());
		dto.setDataAlteracao(ent.getDataAlteracao().toLocalDateTime());

		return mapearEntidadeParaDTO(ent, dto);
	}

	public final Optional<E> converterDTOParaEntidade(final D dto, final E ent) {
		return mapearDTOParaEntidade(dto, ent);
	}

	protected abstract Optional<E> mapearDTOParaEntidade(final D dto, final E ent);

	protected abstract Optional<D> mapearEntidadeParaDTO(final E ent, final D dto);
}
