package org.redquark.aem.learning.core.cq.text;

import com.adobe.cq.sightly.WCMUsePojo;

/**
 * @author Anirudh Sharma
 */
public class InfoComponent extends WCMUsePojo {

	private String details;

	@Override
	public void activate() throws Exception {

		String fullName = get("fullName", String.class);
		Integer age = Integer.parseInt(get("age", String.class));
		Boolean isMarried = Boolean.parseBoolean(get("married", String.class));

		details = fullName + " is " + age + " years old and is " + (isMarried ? "married" : " no married");
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

}
