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


public class RegistrationServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String nick = request.getParameter("nick");
		if (nick == null || nick == "") {
			sendError(request, response, new InfoBean("Error al darse de alta", "El nombre de usuario es un campo obligatorio. Por favor vuelva a rellenar el formulario"));
		    return;
		}
		String password = request.getParameter("password");
		if (password == null || password == "") {
			sendError(request, response, new InfoBean("Error al darse de alta", "El campo contrasenia es un campo obligatorio. Por favor vuelva a rellenar el formulario"));
		    return;
		}
		String repitedPassword = request.getParameter("repited_password");
		if (password == null || password == "") {
			sendError(request, response, new InfoBean("Error al darse de alta", "El campo repeticion de contrasenia es un campo obligatorio. Por favor vuelva a rellenar el formulario"));
			return;
		}
		
		if (!password.equals(repitedPassword)) {
			sendError(request, response, new InfoBean("Error al darse de alta", "El campo contrasenia debe de coincidir con el campo repeticion de contrasenia. Por favor vuelva a rellenar el formulario"));
			return;
		}
		String email = request.getParameter("email");
		if (password == null || password == "") {
			sendError(request, response, new InfoBean("Error al darse de alta", "El campo email es un campo obligatorio. Por favor vuelva a rellenar el formulario"));
		    return;
		}
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String address = request.getParameter("address");
		String telephone = request.getParameter("telephone");
		
		if (!availableNick(nick)) {
			sendError(request, response, new InfoBean("Error al darse de alta", "El nombre de usuario " + nick + " ya existe en nuestra base de datos. Por favor, elija otro diferente volviendo a rellenar el formulario"));
		    return;
		}
		
		if (!availableEmail(email)) {
			sendError(request, response, new InfoBean("Error al darse de alta", "El email " + email + " ya existe en nuestra base de datos. Por favor, elija otro diferente volviendo a rellenar el formulario"));
		    return;
		}
		
		Customer newCustomer = new Customer();
		newCustomer.setNick(nick);
		newCustomer.setPassword(Utils.calculateHash(password));
		newCustomer.setEmail(email);
		newCustomer.setFirstName(firstName);
		newCustomer.setLastName(lastName);
		newCustomer.setAddress(address);
		newCustomer.setTelephone(telephone);
		newCustomer.setActive(false);
		newCustomer.setAdmin(false);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		boolean isConnectionPerformed = false;
		int tries = 0;
		
		do {
			try {
				tries++;
				session.beginTransaction();
				session.save(newCustomer);
				session.getTransaction().commit();
				isConnectionPerformed = true;
			} catch (HibernateException e) {
				// TODO: this is wrong: createQuery(), uniqueResult() and commit() may also throw HibernateException and we cannot retry --> create specific retry catch for .beginTransaction()
				System.out.println("Retrying creating connection: " + e + " TRY: " + tries);
			}
		} while (!isConnectionPerformed);
		
		String emailContent = "Haz click en el siguiente link para activar tu cuenta: \n\n http://" + Constants.HOSTNAME + ":" + Constants.PORT + Constants.CONTEXT_ROOT + Constants.SERVLET_ACTIVATION + "?id=" + newCustomer.getId();
		
		//Utils.sendEmail(Constants.REGISTRATION_FROM_EMAIL, Constants.REGISTRATION_FROM_PASSWORD, new String[]{ email }, "Abia Miel Pedro - Proceso de activacion de cuenta", emailContent);
		System.out.println(emailContent);
		
		sendError(request, response, new InfoBean("Registro Completado!", "Para completar el proceso de registro, necesita activar su cuenta. Para ello, hemos enviado un email a la cuenta de correo electronico que nos ha facilitado. Para activar su cuenta, abra su correo y haga click en el enlace que contiene"));
	}
	
	private boolean availableNick(String nick) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		Customer customer = null;
		boolean isConnectionPerformed = false;
		int tries = 0;
		
		do {
			try {
				tries++;
				session.beginTransaction();
		
				customer = (Customer) session
						.createQuery("from Customer where nick = ?")
						.setString(0, nick)
						.uniqueResult();
				
				session.getTransaction().commit();
				isConnectionPerformed = true;
			} catch (HibernateException e) {
				// TODO: this is wrong: createQuery(), uniqueResult() and commit() may also throw HibernateException and we cannot retry --> create specific retry catch for .beginTransaction()
				System.out.println("Retrying creating connection: " + e + " TRY: " + tries);
			}
		} while (!isConnectionPerformed);
		
		return customer == null;
	}

	private boolean availableEmail(String email) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		Customer customer = null;
		boolean isConnectionPerformed = false;
		int tries = 0;
		
		do {
			try {
				tries++;
				session.beginTransaction();
		
				customer = (Customer) session
						.createQuery("from Customer where email = ?")
						.setString(0, email)
						.uniqueResult();
				
				session.getTransaction().commit();
				isConnectionPerformed = true;
			} catch (HibernateException e) {
				// TODO: this is wrong: createQuery(), uniqueResult() and commit() may also throw HibernateException and we cannot retry --> create specific retry catch for .beginTransaction()
				System.out.println("Retrying creating connection: " + e + " TRY: " + tries);
			}
		} while (!isConnectionPerformed);
		
		return customer == null;
	}
}
