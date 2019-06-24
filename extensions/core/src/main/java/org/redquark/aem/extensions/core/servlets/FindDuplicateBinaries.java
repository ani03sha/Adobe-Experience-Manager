package org.redquark.aem.extensions.core.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
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
 */
@Component(service = Servlet.class, immediate = true, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/extensions/duplicatebinaries" })
public class FindDuplicateBinaries extends SlingSafeMethodsServlet {

	// Generated serial version UID
	private static final long serialVersionUID = 9049912612409737620L;

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		// JCR Session
		Session session = null;

		try {

			// Getting the resource resolver object
			ResourceResolver resourceResolver = request.getResourceResolver();

			// Adapting the resource resolver to the session
			session = resourceResolver.adaptTo(Session.class);

			// Getting instance of QueryManager which will be used to execute search queries
			QueryManager queryManager = session.getWorkspace().getQueryManager();

			// SQL2 query that we will be executing - ths query searches for assets (of type
			// dam:Asset) under the path /content/dam and for the node sha1
			String statement = "SELECT  * FROM [dam:Asset] WHERE ISDESCENDANTNODE(\"/content/dam\") ORDER BY 'jcr:content/metadata/dam:sha1'";

			// Converted the string query into JCR query of SQL2 type
			Query query = queryManager.createQuery(statement, Query.JCR_SQL2);

			// Results after executing the query
			QueryResult result = query.execute();

			// Getting nodes in the form of iterator
			NodeIterator nodes = result.getNodes();

			Node node = null, metadata;

			String previousSHA1 = null, currentSHA1 = null, paths = null, previousPath = null;

			// This map will contain the duplicate binaries
			Map<String, String> duplicates = new LinkedHashMap<>();

			// Loop for all the results
			while (nodes.hasNext()) {

				// Getting the next node's reference
				node = nodes.nextNode();

				// Getting the metadata node for the current node (which is actually an asset)
				metadata = node.getNode("jcr:content/metadata");

				// Check if the metadata node has SHA1 property
				if (metadata.hasProperty("dam:sha1")) {
					currentSHA1 = metadata.getProperty("dam:sha1").getString();
				} else {
					continue;
				}

				if (currentSHA1.equals(previousSHA1)) {
					paths = duplicates.get(currentSHA1);
					
					if (paths == null) {
						paths = previousPath;
					} else {
						if (!paths.contains(previousPath)) {
							paths = paths + ", " + previousPath;
						}
					}
				}

				paths = paths + ", " + node.getPath();
				duplicates.put(currentSHA1, paths);
			}

			previousSHA1 = currentSHA1;
			previousPath = node.getPath();

			String[] duplicatePaths = null;

			response.getWriter().println("--------------------------------------------------------------------");
			response.getWriter().println("Duplicate Binaries in Repository:");
			response.getWriter().println("--------------------------------------------------------------------");

			for (Map.Entry<String, String> entry : duplicates.entrySet()) {
				response.getWriter().println(entry.getKey());

				duplicatePaths = String.valueOf(entry.getValue()).split(",");

				for (String path : duplicatePaths) {
					response.getWriter().println("\t" + path);
				}
			}

		} catch (RepositoryException | IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}
}
