package com.abiamiel.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.abiamiel.model.Customer;
import com.abiamiel.model.InfoBean;
import com.abiamiel.model.HibernateUtil;

public class ActivationServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String idString = request.getParameter("id");
		
		if (idString == null || idString == "") {
			sendError(request, response, new InfoBean("Error en el proceso de activacion", "Ha ocurrido un error en el proceso de activacion. Por favor, contacte a alberto.martinez.gar@gmail.com para completar su proceso de activacion"));
		    return;
		}
		int id = Integer.parseInt(idString);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		Customer customer = null;
		boolean isConnectionPerformed = false;
		int tries = 0;
		
		do {
			try {
				tries++;
				session.beginTransaction();
		
				customer = (Customer) session.get(Customer.class, id);
				
				isConnectionPerformed = true;
			} catch (HibernateException e) {
				// TODO: this is wrong: createQuery(), uniqueResult() and commit() may also throw HibernateException and we cannot retry --> create specific retry catch for .beginTransaction()
				System.out.println("Retrying creating connection: " + e + " TRY: " + tries);
			}
		} while (!isConnectionPerformed);
		
		if (customer == null)
			sendError(request, response, new InfoBean("Error en el proceso de activacion", "El usuario no existe. Por favor, contacte a alberto.martinez.gar@gmail.com para completar su proceso de activacion"));
		else if (customer.isActive())
			sendError(request, response, new InfoBean("Error en el proceso de activacion", "Cuenta ya estaba activada. Puede loguearse usando el enlace de la derecha \"Mi cuenta\""));
		else {
			customer.setActive(true);
			session.save(customer);
			sendError(request, response, new InfoBean("Activacion completa!", "Cuenta ya estaba activada. Puede loguearse en su cuenta usando el enlace de la derecha \"Mi cuenta\""));
		}
		
		session.getTransaction().commit();
	}
}
