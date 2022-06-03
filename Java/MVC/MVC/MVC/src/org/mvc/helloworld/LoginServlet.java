package org.mvc.helloworld;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mvc.helloworld.dto.User;
import org.mvc.helloworld.service.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		LoginService serviceLogin = new LoginService();
		boolean result = serviceLogin.authenticate(userId, password);
		
		if (result) {
			User usr = serviceLogin.getUserDetails(userId);
			if (usr.getUserName() == null) {
				response.sendRedirect("login.jsp");
				return;
			}
			//Redirect on client			
			/*
			request.getSession().setAttribute("user", usr);
			request.getSession().setAttribute("sessionMessage", "Here's session message!");
			response.sendRedirect("success.jsp");
			*/			
			
			//Redirect on server	
			request.setAttribute("user", usr);
			request.setAttribute("dispatchMessage", "Here's dispatch message!");					
			RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
			dispatcher.forward(request, response);
			
			return;
		} else {
			response.sendRedirect("login.jsp");
			return;		
		}	
	}
}
