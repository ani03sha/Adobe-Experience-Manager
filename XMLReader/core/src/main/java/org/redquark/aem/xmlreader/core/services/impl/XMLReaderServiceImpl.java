package org.redquark.aem.xmlreader.core.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.osgi.service.component.annotations.Component;
import org.redquark.aem.xmlreader.core.models.ProductList;
import org.redquark.aem.xmlreader.core.services.XMLReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 */
@Component(immediate = true, service = XMLReaderService.class)
public class XMLReaderServiceImpl implements XMLReaderService {

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	// JAXB instance
	private JAXBContext jaxbContext;
	
	// JAXB Unmarshaller
	private Unmarshaller unmarshaller;
	

	@Override
	public ProductList readXMLFromFile(String filePath) {
		
		ProductList productList = null;

		try {

			// Creating a file object from the XML file
			File file = new File(filePath);

			jaxbContext = JAXBContext.newInstance(ProductList.class);

			unmarshaller = jaxbContext.createUnmarshaller();

			productList = (ProductList) unmarshaller.unmarshal(file);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return productList;
	}

	@Override
	public ProductList readXMLFromURL(String responseURL) {

		URLConnection urlConnection = null;
		InputStreamReader inputStreamReader = null;
		StringBuilder builder = new StringBuilder();
		ProductList productList = null;

		try {
			URL url = new URL(responseURL);
			urlConnection = url.openConnection();

			if (urlConnection != null) {
				urlConnection.setReadTimeout(30 * 1000);
			}

			if (urlConnection != null && urlConnection.getInputStream() != null) {

				inputStreamReader = new InputStreamReader(urlConnection.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				if (bufferedReader != null) {
					int eof;
					while ((eof = bufferedReader.read()) != -1) {
						builder.append((char) eof);
					}
					bufferedReader.close();
				}
			}
			inputStreamReader.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		String xmlResponse = builder.toString();
		xmlResponse = xmlResponse.substring(xmlResponse.indexOf("\n") + 1);
		
		try {
			
			jaxbContext = JAXBContext.newInstance(ProductList.class);
			
			unmarshaller = jaxbContext.createUnmarshaller();
			
			productList = (ProductList) unmarshaller.unmarshal(new StringReader(xmlResponse));
			
		} catch (JAXBException e) {
			log.info(e.getMessage(), e);
		}
		
		return productList;
	}
}
