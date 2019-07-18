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
public class CompaniesListModel {

	// The name `getCompanies` corresponds to the multifield name="./companies"
	@Inject
	List<Company> companies;

	/**
	 * Company model has a name and a list of departments
	 */
	@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
	public class Company {

		@Inject
		String name;

		// The name `getDepartments` corresponds to the multifield name="./departments"
		@Inject
		List<Department> departments;
	}

	/**
	 * Department model has a name and a manager
	 */
	public class Department {

		@Inject
		String name;

		@Inject
		String manager;
	}
}
