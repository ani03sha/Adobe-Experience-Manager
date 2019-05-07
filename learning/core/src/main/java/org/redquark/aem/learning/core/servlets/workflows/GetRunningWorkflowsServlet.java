package org.redquark.aem.learning.core.servlets.workflows;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = Servlet.class, 
		property = { 
				Constants.SERVICE_DESCRIPTION + "=Get Running Workflows Servlet",
				"sling.servlet.methods=" + HttpConstants.METHOD_GET,
				"sling.servlet.resourceTypes=" + "learning/components/structure/page",
				"sling.servlet.selectors=" + "workflow" })
public class GetRunningWorkflowsServlet extends SlingSafeMethodsServlet {

	// Generated serial version UID
	private static final long serialVersionUID = 4909669813234357056L;

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		log.info("Received request, calculating the number of running workflows");

		try {

			// Getting the reference of the MBeanServer class
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

			// Getting the workflow MBean instance
			ObjectName workflowMBean = getWorkflowMBean(mBeanServer);

			// Get the number of running workflows items from AEM
			Object runningWorkflows = mBeanServer.invoke(workflowMBean, "countRunningWorkflows", new Object[] { null },
					new String[] { String.class.getName() });

			// Get running workflows count
			int runningWorkflowCount = (int) runningWorkflows;

			// Return the number of running workflows
			response.getWriter().write("There are " + runningWorkflowCount + " running workflows.");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private ObjectName getWorkflowMBean(MBeanServerConnection serverConnection) {

		log.info("Getting workflow MBean");

		try {

			Set<ObjectName> names = serverConnection
					.queryNames(new ObjectName("com.adobe.granite.workflow:type=Maintenance,*"), null);

			if (names.isEmpty()) {
				log.info("There are no MBeans with the specified name");
				return null;
			}

			// Returning the next value in the set
			return names.iterator().next();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
