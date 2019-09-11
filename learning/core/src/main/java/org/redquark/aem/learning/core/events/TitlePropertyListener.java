package org.redquark.aem.learning.core.events;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

import org.apache.sling.api.resource.LoginException;
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
@Component(immediate = true, service = EventListener.class)
public class TitlePropertyListener implements EventListener {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	private ResourceResolver resourceResolver;

	private Session session;

	private ObservationManager observationManager;

	@Activate
	protected void activate(ComponentContext context) {

		Map<String, Object> serviceUserMap = new HashMap<>();
		serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "eventingService");

		try {
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
			session = resourceResolver.adaptTo(Session.class);
			observationManager = session.getWorkspace().getObservationManager();
			observationManager.addEventListener(this, Event.PROPERTY_ADDED | Event.PROPERTY_CHANGED, "/", true, null,
					null, true);
			log.info("Added event listener");
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		} catch (UnsupportedRepositoryOperationException e) {
			log.error(e.getMessage(), e);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	@Deactivate
	protected void deactivate(ComponentContext context) {
		if (observationManager != null) {
			try {
				observationManager.removeEventListener(this);
				log.info("Removed event listener");
			} catch (RepositoryException e) {
				e.printStackTrace();
			} finally {
				if (session != null && session.isLive()) {
					session.logout();
				}
			}
		}
	}

	@Override
	public void onEvent(EventIterator events) {
		while (events.hasNext()) {
			Event event = events.nextEvent();
			try {
				log.info("New property event: {}", event.getPath());
				Property changedProperty = session.getProperty(event.getPath());
				if (changedProperty.getName().equals("jcr:title") && !changedProperty.getString().endsWith("!")) {
					changedProperty.setValue(changedProperty.getString() + "!");
					session.save();
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		}
	}

}
