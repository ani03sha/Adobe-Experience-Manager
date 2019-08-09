package org.redquark.aem.htl.core.utils;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to parse HTML
 * 
 * @author Anirudh Sharma
 */
public class DataParser {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public Document documentParse(String url) {
		try {
			return Jsoup.connect(url).get();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public List<String> parseLinks(String url) {

		List<String> hyperLinks = new LinkedList<>();

		try {

			// Get all the links from the href attribute of the anchor tag
			Elements links = documentParse(url).select("a[href]");

			for (Element link : links) {
				log.debug(link.attr("abs:href"));

				// Add link to the list of links
				hyperLinks.add(link.attr("abs:href"));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return hyperLinks;
	}

	public List<String> parseImports(String url) {

		List<String> importlist = new LinkedList<>();

		try {
			Elements imports = documentParse(url).select("link[href]");

			for (Element importElement : imports) {
				log.debug(importElement.attr("abs:href"));

				// Add import to the importList
				importlist.add(importElement.attr("abs:href"));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return importlist;
	}

	public List<String> parseImages(String url) {

		List<String> imageList = new LinkedList<>();

		try {
			Elements images = documentParse(url).select("[src]");

			for (Element image : images) {
				log.debug(image.attr("abs:href"));

				if (image.tagName().equals("img")) {
					// Add image to the imageList
					imageList.add(image.attr("abs:src"));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return imageList;
	}
}
