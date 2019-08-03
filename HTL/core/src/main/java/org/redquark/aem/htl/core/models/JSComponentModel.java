package org.redquark.aem.htl.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.settings.SlingSettingsService;

/**
 * @author Anirudh Sharma
 *
 */
@Model(adaptables = Resource.class)
public class JSComponentModel {

	@Inject
	private SlingSettingsService slingSettingsService;

	@Inject
	@Named("sling:resourceType")
	@Default(values = "No resource type")
	protected String resourceType;

	private String message;

	@PostConstruct
	protected void init() {
		message = "This is instance: " + slingSettingsService.getSlingId() + " and resource type is: " + resourceType;
	}

	public String getMessage() {
		return message;
	}
}
