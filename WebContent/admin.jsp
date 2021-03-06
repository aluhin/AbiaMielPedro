<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<jsp:useBean id="customer" class="com.abiamiel.model.Customer" scope="session" />
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
				<h1 class="title">Administracion</h1>
				<div class="content">
					<p>
						Logueado como <jsp:getProperty name = "customer" property = "nick" />. Aqui podra realizar 
						modificaciones en su cuenta de administrador, crear nuevos clientes o administradores y gestionar 
						los pedidos de los clientes. Para ello seleccione una de las siguientes opciones:
					</p>
				</div>
				
				<div id="links">
					<ul>
						<li><a href="orders?admin">Gestionar pedidos</a></li>
						<li><a href="orders">Gestionar consultas (Sin hacer)</a></li>
						<li><a href="neworder">Crear una cuenta nueva (Sin hacer)</a></li>
						<li><a href="modification.jsp">Consultar o modificar mis datos personales (Sin hacer)</a></li>
						<li><a href="login?end=true">Finalizar sesion como administrador <jsp:getProperty name = "customer" property = "nick" /></a></li>
						<li><a href="">Darme de baja en el sistema (Sin hacer)</a></li>
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
			<p>Copyright � 2010-2011 Abia Miel Pedro</p>
		</div>
	</div>
</body>
</html>
