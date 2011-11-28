package com.abiamiel.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.abiamiel.model.Customer;
import com.abiamiel.model.InfoBean;
import com.abiamiel.model.HibernateUtil;

public class LoginServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    
		HttpSession httpSession = request.getSession(false);
		Customer customer = null;
		if (httpSession != null)
			customer = (Customer)httpSession.getAttribute("customer");
		
		if (httpSession == null || customer == null || customer.getNick() == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_LOGIN);
		    dispatcher.forward(request, response);
		}
		else {
			if (request.getParameter("end") != null) {
				httpSession.invalidate();
				sendError(request, response, new InfoBean("Session finalizada", "Su session ha finalizado correctamente"));
			}
			else {
				RequestDispatcher dispatcher = null;
				if (customer.isAdmin())
					dispatcher = request.getRequestDispatcher(Constants.PAGE_ADMIN);
				else
					dispatcher = request.getRequestDispatcher(Constants.PAGE_ACCOUNT);
			    dispatcher.forward(request, response);
			}
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		
		if (nick != null && password != null) {
			
			HttpSession httpSession = request.getSession(false);
			
			if (httpSession != null)
				httpSession.invalidate();
			
			Customer customer = validateCustomer(nick, password);
			
			if (customer == null)
				sendError(request, response, new InfoBean("Error al loguear", "El nombre de usuario no existe o la contrasenia no coincide con la almacenada. Por favor, ingrese sus datos de nuevo en haciendo click en el enlace de la derecha Registrarse"));
			else if (!customer.isActive())
				sendError(request, response, new InfoBean("Error al loguear", "Su cuenta no esta activada. Por favor, primero active su cuenta mediante el link que le enviamos a la cuenta de correo electronico que nos proporciono y despues vuelva a introducir sus datos para loguearse"));
			else {
				httpSession = request.getSession(true);
				httpSession.setAttribute("customer", customer);
				
				RequestDispatcher dispatcher = null;
				if (customer.isAdmin())
					dispatcher = request.getRequestDispatcher(Constants.PAGE_ADMIN);
				else
					dispatcher = request.getRequestDispatcher(Constants.PAGE_ACCOUNT);
			    
				dispatcher.forward(request, response);
			}
		}
		else
			sendError(request, response, new InfoBean("Error al loguear", "No se recibieron los parametros correctamente. Por favor, vuelva a loguearse en el enlace de la derecha Registrarse"));
	}
	
	static private Customer validateCustomer(String nick, String password) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		Customer customer = null;
		boolean isConnectionPerformed = false;
		int tries = 0;
		
		do {
			try {
				tries++;
				session.beginTransaction();
				
				//TODO: change this query to use QueryOver
				customer = (Customer) session
						.createQuery("from Customer where nick = ? and password = ?")
						.setString(0, nick).setString(1, Utils.calculateHash(password))
						.uniqueResult();
				
				session.getTransaction().commit();
				isConnectionPerformed = true;
			} catch (HibernateException e) {
				// TODO: this is wrong: createQuery(), uniqueResult() and commit() may also throw HibernateException and we cannot retry --> create specific retry catch for .beginTransaction()
				System.out.println("Retrying creating connection: " + e + " TRY: " + tries);
			}
		} while (!isConnectionPerformed);
		
		return customer;
	}
}
