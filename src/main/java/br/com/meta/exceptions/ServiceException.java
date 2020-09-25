package br.com.meta.exceptions;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * @author samuel-cruz
 *
 */
public abstract class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -7306155424131826256L;

	private final HttpStatus httpStatus;

	private static final Logger log = LoggerFactory.getLogger(ServiceException.class);

	private final String error;

	public ServiceException(final String error) {
		super(error);
		this.error = error;
		httpStatus = null;
		log.error(error);
	}

	public ServiceException(final HttpStatus httpStatus, final String error) {
		super(error);
		this.error = error;
		this.httpStatus = httpStatus;
		log.error(error);
	}

	public ServiceException(final HttpStatus httpStatus, final String error, final Throwable t) {
		super(error, t);
		this.error = error;
		this.httpStatus = httpStatus;
		log.error(error);
	}

	public ServiceException(final String error, final Throwable t) {
		super(error, t);
		this.error = error;
		httpStatus = null;
		log.error(error);
	}

	public String getError() {
		return error;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	protected static String getErrorFormated(final String error, final Object... params) {
		return MessageFormat.format(error, params);
	}

}
