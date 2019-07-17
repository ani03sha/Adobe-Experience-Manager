package org.redquark.aem.learning.core.models.text;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * @author Anirudh Sharma
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InfoModel {

	@Inject
	@Optional
	@Default(values = "Test")
	private String fullName;

	@Inject
	@Optional
	@Default(values = "0")
	private String age;

	@Inject
	@Optional
	@Named("married")
	@Default(values = "false")
	private String isMarried;

	// Variable that will be read in the HTL file
	private String details;

	@PostConstruct
	protected void init() {

		details = fullName + " is " + Integer.parseInt(age) + " years old and is "
				+ (Boolean.parseBoolean(isMarried) ? "married" : " not married");
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return this.details;
	}

}
