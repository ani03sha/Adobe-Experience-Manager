package org.redquark.aem.learning.core.jmx;

import com.adobe.granite.jmx.annotation.Description;

/**
 * This interface exposes the input parameters for the MBean
 * 
 * @author Anirudh Sharma
 *
 */
@Description("Input parameters for getting the system information")
public interface SystemInfo {

	@Description("Returns the status of the bundles the system")
	String getBundles(String protocol, String hostname, String port);

	@Description("Returns the status of the components in the system")
	String getComponents(String protocol, String hostname, String port);

	@Description("Returns the status of the services in the system")
	String getServices(String protocol, String hostname, String port);
}
