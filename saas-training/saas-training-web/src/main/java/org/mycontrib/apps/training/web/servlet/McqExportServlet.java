package org.mycontrib.apps.training.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mycontrib.apps.training.mcq.itf.domain.service.McqManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class McqExportServlet
 */
public class McqExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public McqExportServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mcqIdAsString=request.getParameter("mcqId");
		if(mcqIdAsString==null || mcqIdAsString.isEmpty() || mcqIdAsString.equals("null")){
			response.setContentType("text/plain");
			response.getWriter().println("no qcmId , no qcm to export !");
		}
		ServletContext servletContext = getServletContext(); // vue comme objet application au sein d'une page JSP
		WebApplicationContext springCtx =WebApplicationContextUtils.getWebApplicationContext(servletContext);
		McqManager serviceMcqManager = (McqManager)springCtx.getBean("serviceMcqManager");
		
		String format=request.getParameter("format");
		Long mcqId=(mcqIdAsString==null)?0L:Long.parseLong(mcqIdAsString);
		if(format==null)
			format="xml"; //par defaut
		if(format.equals("xml")){
			String mcqXmlString=serviceMcqManager.getMcqAsXmlString(mcqId);
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			out.println(mcqXmlString);
			
		}
	}

}
