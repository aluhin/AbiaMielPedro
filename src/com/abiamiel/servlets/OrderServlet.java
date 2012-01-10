package com.abiamiel.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
	static Logger logger = Logger.getLogger(OrderServlet.class);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		boolean requiresAdmin = request.getParameter("admin") != null; 
		
		Customer customer = loggedCustomer(request, response, requiresAdmin);
		if (customer == null)
			return;
		
		String stringOrderId = request.getParameter("id");
		if (stringOrderId == null || stringOrderId == "") {
			sendError(request, response, new InfoBean("Error al obtener pedido", "No se recibio el numero de pedido a obtener"));
			logger.debug("id null or empty");
		    return;
		}
		int orderId = Integer.parseInt(stringOrderId);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		if (!HibernateUtil.startTransaction(session)) {
			sendTooManyUserConectionsError(request, response);
		    logger.warn("Too many connections error when trying to create a transaction for fetching an order info");
		}
		
		
		Criteria criteria = session.createCriteria(MyOrder.class).add(Restrictions.eq("id", orderId));
		
		if (!requiresAdmin)
			criteria.add(Restrictions.eq("customer", customer));
			
		MyOrder order = (MyOrder)criteria.uniqueResult();
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
			logger.debug(String.format("The id(%d) of the order received does not correpond to any order in the db", orderId));
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
