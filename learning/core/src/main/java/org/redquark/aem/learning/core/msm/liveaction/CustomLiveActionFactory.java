package org.redquark.aem.learning.core.msm.liveaction;

import java.util.Collections;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.msm.api.LiveAction;
import com.day.cq.wcm.msm.api.LiveActionFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = LiveActionFactory.class, property = { CustomLiveActionFactory.actionName + "=customLiveAction" })
public class CustomLiveActionFactory implements LiveActionFactory<LiveAction> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Action name
	protected static final String actionName = LiveActionFactory.LIVE_ACTION_NAME;

	@Override
	public LiveAction createAction(Resource resource) throws WCMException {

		log.info("***** Creating action *****");

		ValueMap configs;

		// Adapt the resource to ValueMap
		if (resource == null || resource.adaptTo(ValueMap.class) == null) {
			configs = new ValueMapDecorator(Collections.<String, Object>emptyMap());
		} else {
			configs = resource.adaptTo(ValueMap.class);
		}

		return new CustomLiveAction(actionName, configs);
	}

	@Override
	public String createsAction() {
		return actionName;
	}

}
