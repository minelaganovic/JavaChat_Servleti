/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatservleti;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Minela
 */
@WebServlet(name = "chatWindow", urlPatterns = {"/chatWindow"})
public class chatWindow extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    String username, tempName;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String message=request.getParameter("txtMsg");
            String username=session.getAttribute("username").toString();
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ChatServlet</title>");            
            out.println("</head>");
            out.println("<br><br>");
            out.println("<center>");
            out.println("<h2>Welcome to chat ");
            out.println(username);
            out.println("</h2><br><hr>");
            out.println("<body bgcolor=\"lightblue\">");
            out.println(" <form name=\"chatWindow\" action=\"chatWindow\">");
            out.println("<br><br>");
            out.println("<br>");
            out.println("<textarea readonly=\"readonly\" name=\"txtMessage\" rows=\"20\" cols=\"60\">");           
            
            if(request.getParameter("txtMsg")!=null)
            {
                
            try{
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
                
                Statement st=con.createStatement();
                String sql="insert into textmessage (username,message) values('"+username+"','"+message+"')";
                st.executeUpdate(sql);
                st.execute("commit");                
            }
            catch(Exception ex1)
            {
                System.err.println(ex1.getMessage());
                String messages="No";
                out.println(messages);
            }
        }
            //prikaz poslatih poruka
            try{
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
                
                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery("select *from `chat`.`textmessage`");
                
                while(rs.next())
                {
                    String messages=rs.getString(1)+": "+rs.getString(2);
                    out.println(messages);
                }
                con.close();
            }
            catch(Exception ex1)
            {
                System.err.println(ex1.getMessage());
            }
            out.println("</textarea>");
            out.println("<br><br><hb>");
            out.println("Enter message:  <input type=\"text\" name=\"txtMsg\" value=\"\"/>   <input type=\"submit\" name=\"cmdSend\" value=\"Send\"/>");
            out.println("<br>");
            out.println("<br><a href=\"chatWindow\">Refresh Chat!</a>");
            out.println("<hr>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception e)
        {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet...</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet failed</h1>");
            out.println("</body>");
            out.println("</html>");
            System.out.println(e);
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
            throws ServletException, IOException
    {
        session=request.getSession();
        if(username!=null)
        {
            tempName=username;
        }
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
    HttpSession session;
}
