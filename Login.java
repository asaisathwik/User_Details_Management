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

@WebServlet(urlPatterns = "/Login")
public class Login extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		long phone = Long.parseLong(req.getParameter("phone"));
		String password = req.getParameter("password");
		String query = "select * from 	entertainment.login where phone=? AND password=?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306 ? user=root && password= Andhe@007");
			PreparedStatement ps = c.prepareStatement(query);
			ps.setLong(1, phone);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
//			RequestDispatcher rd = req.getRequestDispatcher("Display.html");
//			rd.forward(req, resp);
				PrintWriter pw = resp.getWriter();
				pw.println("<html><head><title>User Details</title></head><body>");
				pw.println("<h2>User Details</h2>");
				pw.println("<table border='1'><tr><th>Name</th><th>Phone</th><th>Email</th><th>Password</th></tr>");

				pw.println("<tr>");
				pw.println("<td>" + rs.getString("name") + "</td>");
				pw.println("<td>" + rs.getString("phone") + "</td>");
				pw.println("<td>" + rs.getString("email") + "</td>");
				pw.println("<td>" + rs.getString("password") + "</td>");
				pw.println("</tr>");

				pw.println("</table>");
				pw.println(" <a href='Aedit?phone=" + rs.getString("phone") + "'><button>Edit</button></a>"); 
				pw.println("<form action='delete' method='post' style='display:inline;'>");
				pw.println("<input type='hidden' name='phone' value='" + rs.getString("phone") + "'>");
				pw.println("<button type='submit'>Delete</button>");
				pw.println("</form>");
				pw.println("<br>");

				pw.println("</body></html>");
			} else {

				PrintWriter pw = resp.getWriter();
				pw.println("<body><head><h1>Sorry You Entered Wrong id or password</h1></head></body> ");
				RequestDispatcher rd = req.getRequestDispatcher("login.html");
				rd.include(req, resp);
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
