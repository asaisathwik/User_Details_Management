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

@WebServlet(urlPatterns = "/Alogin")
public class Alogin extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Get admin's login credentials
		long phone = Long.parseLong(req.getParameter("phone"));
		String password = req.getParameter("password");

		String query = "SELECT * FROM entertainment.login1 WHERE phone=? AND password=?";
		
		try {
			// Load and connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/entertainment", "root", "Andhe@007");
			
			// Prepare statement to check admin login credentials
			PreparedStatement ps = c.prepareStatement(query);
			ps.setLong(1, phone);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = resp.getWriter();
			
			// Check if admin login is successful
			if (rs.next()) {
				// Admin login successful, display user details
				pw.println("<html><head><title>Users Details</title></head><body>");
				pw.println("<h2>User Details</h2>");
				pw.println("<table border='2px'><tr><th>Name</th><th>Phone</th><th>Email</th><th>Password</th><th>Edit</th><th>Delete</th></tr>");
				
				// Query to retrieve all users' details
				String query1 = "SELECT * FROM login";
				PreparedStatement ps1 = c.prepareStatement(query1);
				ResultSet rs1 = ps1.executeQuery();
				
				// Iterate through the result set to display each user's details
				while (rs1.next()) {
					pw.println("<tr>");
					pw.println("<td>" + rs1.getString("name") + "</td>");  
					pw.println("<td>" + rs1.getString("phone") + "</td>");  
					pw.println("<td>" + rs1.getString("email") + "</td>");  
					pw.println("<td>" + rs1.getString("password") + "</td>"); 
					pw.println("<td> <a href='Aedit?phone=" + rs.getString("phone") + "'><button>Edit</button></a></td>"); 
					pw.println("<td><form action='Adelete' method='Post' style='display:inline;'>");
					pw.println("<input type='hidden' name='phone' value='" + rs.getString("phone") + "'>");
					pw.println("<button type='submit'>Delete</button>");
					pw.println("</form> </td>");
					pw.println("</tr>");
					pw.println("<br>");
				}
				pw.println("</table>");
				
				pw.println("</body></html>");
			} else {
				
				pw.println("<html><head><title>Login Error</title></head><body>");
				pw.println("<h1>Sorry, you entered the wrong ID or password!</h1>");
				pw.println("</body></html>");
				RequestDispatcher rd = req.getRequestDispatcher("login.html");
				rd.include(req, resp);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// Handle SQL or ClassNotFound exceptions
			e.printStackTrace();
		}
	}
}
