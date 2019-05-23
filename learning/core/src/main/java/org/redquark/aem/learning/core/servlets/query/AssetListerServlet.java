package org.redquark.aem.learning.core.servlets.query;

import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/learning/assetlister" })
public class AssetListerServlet extends SlingSafeMethodsServlet {

	// Generated serialVersionUID
	private static final long serialVersionUID = 7762806638577908286L;

	// Default logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Instance of ResourceResolver
	private ResourceResolver resourceResolver;

	// JCR Session instance
	private Session session;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		try {

			// Getting the ResourceResolver from the current request
			resourceResolver = request.getResourceResolver();

			// Getting the session instance by adapting ResourceResolver
			session = resourceResolver.adaptTo(Session.class);

			QueryManager queryManager = session.getWorkspace().getQueryManager();
			String queryString = "SELECT * FROM [dam:Asset] AS asset WHERE ISDESCENDANTNODE(asset ,'/content/dam/we-retail/en/features')";
			Query query = queryManager.createQuery(queryString, "JCR-SQL2");

			QueryResult queryResult = query.execute();

			response.getWriter().println("--------------Result-------------");

			RowIterator rowIterator = queryResult.getRows();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.nextRow();
				response.getWriter().println(row.toString());
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (resourceResolver != null) {
				resourceResolver.close();
			}
		}
	}

}
