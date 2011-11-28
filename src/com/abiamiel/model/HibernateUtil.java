package com.abiamiel.model;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.abiamiel.servlets.Utils;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void main(String[] args) {
		{
			// Customers
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Customer customer1 = new Customer();
			customer1.setNick("aluhin");
			customer1.setFirstName("Alberta");
			customer1.setLastName("Martinez");
			customer1.setAddress("dasd adsa");
			customer1.setEmail("alberto.martinez.gar@gmail.com");
			customer1.setActive(true);
			customer1.setPassword(Utils.calculateHash("temp123"));
			customer1.setTelephone("93232");

			session.save(customer1);

			Customer customer2 = new Customer();
			customer2.setNick("aluhin2");
			customer2.setFirstName("Alberto");
			customer2.setLastName("Martinez2");
			customer2.setAddress("dasd adsa2");
			customer2.setEmail("alberto.martinez.gar2@gmail.com");
			customer2.setActive(true);
			customer2.setPassword(Utils.calculateHash("temp123"));
			customer2.setTelephone("932322");
			customer2.setAdmin(true);

			session.save(customer2);

			Customer customer3 = new Customer();
			customer3.setNick("aluhin3");
			customer3.setFirstName("Alberti");
			customer3.setLastName("Martinez2");
			customer3.setAddress("dasd adsa2");
			customer3.setEmail("alberto.martinez.gar3@gmail.com");
			customer3.setActive(true);
			customer3.setPassword(Utils.calculateHash("temp123"));
			customer3.setTelephone("932322");
			customer3.setAdmin(true);

			session.save(customer3);

			session.getTransaction().commit();

			// Products
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Product product1 = new Product();
			product1.setName("Miel Brezo 1kg");
			product1.setDescription("Miel oscura bla bla bla");
			product1.setPrice(6.50f);

			session.save(product1);

			Product product2 = new Product();
			product2.setName("Miel Leguminosas 1kg");
			product2.setDescription("Miel clara bla bla bla");
			product2.setPrice(10.50f);

			session.save(product2);

			Product product3 = new Product();
			product3.setName("Miel Romero Medio Kilo");
			product3.setDescription("bla bla bla");
			product3.setPrice(20.50f);

			session.save(product3);

			session.getTransaction().commit();

			// Order 1
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			customer2 = (Customer) session.get(Customer.class, 2);
			product1 = (Product) session.get(Product.class, 2);
			product2 = (Product) session.get(Product.class, 3);

			MyOrder order1 = new MyOrder();
			order1.setTitle("Pedido Burgos Noviembre 2011");
			order1.setOrderedAt(new Date());
			order1.setComments("Hola Pedro, me gustaria ordenar este pedido para ir a recogerlo al mercado de burgos el dia 20 de Noviembre.\nUn saludo,\nJulian");
			order1.setCustomer(customer2);
			session.save(order1);

			OrderProduct order1Product1 = new OrderProduct();
			order1Product1.setQuantity(3);
			order1Product1.setProduct(product1);
			order1Product1.setOrder(order1);
			session.save(order1Product1);

			OrderProduct order1Product2 = new OrderProduct();
			order1Product2.setProduct(product2);
			order1Product2.setQuantity(2);
			order1Product2.setOrder(order1);
			session.save(order1Product2);

			session.getTransaction().commit();

			// Order 2
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			customer1 = (Customer) session.get(Customer.class, 1);
			product1 = (Product) session.get(Product.class, 1);
			product3 = (Product) session.get(Product.class, 3);

			MyOrder order2 = new MyOrder();
			order2.setTitle("Cincuenta tarros de leguminosa para el viernes");
			order2.setOrderedAt(new Date());
			order2.setComments("Queria como siempre 50 tarros de leguminosas para el viernes de esta semana,\nHermenegildo");
			order2.setCustomer(customer1);
			session.save(order2);

			OrderProduct order2Product1 = new OrderProduct();
			order2Product1.setProduct(product1);
			order2Product1.setQuantity(50);
			order2Product1.setOrder(order2);
			session.save(order2Product1);

			OrderProduct order2Product3 = new OrderProduct();
			order2Product3.setProduct(product3);
			order2Product3.setQuantity(500);
			order2Product3.setOrder(order2);
			session.save(order2Product3);

			session.getTransaction().commit();

			// Order 3
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			customer2 = (Customer) session.get(Customer.class, 2);

			MyOrder order3 = new MyOrder();
			order3.setTitle("a panales de cera para empezar");
			order3.setOrderedAt(new Date());
			order3.setComments("Hola, soy Pepe, te he localizado por la pagina web y me gustaria preguntarte si se podrian encargar panales de cera sueltos. A cuanto saldria cada uno? Un saludo");
			order3.setCustomer(customer2);
			session.save(order3);

			// Order 4
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			customer3 = (Customer) session.get(Customer.class, 3);

			MyOrder order4 = new MyOrder();
			order4.setTitle("cuarto pedido");
			order4.setOrderedAt(new Date());
			order4.setComments("Hola, soy Pepe, te he localizado por la pagina web y me gustaria preguntarte si se podrian encargar panales de cera sueltos. A cuanto saldria cada uno? Un saludo");
			order4.setCustomer(customer3);
			session.save(order4);

			session.getTransaction().commit();
		}
		{
			// Remove Customer 1
			//Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			//session.beginTransaction();
			
			//Customer customer1 = (Customer) session.get(Customer.class, 1);
			//session.delete(customer1);
			
			// Remove order2_product1 
			//OrderProduct orderProduct = (OrderProduct) session.get(OrderProduct.class, 1);
			//session.delete(orderProduct);
			
			// Remove order1 
			//MyOrder order = (MyOrder) session.get(MyOrder.class, 1);
			//session.delete(order);

			//session.getTransaction().commit();
		}
	}
}