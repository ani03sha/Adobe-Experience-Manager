package org.redquark.aem.learning.core.servlets.text;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.redquark.aem.learning.core.models.text.TextModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = Servlet.class, property = { "sling.servlet.methods=GET",
		"sling.servlet.resourceTypes=" + "/apps/learning/components/content/text/simpleText" })
public class TextServlet extends SlingSafeMethodsServlet {

	// Generate serial version UID
	private static final long serialVersionUID = -1562626142109551633L;

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		response.setContentType("text/html");

		try {

			// Adapting request to TextModel (Sling Model class) via request
			TextModel textModel = request.adaptTo(TextModel.class);

			// Getting the value of extraDetails via request object
			response.getWriter().write(textModel.getExtraDetails());

			log.info("Adaptation done!");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
