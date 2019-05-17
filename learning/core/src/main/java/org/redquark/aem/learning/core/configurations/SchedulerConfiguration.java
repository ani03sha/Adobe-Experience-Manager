package org.redquark.aem.learning.core.configurations;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author Anirudh Sharma
 *
 */
@ObjectClassDefinition(name = "Sling Scheduler Configuration", description = "This configuration is used to demonstrates a sling scheduler in action")
public @interface SchedulerConfiguration {

	@AttributeDefinition(
			name = "Scheduler name", 
			description = "Name of the scheduler", 
			type = AttributeType.STRING)
	public String name() default "Custom Sling Scheduler";

	@AttributeDefinition(
			name = "Enabled", 
			description = "Flag to enable/disable a scheduler", 
			type = AttributeType.STRING)
	public boolean enabled() default false;

	@AttributeDefinition(
			name = "Cron expression", 
			description = "Cron expression used by the scheduler", 
			type = AttributeType.STRING)
	public String cronExpression() default "0 * * * * ?";

	@AttributeDefinition(
			name = "Custom parameter", 
			description = "Custom parameter to showcase the usage of a sling scheduler", 
			type = AttributeType.STRING)
	public String customParameter();
}
