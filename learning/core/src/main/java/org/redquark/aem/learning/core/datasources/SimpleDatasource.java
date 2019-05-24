package org.redquark.aem.learning.core.datasources;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.service.component.annotations.Activate;
import org.redquark.aem.learning.core.constants.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;

/**
 * @author Anirudh Sharma
 *
 */
public class SimpleDatasource extends WCMUsePojo {

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Activate
	public void activate() throws Exception {

		// Getting the resource resolver
		final ResourceResolver resourceResolver = getResourceResolver();

		// Getting the data path value which represents the location of the dropdown
		// values
		String dataPath = ResourceUtil.getValueMap(getResource().getChild("datasource")).get("data_path", String.class);

		log.info("Data path is: {}", dataPath);

		// Getting the resource
		Resource resource = resourceResolver.getResource(AppConstants.DATASOURCE_PATH + dataPath);

		log.info("Resource is: {}", resource);

		// Map to store the datasource details
		Map<String, String> data = new LinkedHashMap<>();

		// Getting the current node
		Node currentNode = resource.adaptTo(Node.class);

		// Get iterator for all the nodes under current node
		NodeIterator nodeIterator = currentNode.getNodes();

		// Loop for each node
		while (nodeIterator.hasNext()) {

			// Get the node
			Node node = nodeIterator.nextNode();

			// Set the values in the Map
			if (!node.hasProperty("value")) {
				data.put(node.getName(), node.getProperty("name").getString());
			} else if (node.hasProperty("name")) {
				data.put(node.getProperty("value").getValue().getString(),
						node.getProperty("name").getValue().getString());
			} else {
				data.put(node.getProperty("value").getValue().getString(), node.getName());
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			DataSource ds = new SimpleDataSource(new TransformIterator<>(data.keySet().iterator(), new Transformer() {

				@Override
				public Object transform(Object o) {

					String dropValue = (String) o;

					ValueMap vm = new ValueMapDecorator(new HashMap<>());

					vm.put("value", dropValue);
					vm.put("text", data.get(dropValue));

					return new ValueMapResource(resourceResolver, new ResourceMetadata(), "nt:unstructured", vm);
				};
			}));

			// Setting the datasource in the request as an attribute
			getRequest().setAttribute(DataSource.class.getName(), ds);
		}
	}

}
