package com.abiamiel.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.abiamiel.model.Customer;
import com.abiamiel.model.InfoBean;
import com.abiamiel.model.HibernateUtil;
import com.abiamiel.model.MyOrder;
import com.abiamiel.model.OrderProduct;
import com.abiamiel.model.Product;


public class NewOrderServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Customer customer = loggedCustomer(request, response);
		if (customer == null)
			return;

		String title = request.getParameter("title");
		if (title == null || title == "") {
			sendError(request, response, new InfoBean("Error al registrar el pedido", "No se encontro el titulo o estaba vacio"));
		    return;
		}

		String comments = request.getParameter("comments");
		if (comments == null) {
			sendError(request, response, new InfoBean("Error al registrar el pedido", "No se encontraron los comentarios"));
		    return;
		}
		
		HashMap<Integer, Integer> list = new HashMap<Integer,Integer>();
		for (int i = 0; i < Constants.NUMBER_MAX_ORDERS; i++) {
			String product = request.getParameter("product" + i);
			String quantity = request.getParameter("quantity" + i);
			if (product == null || quantity == null)
				continue;
			if (product.equals("none") || quantity.equals("0"))
				continue;
			//TODO: Make the parsing safe!
			list.put(Integer.parseInt(product), Integer.parseInt(quantity));
		}
		
		if (list.isEmpty() && comments.equals("")) {
			sendError(request, response, new InfoBean("Error al registrar el pedido", "Tanto la lista de productos como los comentarios estan vacios"));
		    return;
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		MyOrder newOrder = new MyOrder();
		newOrder.setTitle(title);
		newOrder.setComments(comments);
		newOrder.setCustomer(customer);
		newOrder.setOrderedAt(new Date());
		session.save(newOrder);
		
		for (Entry<Integer, Integer> entry : list.entrySet()) {

			Product product = (Product) session.get(Product.class, entry.getKey());
			if (product == null)
				continue;
			
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setProduct(product);
			orderProduct.setQuantity(entry.getValue());
			orderProduct.setOrder(newOrder);
			session.save(orderProduct);
		}
		
		session.getTransaction().commit();
		
		sendError(request, response, new InfoBean("Pedido Registrado!", ""));
	}	
}
