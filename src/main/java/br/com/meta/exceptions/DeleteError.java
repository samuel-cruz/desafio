package br.com.meta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author samuel-cruz
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DeleteError extends ServiceException {
	private static final long serialVersionUID = 3819361762293300152L;

	public DeleteError(final String error) {
		super(error);
	}

	public static DeleteError of(final String error) {
		return new DeleteError(error);
	}

	public static DeleteError of(final String error, final Object... params) {
		return new DeleteError(getErrorFormated(error, params));
	}
}
