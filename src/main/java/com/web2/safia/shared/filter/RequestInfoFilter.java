package com.web2.safia.shared.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Order(1)
@Component
public class RequestInfoFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(RequestInfoFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
/*
				logger.info("Local");
				logger.info("Local Addr: {}", request.getLocalAddr());
				logger.info("Local Name: {}", request.getLocalName());
				logger.info("Local Port: {}", request.getLocalPort());
				logger.info("Locale: {}", request.getLocale());

				logger.info("Remote");
				logger.info("Remote Addr: {}", request.getRemoteAddr());
				logger.info("Remote Host: {}", request.getRemoteHost());
				logger.info("Remote Port: {}", request.getRemotePort());

				logger.info("Is Secure? {}", request.isSecure());
				
				logger.info("Atributes");
				var attributes = request.getAttributeNames().asIterator();
				while(attributes.hasNext()) {
					var attribute = attributes.next();
					logger.info("ATTR: {}", attribute);
				}

				logger.info("Parameters");
				var parameters = request.getParameterMap();
				for (var key : parameters.keySet()) {
					logger.info("{}: {}", key, parameters.get(key));
				}

				var httpRequest = (HttpServletRequest) request;

				// logger.info("Cookies");
				// for (var cookie : httpRequest.getCookies()) {
				// 	logger.info("{}: {}", cookie.getName(), cookie.getValue());
				// }

				logger.info("Auth type: {}", httpRequest.getAuthType());

				logger.info("Headers");
				var headers = httpRequest.getHeaderNames().asIterator();
				while(headers.hasNext()) {
					var header = headers.next();
					logger.info("{}: {}", header, httpRequest.getHeader(header));
				}
*/

				chain.doFilter(request, response);
	}
}