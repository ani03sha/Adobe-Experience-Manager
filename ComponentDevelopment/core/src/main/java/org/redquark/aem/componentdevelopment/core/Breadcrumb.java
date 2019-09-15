package org.redquark.aem.componentdevelopment.core;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

/**
 * @author Anirudh Sharma
 *
 */
public class Breadcrumb extends WCMUsePojo {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// List that will store the pages a user has navigated through
	private List<Page> navigationList = new LinkedList<>();

	@Override
	public void activate() throws Exception {

		// Page level
		long level = 1L;

		long endLevel = 1L;

		int currentLevel = getCurrentPage().getDepth();

		while (level < (currentLevel - endLevel)) {

			Page trailPage = getCurrentPage().getAbsoluteParent((int) level);

			if (trailPage == null) {
				break;
			}

			log.debug("Trail page: {}", trailPage.getTitle());
			
			this.navigationList.add(trailPage);
			level++;
		}
	}

	public List<Page> getNavigationList() {
		return navigationList;
	}
}
