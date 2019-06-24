package org.redquark.aem.extensions.core.services.impl;

import java.util.Arrays;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.extensions.core.services.RenditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

/**
 * @author Anirudh Sharma
 */
@Component(service = WorkflowProcess.class, property = { "process.label=JPEG Rendition Generator Step" })
public class GenerateJPEGThumbnailProcess implements WorkflowProcess {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Injecting reference of RenditonService
	@Reference
	private RenditionService renditionService;

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		try {
			// Getting the resource resolver from the workflow session
			ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);

			// Payload path
			String path = getPayloadPath(workItem.getWorkflowData());

			if (StringUtils.isEmpty(path)) {
				throw new WorkflowException("Failed to get Asset path from the payload");
			}

			// Process arguments passed by the user in the workflow step
			List<String> processArgs = Arrays.asList(metaDataMap.get("PROCESS_ARGS", "").split(","));

			if (processArgs.size() < 2) {
				throw new WorkflowException(
						"Specify the width and height in process arguments eg. width=400,height=400");
			}

			// Getting user defined width of the rendition
			Integer width = NumberUtils
					.createInteger(processArgs.get(0).substring(processArgs.get(0).indexOf("=") + 1));

			// Getting user defined height of the rendition
			Integer height = NumberUtils
					.createInteger(processArgs.get(1).substring(processArgs.get(1).indexOf("=") + 1));

			// JCR session
			Session session = workflowSession.adaptTo(Session.class);

			// Resource on which we need to run the process step
			Resource resource = resolver.getResource(path);

			// Generating JPEG renditions for the image uploaded
			renditionService.generateJPEGRenditions(resource, width, height);

			session.save();

		} catch (RepositoryException e) {
			log.error("Error executing generating thumbnail process", e);
		}
	}

	/**
	 * This method returns the path of the payload
	 * 
	 * @param workflowData
	 * @return {@link String}
	 */
	private String getPayloadPath(WorkflowData workflowData) {

		String payloadPath = null;

		// Execute only if the payload is a type of a JCR path
		if (workflowData.getPayloadType().equals("JCR_PATH")) {
			payloadPath = (String) workflowData.getPayload();
		}

		return payloadPath;
	}
}
