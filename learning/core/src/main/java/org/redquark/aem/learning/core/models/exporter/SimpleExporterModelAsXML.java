package org.redquark.aem.learning.core.models.exporter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * @author Anirudh Sharma
 *
 */
@Model(
		adaptables = Resource.class, 
		resourceType = "learning/components/content/exporter/simpleExporter", 
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(
		name = "jaxb", 
		extensions = "xml", 
		options = {
				@ExporterOption(
						name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", 
						value = "true"),
				@ExporterOption(
						name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", 
						value = "true")
				}
		)
@XmlRootElement
public class SimpleExporterModelAsXML {

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String description;

	@ValueMapValue(name = "sling:resourceType")
	private String resourceType;

	@ValueMapValue(name = "jcr:created")
	private String createdAt;

	@ValueMapValue(name = "jcr:createdBy")
	private String createdBy;

	@ValueMapValue(name = "jcr:lastModified")
	private String lastModifiedAt;

	@ValueMapValue(name = "jcr:lastModifiedBy")
	private String lastModifiedBy;

	@ValueMapValue(name = "jcr:primaryType")
	private String primaryType;

	/**
	 * @return the title
	 */
	@XmlElement
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	@XmlElement
	public String getDescription() {
		return description;
	}

	/**
	 * @return the resourceType
	 */
	@XmlElement
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @return the createdAt
	 */
	@XmlElement
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the createdBy
	 */
	@XmlElement
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the lastModifiedAt
	 */
	@XmlElement
	public String getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * @return the lastModifiedBy
	 */
	@XmlElement
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @return the primaryType
	 */
	@XmlElement
	public String getPrimaryType() {
		return primaryType;
	}
}
