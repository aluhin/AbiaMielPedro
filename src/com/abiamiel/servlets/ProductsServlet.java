package com.abiamiel.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.abiamiel.model.Customer;
import com.abiamiel.model.HibernateUtil;
import com.abiamiel.model.Product;

public class ProductsServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Customer customer = loggedCustomer(request, response);
		if (customer == null)
			return;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		List<Product> products = null;
		boolean isConnectionPerformed = false;
		int tries = 0;
		
		do {
			try {
				tries++;
				session.beginTransaction();
		
				products = (List<Product>) session.createCriteria(Product.class).list();
				
				isConnectionPerformed = true;
			} catch (HibernateException e) {
				// TODO: this is wrong: createQuery(), uniqueResult() and commit() may also throw HibernateException and we cannot retry --> create specific retry catch for .beginTransaction()
				System.out.println("Retrying creating connection: " + e + " TRY: " + tries);
			}
		} while (!isConnectionPerformed);
		
		session.getTransaction().commit();
		
		request.setAttribute("products", products);
		RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_NEW_ORDER);
		dispatcher.forward(request, response);
	}
}
