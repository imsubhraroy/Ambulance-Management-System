package com.driver;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.DatabaseConnection;
import com.email.EmailUtility;

/**
 * Servlet implementation class ForgotDriverPassword
 */
@WebServlet("/ForgotDriverPassword")
public class ForgotDriverPassword extends HttpServlet {

    private String host;
    private String port;
    private String user;
    private String pass;

    public void init() {
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        host = "smtp.gmail.com";
        port = "587";
        user = "subhraroy894@gmail.com";
        pass = "yoqcatgpdoauevdl";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String uname = "";
        String password = null;
        String userPassword = "";
        String databaseEmailId = "";
        String resultMessage = "";
        String message = "Ambulance Management System";
        HttpSession session = request.getSession();

        try {
            ResultSet resultset = DatabaseConnection.getResultFromSqlQuery("select driver_email from tbldriver where driver_email='" + email + "'");
            if (resultset.next()) {
                databaseEmailId = resultset.getString("driver_email");
            }

            if (databaseEmailId.equals(email)) {
                ResultSet resultPassword = DatabaseConnection.getResultFromSqlQuery("select uname,upass from tbldriver where driver_email='" + email + "'");
                if (resultPassword.next()) {
                    uname = resultPassword.getString("uname");
                    password = resultPassword.getString("upass");
                    userPassword = "Hi " + uname + "," + System.lineSeparator() + System.lineSeparator() + "Your Driver Login Portal" + System.lineSeparator() + "User Name: " + uname + System.lineSeparator() + "Password: " + password + System.lineSeparator() + System.lineSeparator() + "Thank you." + System.lineSeparator() + "Ambulance Management System Team.";
                    EmailUtility.sendEmail(host, port, user, pass, email, message, userPassword);
                    resultMessage = "Your login user name & password send successfully in your email.";
                    session.setAttribute("mail-success", resultMessage);
                    response.sendRedirect("driver-forgot-password.jsp");
                }
            } else {
                String fail = "Your email id is wrong, unable to get password.";
                session.setAttribute("error", fail);
                response.sendRedirect("driver-forgot-password.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
