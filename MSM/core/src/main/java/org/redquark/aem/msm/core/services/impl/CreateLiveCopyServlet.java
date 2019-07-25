package org.redquark.aem.msm.core.services.impl;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.msm.core.services.CreateLiveCopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 */
@Component(immediate = true, service = Servlet.class, property = {
		"sling.servlet.paths=/bin/clc",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class CreateLiveCopyServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 7304916580323630467L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference
	private CreateLiveCopyService createLiveCopyService;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		log.debug("executing servlet");
		try {
			createLiveCopyService.createLiveCopies("/content/we-retail/us/en/about-us");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
