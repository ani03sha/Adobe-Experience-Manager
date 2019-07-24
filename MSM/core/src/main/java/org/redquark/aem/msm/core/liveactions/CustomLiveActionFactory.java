package org.redquark.aem.msm.core.liveactions;

import java.util.Collections;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.redquark.aem.msm.core.configs.CustomLiveActionConfig;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.msm.api.LiveAction;
import com.day.cq.wcm.msm.api.LiveActionFactory;

/**
 * @author Anirudh Sharma
 */
@Component(service = LiveActionFactory.class, immediate = true)
@Designate(ocd = CustomLiveActionConfig.class)
public class CustomLiveActionFactory implements LiveActionFactory<LiveAction> {

	protected static String actionName = LiveActionFactory.LIVE_ACTION_NAME;

	@Activate
	protected void activate(CustomLiveActionConfig config) {
		actionName = config.liveActionName();
	}

	@Override
	public LiveAction createAction(Resource resource) throws WCMException {
		ValueMap configs;

		// Adapt the resource to the value map
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
