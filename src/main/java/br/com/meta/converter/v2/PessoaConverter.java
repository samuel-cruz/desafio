package br.com.meta.converter.v2;

import java.sql.Date;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.meta.converter.BaseConverter;
import br.com.meta.dto.v2.PessoaDTO;
import br.com.meta.entity.Pessoa;

/**
 * @author samuel-cruz
 *
 */
@Component("PessoaConverterV2")
public class PessoaConverter extends BaseConverter<Pessoa, PessoaDTO> {

	@Override
	protected Optional<Pessoa> mapearDTOParaEntidade(final PessoaDTO dto, final Pessoa ent) {
		ent.setNome(dto.getNome());
		ent.setSexo(dto.getSexo());
		ent.setEmail(dto.getEmail());
		ent.setDataNascimento(Date.valueOf(dto.getDataNascimento()));
		ent.setNaturalidade(dto.getNaturalidade());
		ent.setNacionalidade(dto.getNacionalidade());
		ent.setCpf(dto.getCpf());
		ent.setEndereco(dto.getEndereco());

		return Optional.of(ent);
	}

	@Override
	protected Optional<PessoaDTO> mapearEntidadeParaDTO(final Pessoa ent, final PessoaDTO dto) {
		dto.setNome(ent.getNome());
		dto.setSexo(ent.getSexo());
		dto.setEmail(ent.getEmail());
		dto.setDataNascimento(ent.getDataNascimento().toLocalDate());
		dto.setNaturalidade(ent.getNaturalidade());
		dto.setNacionalidade(ent.getNacionalidade());
		dto.setCpf(ent.getCpf());
		dto.setEndereco(ent.getEndereco());

		return Optional.of(dto);
	}
}
