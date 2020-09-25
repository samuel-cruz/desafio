package br.com.meta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author samuel-cruz
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends ServiceException {
	private static final long serialVersionUID = -7372362538143630401L;

	public InternalServerError(final String error) {
		super(error);
	}

	public static InternalServerError of(final String error) {
		return new InternalServerError(error);
	}

	public static InternalServerError of(final String error, final Object... params) {
		return new InternalServerError(getErrorFormated(error, params));
	}

}
