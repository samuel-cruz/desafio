package br.com.meta.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @author samuel-cruz
 *
 */
@RestController
@Api(tags = { "Repositório com o código fonte" })
public class SourceGitController {
	@GetMapping(path = "source")
	public String getHome() {
		return "https://github.com/samuel-cruz/desafio";
	}
}
