package br.com.meta.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.meta.controller.BaseController;
import br.com.meta.converter.v1.PessoaConverter;
import br.com.meta.dto.v1.PessoaDTO;
import br.com.meta.entity.Pessoa;
import br.com.meta.service.PessoaService;
import io.swagger.annotations.Api;

/**
 * @author samuel-cruz
 *
 */
/**
 * @author samuel-cruz
 *
 */
@RestController
@RequestMapping("v1/pessoa")
@Api(tags = { "Manutenção de pessoas" })
public class PessoaController extends BaseController<Pessoa, PessoaDTO> {

	public PessoaController(final PessoaService service, final PessoaConverter converter) {
		super(service, converter);
	}

	@Override
	protected PessoaDTO retornaInstanciaDTO() {
		return new PessoaDTO();
	}

	@Override
	protected Pessoa retornaInstanciaEntidade() {
		return new Pessoa();
	}
}
