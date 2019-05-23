package org.redquark.aem.xmlreader.core.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.xmlreader.core.models.Product;
import org.redquark.aem.xmlreader.core.models.ProductList;
import org.redquark.aem.xmlreader.core.services.XMLWriterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 */
@Component(immediate = true, service = XMLWriterService.class)
public class XMLWriterServiceImpl implements XMLWriterService {

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Injecting ResourceResolverFactory
	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	// JCR session
	private Session session;

	/**
	 * This method writes XML data to the JCR repository
	 */
	@Override
	public void writeXMLToJCR(ProductList productList, String jcrPath, String from) {

		log.info("Writing XML data to nodes from: {}", from);

		try {

			// Getting the session
			session = getSession();

			// Getting root node of the CRX repository
			Node root = session.getRootNode();

			// Getting the reference of the node under which we need to create our nodes
			Node xmlNode = root.getNode(jcrPath);

			// Parent node of all the children nodes which are represented by individual
			// XML items
			Node xmlDataNode = null;

			// Checks if the source is from XML file and the node is already present
			if (from.equalsIgnoreCase("file") && !xmlNode.hasNode("xml_file_products")) {
				xmlDataNode = xmlNode.addNode("xml_file_products", "sling:OrderedFolder");
			}

			// Checks if the source is from URL and the node is already present
			if (from.equalsIgnoreCase("url") && !xmlNode.hasNode("xml_url_products")) {
				xmlDataNode = xmlNode.addNode("xml_url_products", "sling:OrderedFolder");
			}

			// Setting the title of the node
			if (xmlDataNode != null) {
				xmlDataNode.setProperty("jcr:title", "Products");
			} else {
				return;
			}

			// Getting the products from ProductList
			List<Product> products = productList.getProduct();

			// Iterate for each item present in the XML file
			for (Product product : products) {
				
				Node currentNode = null;

				if (!xmlDataNode.hasNode("product_" + product.getProductId())) {
					currentNode = xmlDataNode.addNode("product_" + product.getProductId(), "nt:unstructured");
				} else {
					currentNode = xmlDataNode.getNode("product_" + product.getProductId());
				}

				// Setting properties of the node
				currentNode.setProperty("Product_ID", product.getProductId());
				currentNode.setProperty("SKU", product.getSku());
				currentNode.setProperty("Name", product.getName());
				currentNode.setProperty("Product_URL", product.getProductUrl());
				currentNode.setProperty("Price", product.getPrice());
				currentNode.setProperty("Retail_Price", product.getRetailPrice());
				currentNode.setProperty("Thumbnail_URL", product.getThumbnailUrl());
				currentNode.setProperty("Search_Keywords", product.getSearchKeywords());
				currentNode.setProperty("Description", product.getDescription());
				currentNode.setProperty("Category", product.getCategory());
				currentNode.setProperty("Category_ID", product.getCategoryId());
				currentNode.setProperty("Brand", product.getBrand());
				currentNode.setProperty("Child_SKU", product.getChildSku());
				currentNode.setProperty("Child_Price", product.getChildPrice());
				currentNode.setProperty("Color", product.getColor());
				currentNode.setProperty("Color_Family", product.getColorFamily());
				currentNode.setProperty("Color_Swatches", product.getColorSwatches());
				currentNode.setProperty("Size", product.getSize());
				currentNode.setProperty("Shoe_Size", product.getShoeSize());
				currentNode.setProperty("Pants_Size", product.getPantsSize());
				currentNode.setProperty("Occassion", product.getOccassion());
				currentNode.setProperty("Season", product.getSeason());
				currentNode.setProperty("Badges", product.getBadges());
				currentNode.setProperty("Rating_Avg", product.getRatingAvg());
				currentNode.setProperty("Rating_Count", product.getRatingCount());
				currentNode.setProperty("Inventory_Count", product.getInventoryCount());
				currentNode.setProperty("Date_Created", product.getDateCreated());
			}

			// Saving the changes to JCR
			session.save();

		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		}
	}

	private Session getSession() {
		try {
			// Map for service user details
			Map<String, Object> xmlReaderMap = new HashMap<>();
			xmlReaderMap.put(ResourceResolverFactory.SUBSERVICE, "xmlReaderSubservice");

			// Getting ResourceResovler
			ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(xmlReaderMap);

			// Getting the session by adapting the resourceResolver
			session = resourceResolver.adaptTo(Session.class);

		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		}
		return session;
	}
}
