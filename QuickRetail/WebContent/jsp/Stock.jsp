<%@ include file="Header.jsp" %>
<%@ page import="com.quickretail.dao.DBManager" %>
<%@ page import="java.util.*" %>
<%@ page import="com.quickretail.bo.entity.Product" %>
<%@ page import="com.quickretail.bo.entity.StockDetail" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="../style/Style.css" type="text/css">
<style>
table {
    width:100%;
}
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 5px;
    text-align: left;
}
table#table1 tr:nth-child(even) {
    background-color: #eee;
}
table#table1 tr:nth-child(odd) {
   background-color:#fff;
}
table#table1 th	{
    background-color: black;
    color: white;
}
</style>
</head>
<body>
<br><br>
<h1>Stock Available now</h1>
<h2>Select</h2>
<br>
<form action="../app/controller" method="post">
<table id="table1" style="width:100%">
<tr>
<th>Product</th>
<th>Retailer</th>
<th>Price</th>
<th>select</th>
</tr>
<%
List<StockDetail> list = (List<StockDetail>)request.getSession().getAttribute("STOCK_LIST");
System.out.println(" List "+list.size());
System.out.println("Entered StockJSP");

for ( StockDetail sd : list ) {
%>
<tr>
<td><%= sd.getProduct().getProductName() %></td>
<td><%= sd.getRetailer().getRetailerName() %></td>
<td><%= sd.getUnitPrice() %></td>
<td><input type="radio" name="stockid" value="<%=sd.getStockId()%>"></td>
</tr>
<% } %>
</table>
<br>
<input class="myButton" type="submit" name="search" value="Add to Shopping Cart">
<input type="hidden" name="action" value="process">
<input type="hidden" name="activity" value="shopping_cart_add">
</form>
<br>
<form action="../app/controller" method="post">
<% if ( request.getSession().getAttribute("SHOPPING_CART") != null ) {
	%>
<input class="myButton" type="submit" name="submit" value="checkout">
<input type="hidden" name="action" value="process">
<input type="hidden" name="activity" value="checkout">
<% } %>
</form>
</body>
</html>