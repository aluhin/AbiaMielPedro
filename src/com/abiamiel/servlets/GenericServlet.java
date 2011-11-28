package com.abiamiel.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abiamiel.model.Customer;
import com.abiamiel.model.InfoBean;

public class GenericServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void sendError(HttpServletRequest request, HttpServletResponse response, InfoBean info) throws IOException, ServletException {
		request.setAttribute("errorBean", info);
		RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_ERROR);
		dispatcher.forward(request, response);
	}

	protected Customer loggedCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		return loggedCustomer(request, response, false);
	}
	
	protected Customer loggedCustomer(HttpServletRequest request, HttpServletResponse response, boolean requiresAdmin) throws IOException, ServletException {
		
		Customer customer = null;
		HttpSession httpSession = request.getSession(false);
		
		if (httpSession != null)
			customer  = (Customer) httpSession.getAttribute("customer");
		
		if (customer == null) {
			sendError(request, response, new InfoBean("Error", "Para realizar esa accion debe de loguearse en el sistema primero. Para ello, pinche en el enlace de la derecha \"Mi cuenta\""));
		}
		else if (requiresAdmin && !customer.isAdmin()) {
			sendError(request, response, new InfoBean("Error", "Para realizar esa accion debe de loguearse en el sistema con privilegios de administrador"));
			customer = null;
		}
		return customer;
	}
}
