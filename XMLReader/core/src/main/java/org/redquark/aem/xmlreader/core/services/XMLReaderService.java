package org.redquark.aem.xmlreader.core.services;

import org.redquark.aem.xmlreader.core.models.ProductList;

/**
 * @author Anirudh Sharma
 */
public interface XMLReaderService {

	/**
	 * This method reads the XML data from the file
	 */
	public ProductList readXMLFromFile(String filePath);

	/**
	 * This method writes XML data into JCR
	 */
	public ProductList readXMLFromURL(String responseURL);
}
