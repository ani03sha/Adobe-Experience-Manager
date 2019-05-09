package org.redquark.aem.learning.core.models.user;


import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * This Sling Model class represent a user portfolio i.e. its details
 * 
 * @author Anirudh Sharma
 *
 */
@Model(adaptables = Resource.class)
public class UserInfoModel {

	// User name of the user
	@Inject
	private String userName;

	// First name of the user
	@Inject
	private String firstName;

	// Last name of the user
	@Inject
	private String lastName;

	// Company of the user
	@Inject
	private String company;

	// Phone number of the user
	@Inject
	private long phoneNumber;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @return the phoneNumber
	 */
	public long getPhoneNumber() {
		return phoneNumber;
	}
}
