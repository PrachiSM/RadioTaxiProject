package in.ac.bits_pilani.radiotaxi.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogoutServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		// invalidate the session if exists
		HttpSession session = request.getSession(false);
		if(session != null) {
			try {
				session.invalidate();
			} catch (IllegalStateException e) { // called on an already invalidated session
				e.printStackTrace();
			}
		}
		PrintWriter out = response.getWriter();
		out.println("<p align=\"center\"><font color=green>Successfully logged out!</font></p>");
		request.getRequestDispatcher("index.html").include(request, response);
	}

}
