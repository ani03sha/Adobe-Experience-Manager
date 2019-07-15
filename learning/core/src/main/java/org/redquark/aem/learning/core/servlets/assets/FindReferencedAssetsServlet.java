package org.redquark.aem.learning.core.servlets.assets;

import static org.redquark.aem.learning.core.constants.AppConstants.DAM_ROOT;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.redquark.aem.learning.core.models.assets.AssetDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.commons.util.AssetReferenceSearch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Anirudh Sharma
 *
 */
@Component(
		service = Servlet.class, 
		property = { 
				"sling.servlet.methods=GET", 
				"sling.servlet.resourceTypes=cq/Page",
				"sling.servlet.selectors=assetreferences", 
				"sling.servlet.extensions=json", 
				"service.ranking=1000" 
				}
		)
public class FindReferencedAssetsServlet extends SlingSafeMethodsServlet {

	// Generated serial version UID
	private static final long serialVersionUID = 8446564170082865006L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		response.setContentType("application/json");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try {

			// Get the current node reference from the resource object
			Node currentNode = request.getResource().adaptTo(Node.class);

			if (currentNode == null) {
				// Every adaptTo() can return null, so let's handle the case here
				// However, it is very unlikely
				log.error("Cannot adapt resource {} to a node", request.getResource().getPath());
				response.getOutputStream().print(new Gson().toString());

				return;
			}

			// Using AssetReferenceSearch which will do all the work for us
			AssetReferenceSearch assetReferenceSearch = new AssetReferenceSearch(currentNode, DAM_ROOT,
					request.getResourceResolver());

			Map<String, Asset> result = assetReferenceSearch.search();

			List<AssetDetails> assetList = new LinkedList<>();

			for (String key : result.keySet()) {

				Asset asset = result.get(key);

				AssetDetails assetDetails = new AssetDetails(asset.getName(), asset.getPath(), asset.getMimeType());

				assetList.add(assetDetails);
			}

			String jsonOutput = gson.toJson(assetList);

			response.getOutputStream().println(jsonOutput);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}
