package org.redquark.aem.extensions.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author Anirudh Sharma
 */
@ObjectClassDefinition(name = "My Configuration", 
					   description = "This configuration will be used to read the value of properties.")
public @interface MyConfiguration {

	@AttributeDefinition(name = "Property One", description = "Read property one", type = AttributeType.STRING)
	public String getPropertyOne() default "Property One";
	
	@AttributeDefinition(name = "Property Two", description = "Read property two", type = AttributeType.STRING)
	public String getPropertyTwo() default "Property Two";
}
