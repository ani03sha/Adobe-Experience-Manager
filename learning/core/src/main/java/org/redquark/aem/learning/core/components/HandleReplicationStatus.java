package org.redquark.aem.learning.core.components;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.learning.core.services.GetReplicationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component
public class HandleReplicationStatus {

	// Default logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Injecting the service to get Replication Status
	@Reference
	private GetReplicationData getReplicationData;

	/**
	 * This method gets called when the bundle gets activated
	 */
	@Activate
	protected void activate() {

		log.info("Getting the replication status data:");

		// Getting the replication status data using the OSGi service
		getReplicationData.getReplicationData("/content/learning");
	}
}
