package br.com.meta.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * @author samuel-cruz
 *
 */
@Component
public class DefaultErrorAttribute extends DefaultErrorAttributes {

	private static final String ERROR_ATTRIBUTE = DefaultErrorAttributes.class.getName() + ".ERROR";

	@Override
	@SuppressWarnings("deprecation")
	public Map<String, Object> getErrorAttributes(final WebRequest webRequest, final boolean includeStackTrace) {
		final Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
		final Map<String, Object> out = new HashMap<>();
		out.put("mensagem", map.get("message"));
		out.put("url", map.get("path"));

		return out;
	}

	@Override
	public Throwable getError(final WebRequest webRequest) {
		Throwable exception = getAttributeByName(webRequest, ERROR_ATTRIBUTE);
		if (exception == null)
			exception = getAttributeByName(webRequest, "javax.servlet.error.exception");
		return exception;
	}

	@SuppressWarnings("unchecked")
	private <T> T getAttributeByName(final RequestAttributes requestAttributes, final String name) {
		return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

}
