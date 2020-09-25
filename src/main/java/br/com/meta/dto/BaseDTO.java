package br.com.meta.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author samuel-cruz
 *
 */
@Data
public abstract class BaseDTO {

	@ApiModelProperty(value = "Identificador único do registro (somente leitura)", required = false, example = "1")
	private @Positive Integer id;

	@ApiModelProperty(value = "Data de criação do registro (somente leitura)", required = false, example = "2020-09-23T13:04:00.726998")
	private LocalDateTime dataCriacao;

	@ApiModelProperty(value = "Data de alteração do registro (somente leitura)", required = false, example = "2020-09-23T13:04:00.726998")
	private LocalDateTime dataAlteracao;
}
