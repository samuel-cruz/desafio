package br.com.meta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author samuel-cruz
 *
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class NotFoundException extends ServiceException {
	private static final long serialVersionUID = -83468352679249178L;

	public NotFoundException(final String error) {
		super(error);
	}

	public static NotFoundException of(final String error) {
		return new NotFoundException(error);
	}

	public static NotFoundException of(final String error, final Object... params) {
		return new NotFoundException(getErrorFormated(error, params));
	}
}