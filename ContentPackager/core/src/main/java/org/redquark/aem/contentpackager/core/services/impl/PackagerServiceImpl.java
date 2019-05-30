package org.redquark.aem.contentpackager.core.services.impl;

import java.io.PrintWriter;
import java.util.List;

import javax.jcr.Session;

import org.apache.jackrabbit.vault.fs.api.PathFilterSet;
import org.apache.jackrabbit.vault.fs.config.DefaultWorkspaceFilter;
import org.apache.jackrabbit.vault.packaging.JcrPackage;
import org.apache.jackrabbit.vault.packaging.JcrPackageDefinition;
import org.apache.jackrabbit.vault.packaging.JcrPackageManager;
import org.apache.jackrabbit.vault.packaging.Packaging;
import org.apache.jackrabbit.vault.util.DefaultProgressListener;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import static org.redquark.aem.contentpackager.core.constants.AppConstants.DEFAULT_GROUP;
import org.redquark.aem.contentpackager.core.models.ContentFilters;
import org.redquark.aem.contentpackager.core.services.PackagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = PackagerService.class, immediate = true)
public class PackagerServiceImpl implements PackagerService {

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Injecting reference of Packaging
	@Reference
	private Packaging packaging;

	// JCR Session
	private Session session;

	/**
	 * This method creates a package in the JCR using specified group, name and
	 * content filter paths
	 */
	@Override
	public boolean createPackage(String packageName, String groupName, List<ContentFilters> contentFilters,
			SlingHttpServletRequest request) {

		try {

			// Getting the default workspace filter
			DefaultWorkspaceFilter defaultWorkspaceFilter = new DefaultWorkspaceFilter();

			// This will define the content filter path
			PathFilterSet pathFilterSet;

			// Loop for all the paths defined in the input file
			for (ContentFilters filter : contentFilters) {

				// Setting the PathFilterSet for a specific content path
				pathFilterSet = new PathFilterSet(filter.getContentFilterPath());

				// Adding the PathFilterSet object to the DefaultWorkspaceFilter
				defaultWorkspaceFilter.add(pathFilterSet);
			}

			// Getting ResourceResolver from the sling request
			ResourceResolver resolver = request.getResourceResolver();

			// Getting the JCR session by adapting the ResourceResolver
			session = resolver.adaptTo(Session.class);

			// Getting the instance of the AEM's JCR package manager
			final JcrPackageManager jcrPackageManager = packaging.getPackageManager(session);

			JcrPackage jcrPackage;

			if (groupName != null && !groupName.isEmpty()) {
				// Creating a package with the basic details
				jcrPackage = jcrPackageManager.create(groupName, packageName);
			} else {
				// Creating a package with the basic details
				jcrPackage = jcrPackageManager.create(DEFAULT_GROUP, packageName);
			}

			// Getting the package definition from the JcrPackage's instance
			JcrPackageDefinition jcrPackageDefinition = jcrPackage.getDefinition();

			// Setting the filter in the package definition which has all the content paths
			jcrPackageDefinition.setFilter(defaultWorkspaceFilter, false);

			// Getting the PrintWriter to output the response on the successful completion
			// of the process
			PrintWriter out = new PrintWriter(System.out);

			// Creating a new package in the AEM's package manager
			jcrPackageManager.assemble(jcrPackage, new DefaultProgressListener(out));

			return true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
