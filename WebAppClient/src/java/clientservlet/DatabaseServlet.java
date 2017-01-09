/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservlet;

import cimidentities.DatabaseService_Service;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author pdlo003
 */
@WebServlet(name = "DatabaseServlet", urlPatterns = {"/DatabaseServlet"})
public class DatabaseServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "C://Users/pdlo003/Documents/NetBeansProjects/WebAppClient/src/conf/xml-resources/web-service-references/DatabaseService/wsdl/localhost_9090/WebApp/DatabaseService.wsdl")
    private DatabaseService_Service service;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {            
            
            String uuidEnt = request.getParameter("uuidEnt");
            boolean uuidSel = false;
            String uuid = request.getParameter("enter_uuidBox");
            String nNtext = request.getParameter("n_nameBox");
            String ntNtext = request.getParameter("nt_nameBox");
            String ntDtext = request.getParameter("nt_desBox");
            String ntaNtext = request.getParameter("nta_nameBox");
            String ntaDtext = request.getParameter("nta_desBox");
            
            if("false".equals(uuidEnt)){
                uuidSel = false;
            }
            else if("true".equals(uuidEnt)){
                uuidSel = true;
            }
            
            java.lang.String nN = nNtext;
            java.lang.String ntN = ntNtext;
            java.lang.String ntD = ntDtext;
            java.lang.String ntaN = ntaNtext;
            java.lang.String ntaD = ntaDtext;
            
            sendToDB(nN, ntN, ntD, ntaN, ntaD, uuid, uuidSel);
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title> Data Entered</title>");
            out.println("</head>");
            out.println("<body>"); 
            out.println("Your data has been entered.<br><br>");
            
            out.println("<table style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th>mRID</th>");
            out.println("<th> Name: Name</th>" );
            out.println("<th>Name Type: Name</th>");
            out.println("<th>Name Type: Description</th>");
            out.println("<th>Name Type Authority: Name</th>");
            out.println("<th>Name Type Authority: Description</th>");
            out.println("</tr><tr>");
                    
    /*        for(int j = 0; j < showDB().size(); j++){
                if (j>0 && j%6 == 0){
                    out.println("</tr><tr>");
                }
                out.println("<td>" + showDB().get(j) + "</td>");
            }
            
     */       out.println("</table></tr>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void sendToDB(java.lang.String nName, java.lang.String ntName, java.lang.String ntDes, java.lang.String ntaName, java.lang.String ntaDes, java.lang.String uuid, boolean uuidEntered) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        cimidentities.DatabaseService port = service.getDatabaseServicePort();
        port.sendToDB(nName, ntName, ntDes, ntaName, ntaDes, uuid, uuidEntered);
    }

   

}
