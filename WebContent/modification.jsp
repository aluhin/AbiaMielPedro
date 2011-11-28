<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<jsp:useBean id="customer" class="com.abiamiel.model.Customer" scope="session" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
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
				<h1 class="title">Editar Datos</h1>
				<div class="content">
					<p>
						Puede modificar los datos que se presentan a continuacion. Tenga en cuenta que el email y el nombre de usuario proporcionado el el momento de su registro no se pueden modificar:
					</p>
				</div>
			</div>
			<div class="floating-box">
				<form name="input" action="modification" method="post">
					Nombre de usuario: <input type="text" name="nick" readonly="readonly" value="<jsp:getProperty name = "customer" property = "nick" />"/>
					<br> Contrasenia: <input type="password" name="password" required="required" value="<jsp:getProperty name = "customer" property = "password" />"/>
					<br> Repita la contrasenia: <input type="password" name="repited_password" required="required" value="<jsp:getProperty name = "customer" property = "password" />"/>
					<br> Email: <input type="email" name="email" readonly="readonly" value="<jsp:getProperty name = "customer" property = "email" />"/>
					<br>
					<br> Nombre: <input type="text" name="first_name" value="<jsp:getProperty name = "customer" property = "firstName" />"/>
					<br> Apellidos: <input type="text" name="last_name" value="<jsp:getProperty name = "customer" property = "lastName" />"/>
					<br> Direccion: <input type="text" name="address" value="<jsp:getProperty name = "customer" property = "address" />"/>
					<br> Telefono de contacto: <input type="tel" name="telephone" value="<jsp:getProperty name = "customer" property = "telephone" />"/>
					<input type="submit" value="Modificar" />
				</form>
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
					<li><a href="registration.html" accesskey="R"><b>R</b>egistrarse</a></li>
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
