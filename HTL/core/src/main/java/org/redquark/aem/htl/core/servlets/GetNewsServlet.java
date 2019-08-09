package org.redquark.aem.htl.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = Servlet.class, property = { "sling.servlet.paths=/bin/getNews",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class GetNewsServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = -82012732685416435L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		final String endpoint = "http://content.guardianapis.com/search?api-key=test&page-size=10&q=";

		try {

			JSONArray completeJson = new JSONArray();
			String params = request.getParameter("tags");
			log.debug("Parameter passed: {}", params);

			// Getting all the tags
			String[] allTags = params.split("\\|");

			JSONObject tagOneJson = readJsonFromUrl(endpoint + allTags[0]);
			JSONObject tagTwoJson = readJsonFromUrl(endpoint + allTags[1]);
			JSONObject tagThreeJson = readJsonFromUrl(endpoint + allTags[2]);
			JSONObject tagFourJson = readJsonFromUrl(endpoint + allTags[3]);

			completeJson.put(tagOneJson);
			completeJson.put(tagTwoJson);
			completeJson.put(tagThreeJson);
			completeJson.put(tagFourJson);

			String[] topics = allTags;

			String html = "";
			String active = "";
			String[] dataHtml = new String[4];

			for (int i = 0; i < topics.length; i++) {
				// Adding active class to the html of the first tag
				if (i == 0) {
					active = "active";
				} else {
					active = "";
				}

				JSONObject jsonResponse = completeJson.getJSONObject(i).getJSONObject("response");
				JSONArray jsonArray = new JSONArray();
				jsonArray = jsonResponse.getJSONArray("results");

				dataHtml[i] = "<div role=\"tabpanel\" class=\"tab-pane " + active + "\" id=\"" + topics[i] + "\">"
						+ "<ul class=\"list-group\">";
				for (int k = 0; k < jsonArray.length(); k++) {
					JSONObject p = jsonArray.getJSONObject(k);
					String data = "<li class=\"list-group-item\"><span class=\"glyphicon glyphicon-pushpin\">"
							+ "</span> <span class=\"label label-info\">" + p.get("sectionId") + "</span> "
							+ "<a target=\"_blank\" href = \"" + p.get("webUrl") + "\"> " + p.get("webTitle")
							+ "</a> </li>";

					dataHtml[i] = dataHtml[i] + data;
				}

				dataHtml[i] = dataHtml[i] + "</ul></div>";
				html = html + dataHtml[i];
			}

			response.setContentType("text/html");
			response.getWriter().write(html);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private String readAll(BufferedReader reader) {

		StringBuilder sb = new StringBuilder();
		int cp;

		try {
			while ((cp = reader.read()) != -1) {
				sb.append((char) cp);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return sb.toString();
	}

	private JSONObject readJsonFromUrl(String url) {

		InputStream is = null;

		try {
			is = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(reader);
			JSONObject jsonObject = new JSONObject(jsonText);
			return jsonObject;
		} catch (IOException | JSONException e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
}
