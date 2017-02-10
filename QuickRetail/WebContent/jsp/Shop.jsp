<%@ include file="Header.jsp" %>
<%@ page import="com.quickretail.dao.DBManager" %>
<%@ page import="com.quickretail.bo.entity.Product" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="../style/Style.css" type="text/css">
</head>
<body>
<br><br>
<h1>Shop now</h1>
<h2>Select Product</h2>
<br>
<form action="../app/controller" method="post">
<select name="product">
<%
List<Product> list = DBManager.getProduct();
for ( Product p : list ) {
	
%>
<option value="<%=p.getProductId() %>"> <%= p.getProductName() %> </option>
<% } %>
</select>
<input type="hidden" name="action" value="search">
<input type="hidden" name="activity" value="product">
<input class="myButton" type="submit" value ="search"/>
</form>
<br>
<form action="../app/controller" method="post">
<% if ( request.getSession().getAttribute("SHOPPING_CART") != null ) {
	%>
<input class="myButton" type="submit" name="checkout" value="Checkout">
<input type="hidden" name="action" value="process">
<input type="hidden" name="activity" value="checkout">
<% } %>
</form>

</body>
</html>