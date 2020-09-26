package br.com.meta.app;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.meta.dto.v1.PessoaDTO;
import br.com.meta.enumeradores.Sexo;

/**
 * @author samuel-cruz
 *
 */
public class PessoaControllerV1Test extends BaseTest {
	private static String BASE_URL = "/v1/pessoa/";

	@Test
	public void deveRetornar400QuandoNomeNaoInformado() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		dto.setNome(null);
		deveGerarErroAoChamarPost(dto, "não deve estar em branco");
	}

	@Test
	public void deveRetornar400QuandoSexoNaoInformado() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		dto.setSexo(null);
		deveGerarErroAoChamarPost(dto, "não deve ser nulo");
	}

	@Test
	public void deveRetornar400QuandoEmailInvalido() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		dto.setEmail("email");
		deveGerarErroAoChamarPost(dto, "deve ser um endereço de e-mail bem formado");
	}

	@Test
	public void deveRetornar400QuandoDataNascimentoNaoInformada() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		dto.setDataNascimento(null);
		deveGerarErroAoChamarPost(dto, "não deve ser nulo");
	}

	@Test
	public void deveRetornar400QuandoDataNascimentoForDataFutura() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		dto.setDataNascimento(dto.getDataNascimento().plusYears(200));
		deveGerarErroAoChamarPost(dto, "deve ser uma data no passado ou no presente");
	}

	@Test
	public void deveRetornar400QuandoNaturalidadeForMaiorQue40Caracteres() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		dto.setNaturalidade("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		deveGerarErroAoChamarPost(dto, "tamanho deve ser entre 0 e 40");
	}

	@Test
	public void deveRetornar400QuandoNacionalidadeForMaiorQue40Caracteres() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		dto.setNacionalidade("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		deveGerarErroAoChamarPost(dto, "tamanho deve ser entre 0 e 40");
	}

	@Test
	public void deveRetornar400QuandoCPFNaoInformado() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		dto.setCpf(null);
		deveGerarErroAoChamarPost(dto, "não deve estar em branco");
	}

	@Test
	public void deveRetornar400QuandoCPFForInvalido() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		dto.setCpf("123456789");
		deveGerarErroAoChamarPost(dto, "número do registro de contribuinte individual brasileiro (CPF) inválido");
	}

	@Test
	public void deveRetornar400QuandoCPFJaExistirNaBase() throws Exception {
		final PessoaDTO dto = gerarNovaPessoaDTO();

		gravarPessoaERetornar(dto);
		deveGerarErroAoChamarPost(dto, "Já existe um cliente com o CPF informado");
	}

	@Test
	public void deveRetornar201QuandoCadastrarPessoa() throws Exception {
		final PessoaDTO dtoEntrada = gerarNovaPessoaDTO();
		final PessoaDTO dtoRetorno = gravarPessoaERetornar(dtoEntrada);

		assertNotNull(dtoRetorno);
		assertNotNull(dtoRetorno.getId());
		assertEquals(dtoEntrada.getNome(), dtoRetorno.getNome());
		assertEquals(dtoEntrada.getSexo(), dtoRetorno.getSexo());
		assertEquals(dtoEntrada.getEmail(), dtoRetorno.getEmail());
		assertEquals(dtoEntrada.getDataNascimento(), dtoRetorno.getDataNascimento());
		assertEquals(dtoEntrada.getNaturalidade(), dtoRetorno.getNaturalidade());
		assertEquals(dtoEntrada.getNacionalidade(), dtoRetorno.getNacionalidade());
		assertEquals(dtoEntrada.getCpf(), dtoRetorno.getCpf());
		assertNotNull(dtoRetorno.getDataCriacao());
		assertNotNull(dtoRetorno.getDataAlteracao());
	}

	@Test
	public void deveRetornar200QuandoExcluir() throws Exception {
		final PessoaDTO dtoRetorno = gravarPessoaERetornar(gerarNovaPessoaDTO());

		assertNotNull(dtoRetorno);
		assertNotNull(dtoRetorno.getId());

		mvc.perform(delete(BASE_URL.concat(dtoRetorno.getId().toString()))).andExpect(status().isOk());

		mvc.perform(get(BASE_URL.concat(dtoRetorno.getId().toString()))).andExpect(status().isNoContent());
	}

	private void deveGerarErroAoChamarPost(final PessoaDTO dto, final String erroEsperado) throws Exception {
		mvc.perform(post(BASE_URL).content(converterObjetoParaString(dto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message", containsString(erroEsperado)));
	}

	private PessoaDTO gravarPessoaERetornar(final PessoaDTO dto) throws JsonProcessingException, Exception {
		final MvcResult mvcResult = mvc
				.perform(post(BASE_URL).content(converterObjetoParaString(dto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		return converterStringParaObjeto(mvcResult.getResponse().getContentAsString(), PessoaDTO.class);
	}

	private PessoaDTO gerarNovaPessoaDTO() {
		return new PessoaDTO("Pedro Silva", //
				Sexo.MASCULINO, //
				"email@dominio.com.br", //
				LocalDate.parse("1960-01-02"), //
				"Curitiba", //
				"Brasileiro", //
				"00006400000");
	}
}
