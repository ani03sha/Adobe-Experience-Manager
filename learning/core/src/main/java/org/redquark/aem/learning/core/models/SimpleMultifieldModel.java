package org.redquark.aem.learning.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * @author Anirudh Sharma
 */
@Model(adaptables = Resource.class)
public class SimpleMultifieldModel {

	// Injects products node under the current node
	@Inject
	@Optional
	public Resource products;
}
