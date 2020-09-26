package br.com.meta.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.meta.converter.BaseConverter;
import br.com.meta.dto.BaseDTO;
import br.com.meta.entity.BaseEntity;
import br.com.meta.service.BaseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author samuel-cruz
 *
 * @param <E> Entity
 * @param <D> Data Transfer Object
 */
@Transactional(rollbackFor = Exception.class)
public abstract class BaseController<E extends BaseEntity, D extends BaseDTO> {

	private final BaseService<E> service;
	private final BaseConverter<E, D> converter;

	public BaseController(final BaseService<E> service, final BaseConverter<E, D> converter) {
		this.service = service;
		this.converter = converter;
	}

	@PostMapping
	@ApiOperation(value = "Inclui um registro.")
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "Indica que o objeto persistido"),
			@ApiResponse(code = 204, message = "Indica que não foi possível fazer a desserialização"),
			@ApiResponse(code = 500, message = "Indica que ocorreu um erro interno na aplicação"), })
	public ResponseEntity<D> incluirRegistro(//
			@ApiParam(value = "Registro a ser persistido no banco de dados") //
			@RequestBody(required = true) final @Valid D dto) {

		final Optional<E> ent = converter.converterDTOParaEntidade(dto, retornaInstanciaEntidade());

		if (ent.isPresent()) {
			final Optional<D> dtoRet = converter.converterEntidadeParaDTO(service.inserirRegistro(ent.get()),
					retornaInstanciaDTO());

			if (dtoRet.isPresent())
				return new ResponseEntity<>(dtoRet.get(), HttpStatus.CREATED);
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	@ApiOperation(value = "Retorna uma lista de registros paginados.")
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "Indica que encontrou registro(s) no banco de dados"),
			@ApiResponse(code = 204, message = "Indica que não encontrou nenhum registro no banco de dados"), })
	public ResponseEntity<Page<D>> listarRegistros(
			@ApiIgnore @PageableDefault(page = 0, size = 10) final Pageable page) {

		final Page<D> listaRegistros = service.listarRegistros(page)
				.map(item -> converter.converterEntidadeParaDTO(item, retornaInstanciaDTO()).orElse(null));

		if (!listaRegistros.isEmpty())
			return ResponseEntity.ok(listaRegistros);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna um registro específico.")
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "Indica que encontrou o registro no banco de dados"),
			@ApiResponse(code = 204, message = "Indica que não encontrou o registro no banco de dados"), })
	public ResponseEntity<D> listarRegistro(//
			@ApiParam(value = "Identificador do registro a ser exibido") //
			@PathVariable final Integer id) {
		return ResponseEntity.ok(
				converter.converterEntidadeParaDTO(service.retornarRegistro(id), retornaInstanciaDTO()).orElseThrow());
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Remove um registro do banco de dados.")
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "Indica que o registro foi removido do banco de dados"),
			@ApiResponse(code = 400, message = "Indica que não encontrou o registro no banco de dados"), })
	public void excluirRegistro(//
			@ApiParam(value = "Identificador do registro a ser exibido") //
			@PathVariable final Integer id) {
		service.excluirRegistroPorId(service.retornarRegistro(id).getId());
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Atualiza um registro no banco de dados.")
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "Indica que o objeto foi atualizado"),
			@ApiResponse(code = 204, message = "Indica que não foi possível fazer a desserialização"),
			@ApiResponse(code = 500, message = "Indica que ocorreu um erro interno na aplicação"), })
	public ResponseEntity<D> atualizarRegistros(//
			@ApiParam(value = "Identificador do registro a ser atualizado") //
			@PathVariable(name = "id", required = true) final @Valid Integer id, //
			@ApiParam(value = "Registro a ser atualizado no banco de dados") //
			@RequestBody(required = true) @Valid final D dto) {

		final Optional<E> ent = converter.converterDTOParaEntidade(dto, service.retornarRegistro(id));

		if (ent.isPresent())
			return ResponseEntity
					.ok(converter.converterEntidadeParaDTO(service.atualizarRegistro(ent.get()), retornaInstanciaDTO())
							.orElseThrow());

		return ResponseEntity.noContent().build();
	}

	protected abstract D retornaInstanciaDTO();

	protected abstract E retornaInstanciaEntidade();
}
