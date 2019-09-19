package org.redquark.aem.componentdevelopment.core.servlets;

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
@Component(service = Servlet.class, property = { "sling.servlet.paths=/bin/subscribe",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST })
public class SubscriptionServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 9174644852051377802L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		log.debug("Invoking Subscription servlet");

		try {
			String email = request.getParameter("email") != null ? request.getParameter("email") : "";
			String reason = request.getParameter("reason") != null ? request.getParameter("reason") : "";

			if (!email.isEmpty()) {
				log.debug("Subscription process is starting for {}", email);
				// Logic for subscribing the email
			}

			if (!reason.isEmpty()) {
				log.debug("Saving the reason for not subscribing in repository: {}", reason);
				// Logic for saving the reason in the repository
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}
