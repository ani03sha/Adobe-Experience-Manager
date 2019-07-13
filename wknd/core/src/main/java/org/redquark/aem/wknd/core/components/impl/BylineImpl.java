package org.redquark.aem.wknd.core.components.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.factory.ModelFactory;
import org.redquark.aem.wknd.core.components.Byline;

import com.adobe.cq.wcm.core.components.models.Image;

/**
 * @author Anirudh Sharma
 */
@Model(adaptables = { SlingHttpServletRequest.class }, adapters = Byline.class, resourceType = {
		BylineImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BylineImpl implements Byline {

	protected static final String RESOURCE_TYPE = "wknd/components/content/byline";

	@ValueMapValue
	private String name;

	@ValueMapValue
	private List<String> occupations;
	
	@OSGiService
	private ModelFactory modelFactory;
	
	@Self
	private SlingHttpServletRequest request;
	
	private Image image;
	
	@PostConstruct
	private void init() {
		image = modelFactory.getModelFromWrappedRequest(request, request.getResource(), Image.class);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getOccupations() {
		if (occupations != null) {
			Collections.sort(occupations);
			return occupations;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public boolean isEmpty() {
		if (StringUtils.isBlank(name)) {
	        // Name is missing, but required
	        return true;
	    } else if (occupations == null || occupations.isEmpty()) {
	        // At least one occupation is required
	        return true;
	    } else if (getImage() == null || StringUtils.isBlank(getImage().getSrc())) {
	        // A valid image is required
	        return true;
	    } else {
	  // Everything is populated, so this component is not considered empty
	        return false;
	    }
	}

	private Image getImage() {
		return image;
		
	}
}
