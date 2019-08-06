package org.redquark.aem.htl.core.servlets;

import java.util.UUID;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = Servlet.class, property = { "sling.servlet.paths=/bin/claim",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST })
public class GetClaimSerlvet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 8542458546115741012L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		try {
			// Get the form data
			String id = UUID.randomUUID().toString();
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String address = request.getParameter("address");
			String category = request.getParameter("cat");
			String state = request.getParameter("state");
			String details = request.getParameter("details");
			String date = request.getParameter("date");
			String city = request.getParameter("city");

			// Encode the form data to JSON
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("firstName", firstName);
			json.put("lastName", lastName);
			json.put("address", address);
			json.put("cat", category);
			json.put("state", state);
			json.put("details", details);
			json.put("date", date);
			json.put("city", city);

			// Writing json data
			response.getWriter().println(json.toString());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}
