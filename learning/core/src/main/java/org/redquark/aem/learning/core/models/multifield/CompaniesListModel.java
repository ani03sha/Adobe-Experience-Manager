package org.redquark.aem.learning.core.models.multifield;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * @author Anirudh Sharma
 *
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface CompaniesListModel {

	// The name `getCompanies` corresponds to the multifield name="./companies"
	@Inject
	List<Company> getCompanies();

	/**
	 * Company model has a name and a list of departments
	 */
	@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
	interface Company {

		@Inject
		String getName();

		// The name `getDepartments` corresponds to the multifield name="./departments"
		@Inject
		List<Department> getDepartments();
	}

	/**
	 * Department model has a name and a manager
	 */
	interface Department {

		@Inject
		String getName();

		@Inject
		String getManager();
	}
}
