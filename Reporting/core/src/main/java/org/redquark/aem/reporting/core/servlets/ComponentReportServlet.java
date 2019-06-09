package org.redquark.aem.reporting.core.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_POST,
		"sling.servlet.paths=" + "/bin/reports/component" })
public class ComponentReportServlet extends SlingAllMethodsServlet {

	// Generates serial version UID
	private static final long serialVersionUID = -7024907417541933763L;

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		log.info("Invoking component report servlet...");

		try {
			String reportName = request.getParameter("reportName");
			String searchPath = request.getParameter("searchPath");
			String componentPath = request.getParameter("componentPath");
			boolean isActivated = Boolean.parseBoolean(request.getParameter("isActivated"));

			if (searchPath.isEmpty() || searchPath == null) {
				searchPath = "/content";
			}

			String queryString = "SELECT * FROM [cq:PageContent] AS parent INNER JOIN [nt:unstructured] AS child ON ISDESCENDANTNODE(child,parent) WHERE ISDESCENDANTNODE(parent,["
					+ searchPath + "]) AND child.[sling:resourceType]='" + componentPath + "'";

			if (isActivated) {
				queryString += " AND parent.[cq:lastReplicationAction] = 'Activate'";
			}

			// Getting the ResourceResolver from the current request
			ResourceResolver resourceResolver = request.getResourceResolver();

			// Getting the session instance by adapting ResourceResolver
			Session session = resourceResolver.adaptTo(Session.class);

			QueryManager queryManager = session.getWorkspace().getQueryManager();

			Query query = queryManager.createQuery(queryString, "JCR-SQL2");

			QueryResult queryResult = query.execute();

			RowIterator rowIterator = queryResult.getRows();

			Map<String, String> data = new TreeMap<>();

			// data.put("Page path", "Count");

			while (rowIterator.hasNext()) {
				Row row = rowIterator.nextRow();
				String path = StringUtils.substringBefore(row.getPath("child"), "jcr:content");
				Integer temp = StringUtils.isNotBlank(data.get(path)) ? Integer.parseInt(data.get(path)) : 0;
				data.put(path, (temp == 0) ? String.valueOf(1) : String.valueOf(temp + 1));
			}
			writeDataToFile(reportName, data, response);
			response.getWriter().println(data.toString());
		} catch (RepositoryException | IOException e) {
			log.error(e.getMessage(), e);
			try {
				response.getWriter().println(e.getMessage());
			} catch (IOException e1) {
				log.error(e1.getMessage());
			}
		}
	}

	private void writeDataToFile(String reportName, Map<String, String> data, SlingHttpServletResponse response) {

		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Component data");

		Set<String> keyset = data.keySet();

		org.apache.poi.ss.usermodel.Row row = sheet.createRow(0);

		Cell pageTitle = row.createCell(0);
		pageTitle.setCellValue("Page path");

		Cell countTitle = row.createCell(1);
		countTitle.setCellValue("Number of times component appear on a page");

		int rowNum = 1;

		for (String key : keyset) {

			row = sheet.createRow(rowNum++);

			Cell pagePath = row.createCell(0);
			pagePath.setCellValue(key);

			Cell count = row.createCell(1);
			count.setCellValue(data.get(key));
		}

		try {

			response.setContentType("application/ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + reportName + ".xlsx");

			// Write the workbook in file system
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}