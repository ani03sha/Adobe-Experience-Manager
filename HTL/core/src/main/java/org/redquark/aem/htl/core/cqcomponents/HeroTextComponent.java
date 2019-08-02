package org.redquark.aem.htl.core.cqcomponents;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.redquark.aem.htl.core.beans.HeroTextBean;

import com.adobe.cq.sightly.WCMUsePojo;

/**
 * @author Anirudh Sharma
 *
 */
public class HeroTextComponent extends WCMUsePojo {

	private HeroTextBean heroTextBean;

	@Override
	public void activate() throws Exception {

		// Initializing the bean object
		heroTextBean = new HeroTextBean();

		// Getting the current resource
		Resource currentResource = getResource();

		// Getting the current node from the resource obtained above
		Node currentNode = currentResource.adaptTo(Node.class);

		// Checking the if the properties exist and if they do, set them in the bean
		// object
		if (currentNode.hasProperty("heading")) {
			heroTextBean.setHeading(currentNode.getProperty("heading").getString());
		}

		if (currentNode.hasProperty("description")) {
			heroTextBean.setDescription(currentNode.getProperty("description").getString());
		}

	}

	/**
	 * @return the heroTextBean
	 */
	public HeroTextBean getHeroTextBean() {
		return heroTextBean;
	}
}
