package com.abiamiel.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.abiamiel.model.Customer;
import com.abiamiel.model.InfoBean;

public class GenericServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(GenericServlet.class);

	protected void sendError(HttpServletRequest request, HttpServletResponse response, InfoBean info) throws IOException, ServletException {
		request.setAttribute("errorBean", info);
		RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_ERROR);
		dispatcher.forward(request, response);
		logger.info("Showing info to the customer: " + info);
	}

	protected void sendTooManyUserConectionsError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		sendError(request, response, new InfoBean("Error interno", "Demasiados clientes estan accediendo a la base de datos al mismo momento. Por favor, intentelo de nuevo en unos minutos"));
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
			logger.info("Unknown user trying to do something without beeing logged");
			sendError(request, response, new InfoBean("Error de autorizacion", "Para realizar esa accion debe de loguearse en el sistema primero. Para ello, pinche en el enlace de la derecha \"Mi cuenta\""));
		}
		else if (requiresAdmin && !customer.isAdmin()) {
			logger.info(String.format("User %s trying to enter admin functionality without admin privileges",customer.getId()));
			sendError(request, response, new InfoBean("Error de autorizacion", "Para realizar esa accion debe de loguearse en el sistema con privilegios de administrador"));
			customer = null;
		}
		return customer;
	}
}
