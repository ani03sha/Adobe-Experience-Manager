package org.redquark.aem.msm.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.msm.core.services.CreateLiveCopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.msm.api.RolloutManager;

/**
 * @author Anirudh Sharma
 */
@Component(immediate = true, service = CreateLiveCopyService.class)
public class CreateLiveCopyServiceImpl implements CreateLiveCopyService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private RolloutManager rolloutManager;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	// Service user map
	Map<String, Object> serviceUserMap;

	@Activate
	protected void activate() {
		serviceUserMap = new HashMap<>();
		serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "msmService");
	}

	@Override
	public void createLiveCopies(String sourcePath) {
		log.info("***** Started creating live copies *****");

		try {

			// Getting resource resolver
			ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);

			// Adapting resource resolver to the PageManager API
			PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
			
			// Getting the reference of the master page
			Page master = pageManager.getPage(sourcePath);
			
			// Getting the instance of RolloutParams
			RolloutManager.RolloutParams rolloutParams = new RolloutManager.RolloutParams();
			
			// Setting different attributes
			rolloutParams.master = master;
			rolloutParams.isDeep = true;
			
			rolloutManager.rollout(rolloutParams);
			
		} catch (LoginException | WCMException e) {
			log.error(e.getMessage(), e);
		}

	}

}
