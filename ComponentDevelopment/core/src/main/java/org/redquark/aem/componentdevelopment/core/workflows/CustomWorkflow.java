package org.redquark.aem.componentdevelopment.core.workflows;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = WorkflowProcess.class, property = { "process.label" + "= Custom Workflow" })
public class CustomWorkflow implements WorkflowProcess {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		log.info("Executing the workflow...");

		try {
			String textValue = metaDataMap.get("textValue", "empty");
			String dateValue = metaDataMap.get("dateValue", "empty");

			log.info("Text value: {}, Date value: {}", textValue, dateValue);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
