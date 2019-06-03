package org.redquark.aem.learning.core.models.exporter;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;

/**
 * @author Anirudh Sharma
 *
 */
@Model(
		adaptables = Resource.class, 
		resourceType = "learning/components/content/exporter/simpleExporter", 
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(
		name = "jackson", 
		extensions = "json",
		selector = "model",
		options = {
				@ExporterOption(
						name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", 
						value = "true"),
				@ExporterOption(
						name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", 
						value = "true")
				}
		)
public class SimpleExporterModelAsJson {

	@Inject
	private String title;

	@Inject
	private String description;

	@Inject
	@Named("sling:resourceType")
	private String resourceType;

	@Inject
	@Named("jcr:created")
	private String createdAt;

	@Inject
	@Named("jcr:createdBy")
	private String createdBy;

	@Inject
	@Named("jcr:lastModified")
	private String lastModifiedAt;

	@Inject
	@Named("jcr:lastModifiedBy")
	private String lastModifiedBy;

	@Inject
	@Named("jcr:primaryType")
	private String primaryType;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the lastModifiedAt
	 */
	public String getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @return the primaryType
	 */
	public String getPrimaryType() {
		return primaryType;
	}
}
