package br.com.meta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author samuel-cruz
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InsertError extends ServiceException {
	private static final long serialVersionUID = -6766720048498925063L;

	public InsertError(final String error) {
		super(error);
	}

	public static InsertError of(final String error) {
		return new InsertError(error);
	}

	public static InsertError of(final String error, final Object... params) {
		return new InsertError(getErrorFormated(error, params));
	}
}
