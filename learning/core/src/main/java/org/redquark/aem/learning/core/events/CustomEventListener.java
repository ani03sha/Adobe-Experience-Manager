package org.redquark.aem.learning.core.events;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.components.ComponentContext;

/**
 * @author Anirudh Sharma
 */
@Component(service = EventListener.class, immediate = true)
public class CustomEventListener implements EventListener {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	private ResourceResolver resourceResolver;

	private Session session;

	@Activate
	protected void activate(ComponentContext componentContext) {
		log.info("Activating the observation");

		try {
			Map<String, Object> serviceUserMap = new HashMap<>();
			serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "eventingService");

			// Getting resource resolver
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);

			session = resourceResolver.adaptTo(Session.class);

			session.getWorkspace().getObservationManager().addEventListener(this,
					Event.PROPERTY_ADDED | Event.NODE_ADDED, "/apps/learning/components", true, null, null, false);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (session != null) {
			session.logout();
		}
	}

	@Override
	public void onEvent(EventIterator events) {
		try {
			while (events.hasNext()) {
				log.info("Event found at : {}", events.nextEvent().getPath());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
