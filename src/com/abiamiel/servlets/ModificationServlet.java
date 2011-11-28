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


public class ModificationServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Customer customer = loggedCustomer(request, response);
		if (customer == null)
			return;
		
		String password = request.getParameter("password");
		if (password == null || password == "") {
			sendError(request, response, new InfoBean("Error al modificar los datos", "El campo contrasenia es un campo obligatorio. Por favor vuelva a rellenar el formulario"));
		    return;
		}

		String repitedPassword = request.getParameter("repited_password");
		if (password == null || password == "") {
			sendError(request, response, new InfoBean("Error al modificar los datos", "El campo repeticion de contrasenia es un campo obligatorio. Por favor vuelva a rellenar el formulario"));
			return;
		}
		
		if (!password.equals(repitedPassword)) {
			sendError(request, response, new InfoBean("Error al modificar los datos", "El campo contrasenia debe de coincidir con el campo repeticion de contrasenia. Por favor vuelva a rellenar el formulario"));
			return;
		}
				
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String address = request.getParameter("address");
		String telephone = request.getParameter("telephone");
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		boolean isConnectionPerformed = false;
		int tries = 0;
		
		do {
			try {
				tries++;
				session.beginTransaction();

				Customer modifiedCustomer = (Customer)session.get(Customer.class, customer.getId());
				
				if (password.length() != 44)
					modifiedCustomer.setPassword(Utils.calculateHash(password));
				modifiedCustomer.setFirstName(firstName);
				modifiedCustomer.setLastName(lastName);
				modifiedCustomer.setAddress(address);
				modifiedCustomer.setTelephone(telephone);
				
				session.save(modifiedCustomer);
				session.getTransaction().commit();
				isConnectionPerformed = true;
			} catch (HibernateException e) {
				// TODO: this is wrong: createQuery(), uniqueResult() and commit() may also throw HibernateException and we cannot retry --> create specific retry catch for .beginTransaction()
				System.out.println("Retrying creating connection: " + e + " TRY: " + tries);
			}
		} while (!isConnectionPerformed);
	
		sendError(request, response, new InfoBean("Datos modificados!", ""));
	}	
}
