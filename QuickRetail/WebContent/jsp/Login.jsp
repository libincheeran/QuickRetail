<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Quick Retail</title>
<link rel="stylesheet" href="style/Style.css" type="text/css">
</head>
<body>
<header id="mast">
    	<h1>Quick Retail</h1>
</header>
<br>
   <img class="shoppingcart" src="img/cart.png" width="200" height="200" align="middle">
   <br>
   <form action="app/controller" method="post">
      <label for="name">Enter Your Login Details</label><br/>
      <input type="text" name="username" placeholder="username"/>
      <input type="password" name="password" placeholder="password"/>
      <input type="hidden" name="action" value="login">
      <input class="myButton"type="submit" value="Login"/>
   </form>
</body>
</html>