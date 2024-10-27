package User_Details_Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Aedit")
public class Aedit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long phone = Long.parseLong(req.getParameter("phone"));
        
        String query = "SELECT * FROM login WHERE phone=?";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/entertainment", "root", "Andhe@007");
            PreparedStatement ps = c.prepareStatement(query);
            ps.setLong(1, phone);
            ResultSet rs = ps.executeQuery();
            PrintWriter pw = resp.getWriter();
            if (rs.next()) {
//                req.setAttribute("name", rs.getString("name"));
//                req.setAttribute("phone", rs.getLong("phone"));
//                req.setAttribute("email", rs.getString("email"));
//                req.setAttribute("password", rs.getString("password"));
//                
//                RequestDispatcher rd = req.getRequestDispatcher("edit.jsp");
//                rd.forward(req, resp);
            	pw.println("<html><head><title>Edit User</title></head><body>");
                pw.println("<h2>Edit User Details</h2>");
                pw.println("<form action='Aupdate' method='post'>");
                pw.println("<input type='hidden' name='phone' value='" + phone + "'/>");
                pw.println("Name: <input type='text' name='name' value='" + rs.getString("name") + "' required/><br/>");
                pw.println("Email: <input type='email' name='email' value='" + rs.getString("email") + "' required/><br/>");
                pw.println("Password: <input type='password' name='password' value='" + rs.getString("password") + "' required/><br/>");
                pw.println("<input type='submit' value='Update'/>");
                pw.println("</form>");
                pw.println("</body></html>");
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
