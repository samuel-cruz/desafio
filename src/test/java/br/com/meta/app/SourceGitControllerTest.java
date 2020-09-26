package br.com.meta.app;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @author samuel-cruz
 *
 */
public class SourceGitControllerTest extends BaseTest {

	@Test
	public void validarRepositorio() throws Exception {
		final MvcResult mvcResult = mvc.perform(get("/source")).andExpect(status().isOk()).andReturn();
		assertEquals("https://github.com/samuel-cruz/desafio", mvcResult.getResponse().getContentAsString());
	}
}
