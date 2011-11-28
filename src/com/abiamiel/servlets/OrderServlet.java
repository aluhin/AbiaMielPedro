package com.abiamiel.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.abiamiel.model.Customer;
import com.abiamiel.model.HibernateUtil;
import com.abiamiel.model.InfoBean;
import com.abiamiel.model.MyOrder;
import com.abiamiel.model.OrderProduct;
import com.abiamiel.model.Product;

public class OrderServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		boolean requiresAdmin = request.getParameter("admin") != null; 
		
		Customer customer = loggedCustomer(request, response, requiresAdmin);
		if (customer == null)
			return;
		
		String stringOrderId = request.getParameter("id");
		if (stringOrderId == null || stringOrderId == "") {
			sendError(request, response, new InfoBean("Error al obtener pedido", "No se recibio el numero de pedido a obtener"));
		    return;
		}
		int orderId = Integer.parseInt(stringOrderId);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		MyOrder order = null;
		boolean isConnectionPerformed = false;
		int tries = 0;
		
		do {
			try {
				tries++;
				session.beginTransaction();
		
				Criteria criteria = session.createCriteria(MyOrder.class).add(Restrictions.eq("id", orderId));
				
				if (!requiresAdmin)
					criteria.add(Restrictions.eq("customer", customer));
					
				order = (MyOrder)criteria.uniqueResult();
				
				isConnectionPerformed = true;
			} catch (HibernateException e) {
				// TODO: this is wrong: createQuery(), uniqueResult() and commit() may also throw HibernateException and we cannot retry --> create specific retry catch for .beginTransaction()
				System.out.println("Retrying creating connection: " + e + " TRY: " + tries);
			}
		} while (!isConnectionPerformed);
		
		MyOrder copyOrder = null;
		
		if (order != null) {
			copyOrder = new MyOrder(order.getId());
			copyOrder.setTitle(order.getTitle());
			copyOrder.setComments(order.getComments());
			copyOrder.setOrderedAt(order.getOrderedAt());
			for (OrderProduct orderProduct : order.getOrdersProducts()) {
				OrderProduct copyOrderProduct = new OrderProduct();
				copyOrderProduct.setQuantity(orderProduct.getQuantity());
				Product copyProduct = new Product(orderProduct.getProduct().getId());
				copyProduct.setName(orderProduct.getProduct().getName());
				copyOrderProduct.setProduct(copyProduct);
				copyOrder.getOrdersProducts().add(copyOrderProduct);
			}
		}
		
		session.getTransaction().commit();
		
		if (order == null) {
			sendError(request, response, new InfoBean("Error al obtener pedido", "El numero de pedido " + orderId + " no existe en nuestra base de datos"));
		    return;
		}
	
		request.setAttribute("order", copyOrder);
		RequestDispatcher dispatcher = null;
		if (requiresAdmin)
			dispatcher = request.getRequestDispatcher(Constants.PAGE_ADMIN_ORDER);
		else
			dispatcher = request.getRequestDispatcher(Constants.PAGE_ORDER);
		dispatcher.forward(request, response);
	}
}
