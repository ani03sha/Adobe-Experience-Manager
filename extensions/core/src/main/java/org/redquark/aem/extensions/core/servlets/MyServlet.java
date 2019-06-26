package org.redquark.aem.extensions.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.extensions.core.services.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 */
@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/myservlet" })
public class MyServlet extends SlingAllMethodsServlet {

	// Generated serialVersionUID
	private static final long serialVersionUID = -8720724011172847122L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Injecting reference of your service - No need to use BundleContext
	@Reference
	private MyService myService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		try {
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");

			// Calling the method implementation from your service
			String password = myService.getPassword("Sample Type");
			
			log.info("Writing password to the browser...");

			response.getWriter().write(password);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
