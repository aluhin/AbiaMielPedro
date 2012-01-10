package com.abiamiel.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.abiamiel.model.Customer;
import com.abiamiel.model.InfoBean;
import com.abiamiel.model.HibernateUtil;

public class ModificationServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(ModificationServlet.class);
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Customer customer = loggedCustomer(request, response);
		if (customer == null)
			return;
		
		String password = request.getParameter("password");
		if (password == null || password == "") {
			sendError(request, response, new InfoBean("Error al modificar los datos", "El campo contrasenia es un campo obligatorio. Por favor vuelva a rellenar el formulario"));
			logger.info("Error when modifying an account: password field was null or empty");
		    return;
		}

		String repitedPassword = request.getParameter("repited_password");
		if (repitedPassword == null || repitedPassword == "") {
			sendError(request, response, new InfoBean("Error al modificar los datos", "El campo repeticion de contrasenia es un campo obligatorio. Por favor vuelva a rellenar el formulario"));
			logger.info("Error when modifying an account: repited password field was null or empty");
			return;
		}
		
		if (!password.equals(repitedPassword)) {
			sendError(request, response, new InfoBean("Error al modificar los datos", "El campo contrasenia debe de coincidir con el campo repeticion de contrasenia. Por favor vuelva a rellenar el formulario"));
			logger.info(String.format("Error when modifying an account: password(%s) different from repited password(%s)",password, repitedPassword));
			return;
		}
				
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String address = request.getParameter("address");
		String telephone = request.getParameter("telephone");
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!HibernateUtil.startTransaction(session)) {
			sendTooManyUserConectionsError(request, response);
		    logger.warn("Too many connections error when trying to create a transaction for modifying and account");
		}

		Customer modifiedCustomer = (Customer)session.get(Customer.class, customer.getId());
		
		// TODO: fix this hack!
		if (password.length() != 44)
			modifiedCustomer.setPassword(Utils.calculateHash(password));
		modifiedCustomer.setFirstName(firstName);
		modifiedCustomer.setLastName(lastName);
		modifiedCustomer.setAddress(address);
		modifiedCustomer.setTelephone(telephone);
		
		session.save(modifiedCustomer);
		session.getTransaction().commit();
		
		HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute("customer", modifiedCustomer);
		
		logger.info("Customer modified: " + modifiedCustomer);
		
		sendError(request, response, new InfoBean("Datos modificados!", ""));
	}	
}
