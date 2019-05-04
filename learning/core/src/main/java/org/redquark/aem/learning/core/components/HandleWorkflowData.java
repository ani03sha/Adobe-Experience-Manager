package org.redquark.aem.learning.core.components;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.model.WorkflowModel;

/**
 * @author Anirudh Sharma
 *
 */
@Component(immediate = true)
public class HandleWorkflowData {

	// Default logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Inject ResourceResolverFactory instance
	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	// Inject instance of WorkflowService
	@Reference
	private WorkflowService workflowService;

	// Session object
	private Session session;

	@Activate
	protected void activate() {

		log.info("Getting the workflow data...");

		try {

			// Map to store the service user details
			Map<String, Object> serviceUserMap = new HashMap<>();
			serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "handleWorkflowServiceUser");

			// Getting the instance of ResourceResolver from the ResourceResolverFactory
			ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);

			// Adapting the resource resolver to the session object
			session = resourceResolver.adaptTo(Session.class);

			// Getting the workflow session from the workflow service object using JCR
			// session
			WorkflowSession workflowSession = workflowService.getWorkflowSession(session);

			// Getting the workflows according to the states
			WorkflowModel[] workflowModels = workflowSession.getModels();

			log.info("Total number of workflows: {}", workflowModels.length);

			// Getting the workflow details and adding it to the JSON object
			for (int i = 0; i < workflowModels.length; i++) {

				// Getting details
				String id = workflowModels[i].getId();
				String title = workflowModels[i].getTitle();
				String description = workflowModels[i].getDescription();

				log.info("Workflow {} details: title:{}, description:{}", id, title, description);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}
}
