package br.com.meta.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author samuel-cruz
 *
 */
public class AppException extends ServiceException {
	private static final long serialVersionUID = -7102684378431543349L;

	public AppException(final HttpStatus httpStatus, final String error) {
		super(httpStatus, error);
	}

	public AppException(final HttpStatus httpStatus, final String error, final Object... params) {
		super(httpStatus, getErrorFormated(error, params));
	}

	public AppException(final String error, final Object... params) {
		super(getErrorFormated(error, params));
	}

	public static RuntimeException of(final String error, final Object... params) {
		throw new AppException(error, params);
	}

	public static RuntimeException of(final HttpStatus httpStatus, final String error, final Object... params) {
		throw new AppException(httpStatus, error, params);
	}

}
