package org.redquark.aem.learning.core.services.impl;

import org.osgi.service.component.annotations.Component;
import org.redquark.aem.learning.core.services.CheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 */
@Component(service = CheckService.class, property = { "service.label=one" })
public class CheckServiceImplOne implements CheckService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String message(String name) {
		log.info("The name of the user is: {}", name);
		return "Hello from service implementation one to " + name;
	}

}
