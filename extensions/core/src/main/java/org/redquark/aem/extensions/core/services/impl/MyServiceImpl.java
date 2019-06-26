package org.redquark.aem.extensions.core.services.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.redquark.aem.extensions.core.config.MyConfiguration;
import org.redquark.aem.extensions.core.services.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 */
@Component(service = MyService.class, 
		   property = {
				   "label=My Service Implementation"
				   }
		)
@Designate(ocd = MyConfiguration.class)
public class MyServiceImpl implements MyService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	// Two properties to be read
	private String propertyOne;
	private String propertyTwo;
	
	@Activate
	protected void activate(MyConfiguration config) {
		// Reading properties from the configuration
		propertyOne = config.getPropertyOne();
		propertyTwo = config.getPropertyTwo();
		
	}
	
	@Override
	public String getPassword(String type) {
		
		// MD5 equivalent of password string
		String passwordHash = null;
		
		try {
			
			type = type + propertyOne + propertyTwo;
			
			log.info("Resulant password: " + type);
			
			// Convert string to bytes - this is for the sample implementation (for show casing)
			byte[] passwordByte = type.getBytes("UTF-8");
			
			// Getting instance of MessageDigest
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			// Convert bytes array to hash using MD5 algorithm
			byte[] digest = md.digest(passwordByte);
			
			passwordHash = new String(digest);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return passwordHash;
	}

}
