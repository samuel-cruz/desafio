package br.com.meta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author samuel-cruz
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ServiceException {
	private static final long serialVersionUID = -411479108919674449L;

	public BadRequestException(final String error) {
		super(error);
	}

	public static BadRequestException of(final String error) {
		return new BadRequestException(error);
	}

	public static BadRequestException of(final String error, final Object... params) {
		return new BadRequestException(getErrorFormated(error, params));
	}

}
