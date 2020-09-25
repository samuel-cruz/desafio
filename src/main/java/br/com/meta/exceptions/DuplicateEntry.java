package br.com.meta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author samuel-cruz
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateEntry extends ServiceException {
	private static final long serialVersionUID = 6393159448510811127L;

	public DuplicateEntry(final String error) {
		super(error);
	}

	public static DuplicateEntry of(final String error) {
		return new DuplicateEntry(error);
	}

	public static DuplicateEntry of(final String error, final Object... params) {
		return new DuplicateEntry(getErrorFormated(error, params));
	}

}
