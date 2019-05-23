package org.redquark.aem.xmlreader.core.configurations;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author Anirudh Sharma
 *
 */
@ObjectClassDefinition(
		name = "Red Quark XML Reader", 
		description = "This configuration lets us read XML file from the system or XML response from a URLl̥")
public @interface XMLReaderConfiguration {
	
	@AttributeDefinition(
			name = "Scheduler name", 
			description = "Name of the scheduler", 
			type = AttributeType.STRING)
	public String name() default "XML Reader Scheduler";

	@AttributeDefinition(
			name = "Enabled", 
			description = "Flag to enable/disable a scheduler", 
			type = AttributeType.BOOLEAN)
	public boolean enabled() default true;

	@AttributeDefinition(
			name = "Cron expression", 
			description = "Cron expression used by the scheduler", 
			type = AttributeType.STRING)
	public String cronExpression() default "0 * * * * ?";

	@AttributeDefinition(
			name = "XML file path", 
			description = "Path of the XML file on the system", 
			type = AttributeType.STRING)
	public String xmlFilePath();
	
	@AttributeDefinition(
			name = "XML response URL", 
			description = "URL from where XML response is to be read", 
			type = AttributeType.STRING)
	public String xmlResponseURL();
	
	@AttributeDefinition(
			name = "JCR path", 
			description = "Path in the JCR to store data", 
			type = AttributeType.STRING)
	public String jcrPath();
}
