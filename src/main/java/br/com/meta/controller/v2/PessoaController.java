package br.com.meta.controller.v2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.meta.controller.BaseController;
import br.com.meta.converter.v2.PessoaConverter;
import br.com.meta.dto.v2.PessoaDTO;
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
@RestController("PessoaControllerV2")
@RequestMapping("v2/pessoa")
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
