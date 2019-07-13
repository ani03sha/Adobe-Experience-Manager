package org.redquark.aem.learning.core.servlets.user;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.security.Privilege;
import javax.servlet.Servlet;

import org.apache.jackrabbit.api.security.JackrabbitAccessControlManager;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = Servlet.class,
property = { 
		Constants.SERVICE_DESCRIPTION + "=User Info Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/learning/userreport"})
public class UserReportServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 8505708732868658881L;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		
		Map<String, Object> serviceUserMap = new HashMap<>();
		serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "userReportService");
		try {
			
			ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
			Session session = resourceResolver.adaptTo(Session.class);
			
			UserManager userManager = resourceResolver.adaptTo(UserManager.class);
			
			Iterator<Authorizable> iterator = userManager.findAuthorizables("jcr:primaryType", "rep:Group");
			
			PrintWriter out = response.getWriter();
			
			JackrabbitAccessControlManager acm = (JackrabbitAccessControlManager) session.getAccessControlManager();
			
			Map<String, List<String>> memberMap = new HashMap<>();
			
			while (iterator.hasNext()) {
				Authorizable authorizable = (Authorizable) iterator.next();
				//out.println(authorizable.getPath());
				
				if(authorizable.isGroup()) {
					Group group = (Group) authorizable;
					
					Iterator<Authorizable> members = group.getMembers();
					List<String> memberList = new LinkedList<>();
					while (members.hasNext()) {
						Authorizable authorizable2 = (Authorizable) members.next();
						memberList.add(authorizable2.getID() + "\n");
						Set<Principal> principals = new HashSet<>();
						principals.add(authorizable2.getPrincipal());
						Privilege[] privileges = acm.getPrivileges("/content/CSCX1/columbia", principals);
						for(Privilege p : privileges) {
						//out.println(authorizable2 + ":--->" + authorizable2.getID() + ":--->" + p.getName());
						}
					}
					memberMap.put(group.getID(), memberList);
					
					writeToExcel(memberMap);
				}
				//out.println(memberMap);
				
			}
		
		} catch (LoginException | RepositoryException | IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void writeToExcel(Map<String, List<String>> memberMap) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("User Details");
		
		int rowCount = 0;
		
		Iterator<String> iterator = memberMap.keySet().iterator();
		
		while (iterator.hasNext()) {
			String groupId = iterator.next();
			Row row = sheet.createRow(++rowCount);
			int columnCount = 0;
			Cell cell = row.createCell(++columnCount);
			cell.setCellValue(groupId);
			
			List<String> members = memberMap.get(groupId);
			
			for(String member : members) {
				cell = row.createCell(++columnCount);
				cell.setCellValue(member);
			}
		}
		
		try {
			FileOutputStream stream = new FileOutputStream("E:\\development\\user.xlsx");
			workbook.write(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
