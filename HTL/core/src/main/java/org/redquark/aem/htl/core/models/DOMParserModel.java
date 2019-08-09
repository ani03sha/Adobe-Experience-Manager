package org.redquark.aem.htl.core.models;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.redquark.aem.htl.core.utils.DataParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Model(adaptables = Resource.class)
public class DOMParserModel {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	@Named("imp")
	private String imports;

	@Inject
	@Named("link")
	private String links;

	@Inject
	@Named("img")
	private String images;

	@Inject
	private String url;

	private List<String> urlList;
	private List<String> importList;
	private List<String> imageList;

	@PostConstruct
	public void init() {

		// Initializing lists
		this.urlList = new LinkedList<>();
		this.importList = new LinkedList<>();
		this.imageList = new LinkedList<>();

		log.info("URL is: {}", this.url);

		if (this.links.equalsIgnoreCase("true")) {
			this.urlList = new DataParser().parseLinks(this.links);
			log.debug("File size: {}", Integer.valueOf(urlList.size()));
		}

		if (this.images.equalsIgnoreCase("true")) {
			this.imageList = new DataParser().parseImages(this.links);
			log.debug("Image size: {}", Integer.valueOf(imageList.size()));
		}

		if (this.imports.equalsIgnoreCase("true")) {
			this.importList = new DataParser().parseImports(this.links);
			log.debug("Import size: {}", Integer.valueOf(importList.size()));
		}
	}

	/**
	 * @return the urlList
	 */
	public List<String> getUrlList() {
		return urlList;
	}

	/**
	 * @return the importList
	 */
	public List<String> getImportList() {
		return importList;
	}

	/**
	 * @return the imageList
	 */
	public List<String> getImageList() {
		return imageList;
	}

}
