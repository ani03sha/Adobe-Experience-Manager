package org.redquark.aem.componentdevelopment.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * @author Anirudh Sharma
 *
 */
@Model(adaptables = Resource.class)
public class CarouselModel {

	@Inject
	@Optional
	public Resource carousels;
}
