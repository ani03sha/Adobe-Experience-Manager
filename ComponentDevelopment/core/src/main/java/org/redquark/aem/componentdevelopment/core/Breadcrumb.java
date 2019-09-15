package org.redquark.aem.componentdevelopment.core;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

/**
 * This class is the Java backend for the Breadcrumb component
 * 
 * @author Anirudh Sharma
 *
 */
public class Breadcrumb extends WCMUsePojo {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// List that will store the pages a user has navigated through
	private List<Page> navigationList = new LinkedList<>();

	@Override
	public void activate() throws Exception {

		// The level at which to start the breadcrumb: 0 = /content, 1 = /content/site
		int level = Integer.parseInt(getProperties().get("level", "0"));

		// Current page level - The page on which the breadcrumb component is placed
		int currentLevel = getCurrentPage().getDepth();

		// Loop until we reach equal to the current level
		while (level <= currentLevel) {

			// Get the reference of the current page
			Page trailPage = getCurrentPage().getAbsoluteParent((int) level);

			// If there is no such page at this level
			if (trailPage == null) {
				break;
			}

			log.debug("Trail page: {}", trailPage.getTitle());

			// Add this page to the list
			this.navigationList.add(trailPage);

			// Increment the level so that we can find the page and their parent pages
			// recursively
			level++;
		}
	}

	/**
	 * Returns the list of the pages
	 * 
	 * @return {@link List}
	 */
	public List<Page> getNavigationList() {
		return navigationList;
	}
}
