package org.redquark.aem.learning.core.servlets.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.learning.core.models.user.UserInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = Servlet.class,
property = { 
		Constants.SERVICE_DESCRIPTION + "=User Info Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/learning/userInfo"})
public class UserInfoServlet extends SlingSafeMethodsServlet {

	// Generated serial version uid
	private static final long serialVersionUID = 7773503512609755277L;

	// Default logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference
	private ResourceResolverFactory resolverFactory;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		
		log.info("Invoking UserInfoServlet");
		response.setContentType("text/html");
		
		// ResourceResolver instance
		ResourceResolver resolver = null;
		
		try {
			
			// Map to store details of service user
			Map<String, Object> serviceUserMap = new HashMap<>();
			serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "userInfoService");
			
			// Getting the resource resolver
			resolver = resolverFactory.getServiceResourceResolver(serviceUserMap);
			
			// Getting the resource
			Resource resource = resolver.getResource("/content/learning/en/userInfo/jcr:content/path_par/userinfo");
			
			// Getting the instance of SlingModel - UserInfoModel.java
			UserInfoModel userInfoModel = resource.adaptTo(UserInfoModel.class);
			
			// Getting values using Sling Model
			String userName = userInfoModel.getUserName();
			String firstName = userInfoModel.getFirstName();
			String lastName = userInfoModel.getLastName();
			String company = userInfoModel.getCompany();
			Long phoneNumber = userInfoModel.getPhoneNumber();
			
			// Writing values to the response
			response.getWriter().write("User details are: \n"
			+ "User Name: " + userName + "\n"
			+ "First Name: " + firstName + "\n"
			+ "Last Name: " + lastName + "\n"
			+ "Company: " + company + "\n"
			+ "Phone Number: " + phoneNumber + "\n");
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if(!resolver.isLive()) {
				resolver.close();
			}
		}
	}
}
