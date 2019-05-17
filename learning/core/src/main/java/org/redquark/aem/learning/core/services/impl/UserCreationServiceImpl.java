package org.redquark.aem.learning.core.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.learning.core.models.user.UserDetails;
import org.redquark.aem.learning.core.services.UserCreationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 */
@Component(service = UserCreationService.class, immediate = true)
public class UserCreationServiceImpl implements UserCreationService {

	// Default logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Injecting reference of the ResourceResolverFactory
	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	// Instance of ResourceResolver
	private ResourceResolver resourceResolver;

	// Session instance
	private Session session;

	/**
	 * This method initializes stuff for the UserManager API
	 */
	private void init() {

		log.info("Initializing stuff for creating users");

		try {
			// Creating a map for holding service user data
			Map<String, Object> serviceUserMap = new HashMap<>();
			serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "createUsersService");

			// Getting the ResourceResolver from the serviceUserMap
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);

			// Creating the session object by adapting ResourceResolver
			session = resourceResolver.adaptTo(Session.class);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void createUsers(List<UserDetails> users) {

		// Calling the method to initialize stuff
		init();

		// Getting the UserManager instance
		UserManager userManager = resourceResolver.adaptTo(UserManager.class);

		log.info("User creation starts...");

		try {

			// Loop for all the users
			for (UserDetails userObj : users) {

				// Getting user details
				String userName = userObj.getUsername();
				String firstName = userObj.getFirstname();
				String lastName = userObj.getLastname();
				String email = userObj.getEmail();
				String password = userObj.getPassword();
				String groupName = userObj.getGroup();

				// Create a group if does not exist
				Group group = null;

				if (userManager.getAuthorizable(groupName) == null) {
					// If the code comes in this block, it means user does not exist
					// We need to create a new group with the give group name
					group = userManager.createGroup(groupName);

					// Getting the instance of the ValueFactory
					ValueFactory valueFactory = session.getValueFactory();

					// Getting group name value
					Value groupNameValue = valueFactory.createValue(groupName, PropertyType.STRING);

					// Setting the property
					group.setProperty("./profile/givenName", groupNameValue);

					log.info("Group {} is successfully created", groupName);

				} else {
					// If the code comes in this block, it means the group already exists
					log.info("Group {} already exists", groupName);
				}

				// Creating a user
				User user = null;
				if (userManager.getAuthorizable(userName) == null) {
					// If the code comes in this block which means the user is not present in the
					// system.
					// We need to create a user in this case.
					user = userManager.createUser(userName, password);

					// Getting the instance of ValueFactory
					ValueFactory valueFactory = session.getValueFactory();

					// Setting different properties of the user
					Value firstNameValue = valueFactory.createValue(firstName, PropertyType.STRING);
					user.setProperty("./profile/givenName", firstNameValue);

					Value lastNameValue = valueFactory.createValue(lastName, PropertyType.STRING);
					user.setProperty("./profile/familyName", lastNameValue);

					Value emailValue = valueFactory.createValue(email, PropertyType.STRING);
					user.setProperty("./profile/email", emailValue);

					// Adding user to the group
					Group userGroup = (Group) userManager.getAuthorizable(groupName);
					userGroup.addMember(userManager.getAuthorizable(userName));

					log.info("User {} is successfully created and added to the group", userName);
				} else {
					log.info("User {} already exists in the system", userName);
				}
			}

		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (!userManager.isAutoSave()) {
					session.save();
				}
			} catch (RepositoryException e) {
				log.error(e.getMessage(), e);
			}
			if (session != null && session.isLive()) {
				session.logout();
			}
			if (resourceResolver != null) {
				resourceResolver.close();
			}
		}
	}
}
