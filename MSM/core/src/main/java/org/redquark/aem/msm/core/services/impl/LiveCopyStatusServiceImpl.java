package org.redquark.aem.msm.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.msm.core.services.LiveCopyStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.msm.api.LiveRelationship;
import com.day.cq.wcm.msm.api.LiveRelationshipManager;

/**
 * @author Anirudh Sharma
 */
@Component(immediate = true, service = LiveCopyStatusService.class)
public class LiveCopyStatusServiceImpl implements LiveCopyStatusService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference
	private LiveRelationshipManager liveRelationshipManager;
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	public String getLiveCopyStatus(String liveCopyPath) {
		
		StringBuilder sb;
		
		log.info("Getting live copy status information");
		
		try {
			
			// Service user map
			Map<String, Object> serviceUserMap = new HashMap<>();
			serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "msmService");
			
			// Getting the instance of the ResourceResolver via service user
			ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
			
			// Getting the resource representing the live copy
			Resource liveCopyResource = resourceResolver.getResource(liveCopyPath);
			
			// Getting the live relationship
			LiveRelationship liveRelationship = liveRelationshipManager.getLiveRelationship(liveCopyResource, true);
			
			// Check if there exists a live relationship
			Boolean isLC = liveRelationshipManager.hasLiveRelationship(liveCopyResource);
			
			if(!isLC) {
				log.info("The current resource does not have the live relationship");
				sb = new StringBuilder("Nothing to display");
			} else {
				// Getting the advanced status of live relationship
				Map<String, Boolean> liveRelationshipStatusMap = liveRelationship.getStatus().getAdvancedStatus();
				
				liveRelationshipStatusMap.size();
				
				sb = new StringBuilder();
				
				// Get all the details from a live copy status map into the StringBuilder object
				for(Map.Entry<String, Boolean> element : liveRelationshipStatusMap.entrySet()) {
					sb.append("{ Key: ").append(element.getKey()).append(" => Value: ").append(element.getValue()).append(" }");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return "No information found";
	}

}
