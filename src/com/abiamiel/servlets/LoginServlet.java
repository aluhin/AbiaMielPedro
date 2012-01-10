package com.abiamiel.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.abiamiel.model.Customer;
import com.abiamiel.model.InfoBean;
import com.abiamiel.model.HibernateUtil;

public class LoginServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(LoginServlet.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    
		HttpSession httpSession = request.getSession(false);
		Customer customer = null;
		if (httpSession != null)
			customer = (Customer)httpSession.getAttribute("customer");
		
		if (httpSession == null || customer == null || customer.getNick() == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_LOGIN);
		    dispatcher.forward(request, response);
		    logger.info("No session when login, so user redirected to login page");
		}
		else {
			if (request.getParameter("end") != null) {
				logger.info(String.format("User with id %s finishing session", customer.getId()));
				httpSession.invalidate();
				sendError(request, response, new InfoBean("Sesion finalizada", String.format("Su sesion como usuario %s ha finalizado correctamente",customer.getNick())));
			}
			else {
				RequestDispatcher dispatcher = null;
				if (customer.isAdmin()) {
					dispatcher = request.getRequestDispatcher(Constants.PAGE_ADMIN);
					logger.info(String.format("Redirecting user %s to account page", customer.getId()));
				}
				else {
					dispatcher = request.getRequestDispatcher(Constants.PAGE_ACCOUNT);
					logger.info(String.format("Redirecting user %s to admin page", customer.getId()));					
				}
			    dispatcher.forward(request, response);
			}
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		
		if (nick == null || password == null) {
			sendError(request, response, new InfoBean("Error al loguear", "No se recibieron los parametros correctamente. Por favor, vuelva a loguearse en el enlace de la derecha Registrarse"));
		    logger.warn("The parameters nick and name where at least one of them null");
			return;
		}
		HttpSession httpSession = request.getSession(false);
		
		if (httpSession != null)
			httpSession.invalidate();
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!HibernateUtil.startTransaction(session)) {
			sendTooManyUserConectionsError(request, response);
		    logger.warn("Too many connections error when trying to create a transaction for checking a nick/password");
		}

		Customer customer = (Customer) session.createCriteria(Customer.class)
				.add(Restrictions.eq("nick",nick))
				.add(Restrictions.eq("password", Utils.calculateHash(password)))
				.uniqueResult();
		
		session.getTransaction().commit();
		
		if (customer == null) {
			sendError(request, response, new InfoBean("Error al loguear", "El nombre de usuario no existe o la contrasenia no coincide con la almacenada. Por favor, ingrese sus datos de nuevo en haciendo click en el enlace de la derecha Registrarse"));
		    logger.warn(String.format("User unable to log in: wrong nick(%s) or password(%s)", nick, password));
		}
		else if (!customer.isActive()) {
			sendError(request, response, new InfoBean("Error al loguear", "Su cuenta no esta activada. Por favor, primero active su cuenta mediante el link que le enviamos a la cuenta de correo electronico que nos proporciono y despues vuelva a introducir sus datos para loguearse"));
		    logger.warn(String.format("User %d unable to log in: account is not still activated", customer.getId()));
		}
		else {
			httpSession = request.getSession(true);
			httpSession.setAttribute("customer", customer);
			
			//RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.SERVLET_LOGIN);
			//dispatcher.forward(request, response);
			doGet(request, response);
		}
	}
}
