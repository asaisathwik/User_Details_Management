package User_Details_Project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phoneString = req.getParameter("phone");
            try {
                // Parse the phone number
                long phone = Long.parseLong(phoneString);
                String query = "DELETE FROM login WHERE phone=?";
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/entertainment", "root", "Andhe@007");
                PreparedStatement ps = c.prepareStatement(query);
                ps.setLong(1, phone);
                ps.executeUpdate();
                resp.sendRedirect("index.html");

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

    }
}
