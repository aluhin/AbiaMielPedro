package com.abiamiel.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.abiamiel.model.Customer;
import com.abiamiel.model.FilteringOptions;
import com.abiamiel.model.HibernateUtil;
import com.abiamiel.model.MyOrder;

public class OrdersServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(OrderServlet.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		boolean requiresAdmin = request.getParameter("admin") != null; 

		Customer customer = loggedCustomer(request, response, requiresAdmin);
		if (customer == null)
			return;
		
		HttpSession httpSession = request.getSession(false);
		FilteringOptions filteringOptions = (FilteringOptions)httpSession.getAttribute("filtering");
		if (filteringOptions == null)
			filteringOptions = new FilteringOptions();
		
		String sortParameter = request.getParameter("sort");
		if (sortParameter != null) {
			if (sortParameter.equals("ordered_at")) {
				filteringOptions.setOrderAtSort(filteringOptions.getOrderAtSort().negate());
				filteringOptions.setTitleSort(FilteringOptions.SortedOptions.NO_SORTED);
				filteringOptions.setCustomerSort(FilteringOptions.SortedOptions.NO_SORTED);
			}
			else if (sortParameter.equals("title")) {
				filteringOptions.setOrderAtSort(FilteringOptions.SortedOptions.NO_SORTED);
				filteringOptions.setTitleSort(filteringOptions.getTitleSort().negate());
				filteringOptions.setCustomerSort(FilteringOptions.SortedOptions.NO_SORTED);

			}
			else if (sortParameter.equals("customer")) {
				filteringOptions.setOrderAtSort(FilteringOptions.SortedOptions.NO_SORTED);
				filteringOptions.setTitleSort(FilteringOptions.SortedOptions.NO_SORTED);
				filteringOptions.setCustomerSort(filteringOptions.getCustomerSort().negate());
			}
		}
		
		httpSession.setAttribute("filtering", filteringOptions);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		List<MyOrder> orders = null;
		boolean isConnectionPerformed = false;
		int tries = 0;
		
		do {
			try {
				tries++;
				session.beginTransaction();
				
				Criteria criteria = session.createCriteria(MyOrder.class);
				
				if (filteringOptions.getOrderAtSort() == FilteringOptions.SortedOptions.ASC_SORTED)
					criteria.addOrder(Order.asc("orderedAt"));
				else if (filteringOptions.getOrderAtSort() == FilteringOptions.SortedOptions.DESC_SORTED)
					criteria.addOrder(Order.desc("orderedAt"));
				
				if (filteringOptions.getTitleSort() == FilteringOptions.SortedOptions.ASC_SORTED)
					criteria.addOrder(Order.asc("title").ignoreCase());
				else if (filteringOptions.getTitleSort() == FilteringOptions.SortedOptions.DESC_SORTED)
					criteria.addOrder(Order.desc("title").ignoreCase());
				
				if (requiresAdmin) {
					if (filteringOptions.getCustomerSort() == FilteringOptions.SortedOptions.ASC_SORTED)
						criteria.createCriteria("customer").addOrder(Order.asc("firstName").ignoreCase()); 
					else if (filteringOptions.getCustomerSort() == FilteringOptions.SortedOptions.DESC_SORTED)
						criteria.createCriteria("customer").addOrder(Order.desc("firstName").ignoreCase()); 
				}
				else
					criteria.add(Restrictions.eq("customer", customer));
				
				orders = (List<MyOrder>)criteria.list();
				
				for (MyOrder order : orders)
					order.getCustomer();
				
				isConnectionPerformed = true;
			} catch (HibernateException e) {
				// TODO: this is wrong: createQuery(), uniqueResult() and commit() may also throw HibernateException and we cannot retry --> create specific retry catch for .beginTransaction()
				System.out.println("Retrying creating connection: " + e + " TRY: " + tries);
			}
		} while (!isConnectionPerformed);
		
		session.getTransaction().commit();
		
		request.setAttribute("orders", orders);
		RequestDispatcher dispatcher = null;
		if (requiresAdmin)
			dispatcher = request.getRequestDispatcher(Constants.PAGE_ADMIN_ORDERS);
		else
			dispatcher = request.getRequestDispatcher(Constants.PAGE_ORDERS);
		dispatcher.forward(request, response);
	}
}
