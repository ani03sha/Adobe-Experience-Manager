package org.redquark.aem.learning.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.learning.core.services.GetReplicationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationStatus;
import com.day.cq.replication.Replicator;

/**
 * This service gets the replication status of the specified node path
 * 
 * @author Anirudh Sharma
 *
 */
@Component(immediate = true, service = GetReplicationData.class)
public class GetReplicationDataImpl implements GetReplicationData {

	// Default logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Injecting the instance of ResourceResolverFactory
	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	// Injecting instance of the Replicator
	@Reference
	private Replicator replicator;

	// Session object
	private Session session;

	// Instance of ReplicationSatus
	private ReplicationStatus replicationStatus;

	@Override
	public void getReplicationData(String nodePath) {

		try {

			// Map object to store service user details
			Map<String, Object> serviceUserMap = new HashMap<>();
			serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "replicationDataService");

			// Getting the instance of ResourceResolver
			ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);

			// Getting the session object by adapting ResourceResolver
			session = resourceResolver.adaptTo(Session.class);

			// Getting the status of the replication for the specified node path
			replicationStatus = replicator.getReplicationStatus(session, nodePath);

			// Check if the content is delivered
			boolean isDelivered = replicationStatus.isDelivered();

			// Check if the content is activated
			boolean isActivated = replicationStatus.isActivated();

			// Check if the content is deactivated
			boolean isDeactivated = replicationStatus.isDeactivated();

			log.info("replication status of the node --> Delivered: {}, Activated: {}, Deactivated: {}", isDelivered,
					isActivated, isDeactivated);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
