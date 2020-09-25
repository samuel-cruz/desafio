package br.com.meta.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.meta.exceptions.ExceptionResponse;
import br.com.meta.exceptions.ServiceException;

/**
 * @author samuel-cruz
 *
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(final ServiceException ex,
			final WebRequest request) {

		final HttpStatus httpStatus = ex.getHttpStatus() == null
				? ex.getClass().getAnnotation(ResponseStatus.class).value()
				: ex.getHttpStatus();

		return new ResponseEntity<>(new ExceptionResponse(ex.getError()), httpStatus);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		String strRetorno = "";
		for (final ObjectError erro : ex.getBindingResult().getAllErrors())
			if (erro instanceof FieldError)
				strRetorno = ((FieldError) erro).getField() + ": " + erro.getDefaultMessage() + "\n";
			else
				strRetorno = erro.toString();

		return new ResponseEntity<>(new ExceptionResponse(strRetorno), status);
	}
}