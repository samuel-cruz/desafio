package br.com.meta.exceptions;

import lombok.Data;

/**
 * @author samuel-cruz
 *
 */
@Data
public class ExceptionResponse {

	private final String message;

	public ExceptionResponse(final String message) {
		this.message = message;
	}

}