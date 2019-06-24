package org.redquark.aem.extensions.core.services;

import org.apache.sling.api.resource.Resource;

import com.day.cq.dam.api.Asset;

/**
 * This OSGi service is used to create JPEG based renditions of an Asset
 * 
 * @author Anirudh Sharma
 */
public interface RenditionService {

	/**
	 * This method returns the rendition of an asset in the JPEG format. By default
	 * AEM generated renditions in the PNG format
	 * 
	 * @param resource
	 * @param width
	 * @param height
	 * @return {@link Asset}
	 */
	Asset generateJPEGRenditions(Resource resource, Integer width, Integer height);
}
