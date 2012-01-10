package com.abiamiel.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {
	public static final String PAGE_LOGIN = "login.jsp";
	public static final String PAGE_ACCOUNT = "account.jsp";
	public static final String PAGE_ORDERS = "orders.jsp";
	public static final String PAGE_ADMIN_ORDERS = "adminorders.jsp";
	public static final String PAGE_ORDER = "order.jsp";
	public static final String PAGE_ADMIN_ORDER = "adminorder.jsp";
	public static final String PAGE_NEW_ORDER = "neworder.jsp";
	public static final String PAGE_ADMIN = "admin.jsp";
	public static final String PAGE_ERROR = "error.jsp";

	public static final String SERVLET_ACTIVATION = "activation";
	public static final String SERVLET_LOGIN = "login";
		
	public static final String HOSTNAME;
	public static final String PORT;
	public static final String CONTEXT_ROOT;

	public static final String REGISTRATION_FROM_EMAIL;
	public static final String REGISTRATION_FROM_PASSWORD;

	public static final String SUPPORT_EMAIL;

	public static final int NUMBER_MAX_ORDERS;	

	static {
		Properties properties = new Properties();
		try {
			InputStream is = Constants.class.getResourceAsStream("/app.properties");
		    properties.load(is);
		} catch (IOException e) {
			//TODO: log this shit!
		}
		
		HOSTNAME = properties.getProperty("generic.hostname");
		PORT = properties.getProperty("generic.port");
		CONTEXT_ROOT = properties.getProperty("generic.contextroot");

		REGISTRATION_FROM_EMAIL = properties.getProperty("registration.from.email");
		REGISTRATION_FROM_PASSWORD = properties.getProperty("registration.from.password");
		
		SUPPORT_EMAIL = properties.getProperty("support.email");
		NUMBER_MAX_ORDERS = Integer.parseInt(properties.getProperty("orders.maxproductsperorder"));
	}
}
