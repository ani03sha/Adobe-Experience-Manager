package org.redquark.aem.xmlreader.core.services;

import org.redquark.aem.xmlreader.core.models.ProductList;

/**
 * @author Anirudh Sharma
 */
public interface XMLWriterService {

	/**
	 * This method writes the XML data into JCR as nodes and its properties
	 */
	public void writeXMLToJCR(ProductList productList, String jcrPath, String from);
}
