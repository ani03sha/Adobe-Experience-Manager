package org.redquark.aem.componentdevelopment.core.models;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import com.day.cq.wcm.api.Page;

/**
 * @author Anirudh Sharma
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class BreadcrumbModel {

	@Inject
	@Named("level")
	@Via("resource")
	private String startLevel;

	@Inject
	private Page currentPage;

	private List<Page> navigationList = new LinkedList<>();

	@PostConstruct
	public void init() {

		// The level at which to start the breadcrumb: 0 = /content, 1 = /content/site
		int level = Integer.parseInt(startLevel);

		// Current page level - The page on which the breadcrumb component is placed
		int currentLevel = currentPage.getDepth();

		// Loop until we reach equal to the current level
		while (level <= currentLevel) {

			// Get the reference of the current page
			Page trailPage = currentPage.getAbsoluteParent((int) level);

			// If there is no such page at this level
			if (trailPage == null) {
				break;
			}

			// Add this page to the list
			this.navigationList.add(trailPage);

			// Increment the level so that we can find the page and their parent pages
			// recursively
			level++;
		}
	}

	public List<Page> getNavigationList() {
		return navigationList;
	}
}
