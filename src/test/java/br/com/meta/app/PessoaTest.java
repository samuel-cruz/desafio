package br.com.meta.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.MessageFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.meta.entity.Pessoa;
import br.com.meta.enumeradores.Sexo;
import br.com.meta.exceptions.EntityNotFound;
import br.com.meta.exceptions.ServiceException;
import br.com.meta.service.PessoaService;

/**
 * @author samuel-cruz
 *
 */
class PessoaTest extends BaseTest {
	private @Autowired PessoaService pessoaService;

	@Test
	public void deveGravarPessoa() {
		final Pessoa pessoa = pessoaService.inserirRegistro(retornaUmaPessoaNova());

		assertNotNull(pessoa.getId());
		assertEquals("João Silva", pessoa.getNome());
		assertEquals(Sexo.MASCULINO, pessoa.getSexo());
		assertEquals("email@dominio.com.br", pessoa.getEmail());
		assertEquals(Date.valueOf("1960-01-02"), pessoa.getDataNascimento());
		assertEquals("Curitiba", pessoa.getNaturalidade());
		assertEquals("Brasileiro", pessoa.getNacionalidade());
		assertEquals("00006400000", pessoa.getCpf());
		assertEquals("Rua João Ferreira, Curitiba, PR", pessoa.getEndereco());
	}

	@Test
	public void deveAlterarPessoa() {
		Pessoa pessoa = pessoaService.inserirRegistro(retornaUmaPessoaNova());

		pessoa.setEndereco(null);
		pessoa.setEmail(null);
		pessoa.setNaturalidade("São Paulo");
		pessoa.setNacionalidade(null);

		pessoa = pessoaService.atualizarRegistro(pessoa);

		assertNull(pessoa.getEndereco());
		assertNull(pessoa.getEmail());
		assertEquals("São Paulo", pessoa.getNaturalidade());
		assertNull(pessoa.getNacionalidade());
	}

	@Test
	public void deveRemoverRegistro() {
		final Pessoa pessoa = pessoaService.inserirRegistro(retornaUmaPessoaNova());
		pessoaService.excluirRegistroPorId(pessoa.getId());

		final EntityNotFound thrown = assertThrows(EntityNotFound.class, () -> {
			pessoaService.retornarRegistro(pessoa.getId());
		});

		assertTrue(thrown.getMessage()
				.equals(MessageFormat.format("Registro com código {0} não encontrado.", pessoa.getId())));
	}

	@Test
	public void deveGerarErroQuandoCPFJaExistir() {
		pessoaService.inserirRegistro(retornaUmaPessoaNova());
		validarErroAoIncluir(retornaUmaPessoaNova(), "Já existe um cliente com o CPF informado");
	}

	@Test
	public void deveGerarErroQuandoCPFForInvalido() {
		final Pessoa pessoa = retornaUmaPessoaNova();
		pessoa.setCpf("12345678921");
		validarErroAoIncluir(pessoa, "número do registro de contribuinte individual brasileiro (CPF) inválido");
	}

	@Test
	public void deveGerarErroQuandoCPFNaoForInformado() {
		final Pessoa pessoa = retornaUmaPessoaNova();
		pessoa.setCpf(null);
		validarErroAoIncluir(pessoa, "CPF deve ser informado");
	}

	@Test
	public void deveGerarErroQuandoSexoNaoForInformado() {
		final Pessoa pessoa = retornaUmaPessoaNova();
		pessoa.setSexo(null);
		validarErroAoIncluir(pessoa, "Sexo deve ser informado");
	}

	@Test
	public void deveGerarErroQuandoDataNascimentoNaoForInformado() {
		final Pessoa pessoa = retornaUmaPessoaNova();
		pessoa.setDataNascimento(null);
		validarErroAoIncluir(pessoa, "Data de nascimento deve ser informada");
	}

	@Test
	public void deveGerarErroQuandoNomeUltrapassar80Caracteres() {
		final Pessoa pessoa = retornaUmaPessoaNova();
		pessoa.setNome(preencheStringA(pessoa.getNome(), 90));
		validarErroAoIncluir(pessoa, "O nome não deve ultrapassar 80 caracteres");
	}

	@Test
	public void deveGerarErroQuandoEmailForInvalido() {
		final Pessoa pessoa = retornaUmaPessoaNova();
		pessoa.setEmail("email");
		validarErroAoIncluir(pessoa, "E-mail inválido");
	}

	@Test
	public void deveGerarErroQuandoEmailUltrapassar100Caracteres() {
		final Pessoa pessoa = retornaUmaPessoaNova();
		pessoa.setEmail(preencheStringA(pessoa.getEmail(), 120));
		validarErroAoIncluir(pessoa, "O e-mail não deve ultrapassar 100 caracteres");
	}

	private String preencheStringA(final String valor, final int tamanho) {
		return String.format("%1$" + tamanho + "s", valor).replace(' ', 'A');
	}

	private void validarErroAoIncluir(final Pessoa pessoa, final String msgErroEsperada) {
		final ServiceException thrown = assertThrows(ServiceException.class, () -> {
			pessoaService.inserirRegistro(pessoa);
		});
		assertTrue(thrown.getMessage().contains(msgErroEsperada));
	}

	private Pessoa retornaUmaPessoaNova() {
		return new Pessoa("João Silva", Sexo.MASCULINO, "email@dominio.com.br", Date.valueOf("1960-01-02"), "Curitiba",
				"Brasileiro", "00006400000", "Rua João Ferreira, Curitiba, PR");
	}

	/*
	 * Outros testes devem ser implementados, como por exemplo:
	 *
	 * + Validar a quantidade de caracteres informados para Nacionalidade e
	 * Naturalidade
	 *
	 * +Definir e validar as mensagens de erro para cada atributo da entidade
	 */
}
