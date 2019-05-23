package org.redquark.aem.learning.core.servlets.feed;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(immediate = true, service = Servlet.class, 
		property = {
				"sling.servlet.methods=" + HttpConstants.METHOD_GET,
				"sling.servlet.methods=" + HttpConstants.METHOD_POST,
				"sling.servlet.resourceTypes=" + "sling/servlet/default",
				"sling.servlet.selectors="+"feed",
				"sling.servlet.selectors="+"feedentry",
				"sling.servlet.extensions="+"xml",
				"service.ranking=" + 1003})
public class CustomFeedRendererServlet extends SlingAllMethodsServlet {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Generated serialVersionUID
	private static final long serialVersionUID = 362516459145838595L;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		try {
			log.info("Inside GET method");
			response.getWriter().println("Custom servlet invoked from GET");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		try {
			log.info("Inside POST method");
			response.getWriter().println("Custom servlet invoked from POST");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
