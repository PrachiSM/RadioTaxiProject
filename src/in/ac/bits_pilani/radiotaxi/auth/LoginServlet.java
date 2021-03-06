package in.ac.bits_pilani.radiotaxi.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.ac.bits_pilani.radiotaxi.db.AccessDB;
import in.ac.bits_pilani.radiotaxi.roles.User;
import in.ac.bits_pilani.radiotaxi.roles.driver.Driver;

/**
 * Performs user authentication
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);
    	if(session != null) {
    		request.getRequestDispatcher("/profile").forward(request, response);
    	}
    	/*
    	else {
    		request.getRequestDispatcher("index.html").include(request, response);
    	}
    	*/
    	
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if(request.getParameter("username") == null) {
			response.sendRedirect("index.html");
		}
		else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String userType = request.getParameter("Rbutton");

			Login login = new Login();

			try {
				User user = login.login(username, password, userType);
				if(user != null){
		            HttpSession session = request.getSession();
		            session.setAttribute("user", user); // add user
		            							// object to session
		            session.setAttribute("type", userType);
		            session.setMaxInactiveInterval(1800);
		            request.getRequestDispatcher("profile").forward(request, response);
				} else {
					out.println("<p align=\"center\"><font color=red>Either user name or password is wrong.</font></p>");
					request.getRequestDispatcher("index.html").include(request, response);
				}
			} catch(Exception e) { // TODO: improve error handling.
									// catching "Exception" is BAD
				// e.printStackTrace();
				request.getRequestDispatcher("html/error.html").include(request, response);
				out.println("Database error");
			}
		}
	}
}
