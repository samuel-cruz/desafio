package br.com.meta.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import br.com.meta.enumeradores.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author samuel-cruz
 *
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pessoa")
@EqualsAndHashCode(callSuper = true)
public class Pessoa extends BaseEntity {

	@Column(name = "nome", nullable = false, length = 80)
	@Size(max = 80, message = "O nome não deve ultrapassar 80 caracteres")
	@NotBlank(message = "Nome deve ser informado")
	private String nome;
	@Column(name = "sexo", nullable = false, length = 15)
	@Enumerated(EnumType.STRING)
	private @NotNull(message = "Sexo deve ser informado") Sexo sexo;
	@Column(name = "email", length = 100)
	@Size(max = 100, message = "O e-mail não deve ultrapassar 100 caracteres")
	@Email(message = "E-mail inválido")
	private String email;
	@Column(name = "dataNascimento", nullable = false)
	@NotNull(message = "Data de nascimento deve ser informada")
	private Date dataNascimento;
	@Column(name = "naturalidade", length = 40)
	private @Size(max = 40) String naturalidade;
	@Column(name = "nacionalidade", length = 40)
	private @Size(max = 40) String nacionalidade;
	@Column(name = "cpf", nullable = false, length = 11)
	@NotBlank(message = "CPF deve ser informado")
	private @CPF String cpf;
	@Column(name = "endereco", length = 200)
	private @Size(max = 200) String endereco;
}
