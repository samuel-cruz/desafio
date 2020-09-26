package br.com.meta.app;

import java.util.Locale;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author samuel-cruz
 *
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseTest {

	protected @Autowired WebApplicationContext context;
	protected MockMvc mvc;

	@org.junit.Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	protected ObjectMapper getObjectMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.registerModule(new JavaTimeModule());
		mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
		mapper.setLocale(new Locale("pt_BR"));

		return mapper;
	}

	protected String converterObjetoParaString(final Object obj) throws JsonProcessingException {
		final String objetoString = getObjectMapper().writeValueAsString(obj);
		System.out.println(objetoString);
		return objetoString;
	}

	protected <T> T converterStringParaObjeto(final String obj, final Class<T> classe) {
		try {
			return getObjectMapper().readValue(obj, classe);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
