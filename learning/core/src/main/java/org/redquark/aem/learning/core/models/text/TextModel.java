package org.redquark.aem.learning.core.models.text;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Anirudh Sharma
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TextModel {

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Extra field that is not present in the component
	private String extraDetails;

	/*
	 * Injects common Sling objects that can be derived from either a
	 * SlingHttpServletRequest, a ResourceResolver or a Resource.
	 * 
	 * Here we are using SlingHttpServletRequest
	 */
	@SlingObject
	private SlingHttpServletRequest request;

	@Inject
	@Via("resource")
	private String title;

	@Inject
	@Via("resource")
	private String description;

	@PostConstruct
	protected void init() {

		extraDetails = "Extra details can be found here: http://aem.redquark.org \n";

		if (request != null) {
			this.extraDetails += "Request path: " + request.getRequestPathInfo().getResourcePath() + "\n";
		}

		extraDetails += "Title: " + title + "\n";
		extraDetails += "Description: " + description + "\n";
		
		log.info("Complete string: {}", extraDetails);
	}

	/**
	 * @return the extraDetails
	 */
	public String getExtraDetails() {
		return extraDetails;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
