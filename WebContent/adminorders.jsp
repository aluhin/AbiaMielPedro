<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page import="com.abiamiel.model.MyOrder" %>
<jsp:useBean id="customer" class="com.abiamiel.model.Customer" scope="session" />
<jsp:useBean id="orders" class="java.util.ArrayList" scope="request" />
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
				<h1 class="title">Pedidos</h1>
				<div class="content">
					<p>La siguiente lista muestra todos los pedidos del sistema. Puede realizar filtros y/u ordenarlos usando diferentes criterios</p>
				</div>

				<div>
					<table border="1">
						<tr>
							<th><a href="orders?sort=title&admin">Titulo</a></th>
							<th><a href="orders?sort=ordered_at&admin">Fecha</a></th>
							<th><a href="orders?sort=customer&admin">Cliente</a></th>
						</tr>
						
						<% for (Object myOrderObject : orders) { 
							MyOrder myOrder = (MyOrder) myOrderObject;
						%>
						<tr>
							<td><a href="order?id=<%= myOrder.getId() %>&admin"><%= myOrder.getTitle() %></a></td>
							<td><%= myOrder.getOrderedAt() %></td>
							<td><%= myOrder.getCustomer().getFullName() %></td>
						</tr>
						<% } %>
						
					</table>
				</div>

				<div id="links">
					<ul>
						<li><a href="login">Volver al menu Administracion</a></li>
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
					<li><a href="login" accesskey="A"><b>A</b>dministracion</a></li>
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
