package org.redquark.aem.msm.core.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author Anirudh Sharma
 */
@ObjectClassDefinition(
		name = "Custom Live Action Configuration", 
		description = "This configuration will be used in custom live action for MSM")
public @interface CustomLiveActionConfig {

	@AttributeDefinition(name = "Action Name", description = "Name of Live Action", type = AttributeType.STRING)
	public String liveActionName() default "customLiveAction";
}
