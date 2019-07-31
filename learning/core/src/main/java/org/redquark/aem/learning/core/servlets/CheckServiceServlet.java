package org.redquark.aem.learning.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.learning.core.services.CheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 */
@Component(service = Servlet.class, property = { "sling.servlet.paths=/bin/check" })
public class CheckServiceServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = -8245851417854551228L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference(target = "(service.label=second)")
	private CheckService checkService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		log.info("Executing servlet");

		String message = checkService.message("Anirudh");

		response.getWriter().println(message);
	}
}
