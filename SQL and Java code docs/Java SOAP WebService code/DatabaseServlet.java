/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservlet;

import cimdatabase.CIMdatabase_Service;
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
 * @author peba007
 */
@WebServlet(name = "DatabaseServlet", urlPatterns = {"/DatabaseServlet"})
public class DatabaseServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "C:/Users/peba007/Documents/NetBeansProjects/DatabaseService/src/conf/xml-resources/web-service-references/CIMdatabase/wsdl/localhost_8080/CIMdatabase/CIMdatabase.wsdl")
    private CIMdatabase_Service service;

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
            
            sendToDB(uuidSel, uuid, nN, ntN, ntD, ntaN, ntaD);
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title> Data Entered</title>");
            out.println("</head>");
            out.println("<body>"); 
            out.println("Your data has been entered.<br>");
            
            out.println("<table style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th>mRID</th>");
            out.println("<th> Name: Name</th>");
            out.println("<th>Name Type: Name</th>");
            out.println("<th>Name Type: Description</th>");
            out.println("<th>Name Type Authority: Name</th>");
            out.println("<th>Name Type Authority: Description</th>");
            out.println("</tr><tr>");
                    
            for(int j = 0; j < showDB().size(); j++){
                if (j>0 && j%6 == 0){
                    out.println("</tr><tr>");
                }
                out.println("<td>" + showDB().get(j) + "</td>");
            }
            
            out.println("</table></tr>");
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

    private void sendToDB(boolean arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4, java.lang.String arg5, java.lang.String arg6) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        cimdatabase.CIMdatabase port = service.getCIMdatabasePort();
        port.sendToDB(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    private java.util.List<java.lang.String> showDB() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        cimdatabase.CIMdatabase port = service.getCIMdatabasePort();
        return port.showDB();
    }

}
