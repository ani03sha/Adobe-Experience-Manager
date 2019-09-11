package org.redquark.aem.learning.core.events;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.components.ComponentContext;

/**
 * @author Anirudh Sharma
 */
@Component(immediate = true, service = EventHandler.class, property = {
		EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC })
public class ReplicationTracker implements EventHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	ResourceResolver resourceResolver;

	@Activate
	protected void activate(ComponentContext context) {

		Map<String, Object> serviceUserMap = new HashMap<>();
		serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "eventService");

		try {
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleEvent(Event event) {

		log.info("Tracking replication");

		ReplicationAction action = ReplicationAction.fromEvent(event);

		if (action.getType().equals(ReplicationActionType.ACTIVATE)) {
			try {
				final PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
				final Page page = pageManager.getContainingPage(action.getPath());
				if (page != null) {
					log.info("Page Activated: {}", page.getTitle());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				if (resourceResolver != null && resourceResolver.isLive()) {
					resourceResolver.close();
				}
			}
		}
	}
}
