<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page import="com.abiamiel.model.OrderProduct" %>
<jsp:useBean id="customer" class="com.abiamiel.model.Customer" scope="session" />
<jsp:useBean id="order" class="com.abiamiel.model.MyOrder" scope="request" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE"/>
<title>Abia Miel Pedro</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="default.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="logo">
		<h1>
			<a href="#">Abia Miel Pedro</a>
		</h1>
		<h2>
			<a href="#">Pedro Garcia Plaza - Abia de las Torres - Palencia</a>
		</h2>
	</div>
	<div id="page">
		<div id="content">
			<div id="welcome">
				<h1 class="title"><jsp:getProperty name = "order" property = "title" /></h1>
				
				<div class="content">
				<ul>
				<li> Fecha del pedido: <%= order.getFormatedOrderedAt() %> </li>
				
				<% if (!order.getOrdersProducts().isEmpty()) { %>
				
				<li>
					<p>Productos que conforman el pedido:</p>
					<table border="1">
						<tr>
							<th>Producto</th>
							<th>Cantidad</th>
						</tr>
						
						<% for (Object orderProductObject : order.getOrdersProducts()) { 
							OrderProduct orderProduct = (OrderProduct) orderProductObject;
						%>
						<tr>
							<td><%= orderProduct.getProduct().getName() %></td>
							<td><%= orderProduct.getQuantity() %></td>
						</tr>
						<% } %>
						
					</table>
				</li>
				<% } %>
				<li> Comentarios del pedido: <jsp:getProperty name = "order" property = "comments" /></li>
				</ul>
				</div>
				<div id="links">
					<ul>
						<li><a href="orders">Manejar mis pedidos</a></li>
						<li><a href="neworder">Realizar un pedido nuevo</a></li>
						<li><a href="login">Volver al menu Mi Cuenta</a></li>
					</ul>
				</div>

			</div>
		</div>
		<!-- end #content -->
		<div id="sidebar">
			<div id="menu">
				<ul>
					<li class="first"><a href="index.html" accesskey="I" title=""><b>I</b>nicio</a></li>
					<li><a href="products.html" accesskey="P" title=""><b>P</b>roductos</a></li>
					<li><a href="sell.html" accesskey="V" title=""><b>V</b>enta</a></li>
					<li><a href="who.html" accesskey="Q" title=""><b>Q</b>uienes somos</a></li>
					<li><a href="where.html" accesskey="D" title=""><b>D</b>onde estamos</a></li>
				</ul>
			</div>
			<div id="links">
				<h2>Personalizacion:</h2>
				<ul>
					<li><a href="login" accesskey="M"><b>M</b>i cuenta</a></li>
					<li><a href="login?end=true" accesskey="F"><b>F</b>inalizar sesion</a></li>
				</ul>
			</div>
		</div>
		<!-- end #sidebar -->
		<div id="extra" style="clear: both; height: 1px"></div>
	</div>
	<!-- end #page -->
	<div id="footer">
		<div class="fcenter">
			<p>Copyright © 2010-2011 Abia Miel Pedro</p>
		</div>
	</div>
</body>
</html>
