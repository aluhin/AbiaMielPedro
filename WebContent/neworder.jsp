<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page import="com.abiamiel.model.Product" %>
<%@ page import="com.abiamiel.servlets.Constants" %>
<jsp:useBean id="products" class="java.util.ArrayList" scope="request" />
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
				<h1 class="title">Nuevo Pedido</h1>
				<div class="content">
					<p>A continuacion seleccione los productos y cantidades de ellos que conformaran su pedido. Al final puede introducir los comentarios que desee: </p>

					<form name="input" action="neworder" method="post">
						
						<label class="label" for="title">Titulo: </label>
						<input type="text" name= "title" required="required"/>
						
						<table border="0">
						<% for (int i = 0; i < Constants.NUMBER_MAX_ORDERS; i++) { %>
						<tr>
						<td>
							<label class="label" for="product<%= i%>">Producto <%= i%>:</label>
		        		<select name="product<%= i%>">
		        			<option value="none" selected="selected">Ningun Producto</option>
		        			<% for (Object productObject : products) {
		        				Product product = (Product) productObject;
		        			%>
		             		<option value="<%= product.getId() %>"><%= product.getName() %></option>
		                	<% } %>
		        		</select>
		        		</td>
						<td>
						<label class="label" for="quantity<%= i%>">Cantidad: </label>
						<input type="number" name= "quantity<%= i%>" value="0" min="0" max="100"/>						
						</td>
						</tr>
						<% } %>
						</table>
						<br>
						<label class="label" for="comments">Comentarios: </label> 
						<textarea cols="40" rows="3" id="comments" name="comments" ></textarea>
						
						<input type="submit" value="Ordenar Pedido" />
					</form>
				</div>
				<br>
				<div id="links">
					<ul>
						<li><a href="orders">Mis pedidos</a></li>
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
