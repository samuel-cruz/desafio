package br.com.meta.dto.v1;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import br.com.meta.dto.BaseDTO;
import br.com.meta.enumeradores.Sexo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author samuel-cruz
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PessoaDTO extends BaseDTO {
	@ApiModelProperty(value = "Nome ", required = true, example = "João Silva", allowableValues = "range[1, 80]")
	private @NotBlank @Size(max = 80) String nome;
	@ApiModelProperty(value = "Sexo", required = true, example = "MASCULINO")
	private @NotNull Sexo sexo;
	@ApiModelProperty(value = "E-mail", example = "email@dominio.com.br", allowableValues = "range[1, 100]")
	private @Size(max = 100) @Email String email;
	@ApiModelProperty(value = "Data de nascimento", required = true, example = "2020-01-01")
	private @NotNull @PastOrPresent LocalDate dataNascimento;
	@ApiModelProperty(value = "Naturalidade", example = "Curitiba", allowableValues = "range[0, 40]")
	private @Size(max = 40) String naturalidade;
	@ApiModelProperty(value = "Nacionalidade", example = "Brasileiro", allowableValues = "range[0, 40]")
	private @Size(max = 40) String nacionalidade;
	@ApiModelProperty(value = "CPF", example = "00006400000", allowableValues = "range[1, 11]")
	private @NotBlank @Size(max = 11) @CPF String cpf;
}
