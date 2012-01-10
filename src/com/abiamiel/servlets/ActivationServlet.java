package com.abiamiel.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.abiamiel.model.Customer;
import com.abiamiel.model.InfoBean;
import com.abiamiel.model.HibernateUtil;

public class ActivationServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(ActivationServlet.class);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String idString = request.getParameter("id");
		
		if (idString == null || idString == "") {
			sendError(request, response, new InfoBean("Error en el proceso de activacion", "Ha ocurrido un error en el proceso de activacion. Por favor, contacte a alberto.martinez.gar@gmail.com para completar su proceso de activacion"));
		    logger.warn("Error in activation process. Received idString parameter null or empty");
			return;
		}
		
		int id = Integer.parseInt(idString);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!HibernateUtil.startTransaction(session)) {
			sendTooManyUserConectionsError(request, response);
		    logger.warn("Too many connections when trying to create a transaction");
			return;
		}

		Customer customer = (Customer) session.get(Customer.class, id);
		
		if (customer == null) {
			sendError(request, response, new InfoBean("Error en el proceso de activacion", "El usuario no existe. Por favor, contacte a " + Constants.SUPPORT_EMAIL + "para completar su proceso de activacion"));
		    logger.warn("Error in activation process. There is no customer with id " + id);
		}
		else if (customer.isActive()) {
			sendError(request, response, new InfoBean("Error en el proceso de activacion", "Cuenta ya estaba activada. Puede loguearse usando el enlace de la derecha \"Mi cuenta\""));
		    logger.info("Error in activation process. Account with id "+ customer.getId() + " was already activated");
		}
		else {
			customer.setActive(true);
			session.save(customer);
			sendError(request, response, new InfoBean("Activacion completa!", "Cuenta ya estaba activada. Puede loguearse en su cuenta usando el enlace de la derecha \"Mi cuenta\""));
		    logger.info(String.format("Customer %d activated him account", customer.getId()));
		}
		
		session.getTransaction().commit();
	}
}
