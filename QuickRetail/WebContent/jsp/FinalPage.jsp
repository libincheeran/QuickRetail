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
    text-align: center;
}
tr:nth-child(even) {
    background-color: #fff;
}
table#table1 tr:nth-child(odd) {
   background-color:#fff;
}
table#table1 th	{
    background-color: #333;
    color: white;
}
</style>
</head>
<body>
<br><br>
<h1>Invoice/Shipping Details</h1>
<h1>Your order is confirmed..Here are the details</h1>
<br>
<table id="table1" style="width:100%">
<tr>
<th>Purchase Order</th>
<th>Shipping ID</th>
<th>Shipping Address</th>
</tr>
<%
System.out.println("getting attr");
Integer poId = (Integer) request.getAttribute("PO_ID");
System.out.println("PO_ID "+poId);
Integer shippingId = (Integer) request.getAttribute("SHIPPING_ID");
System.out.println("SHIPPING_ID "+shippingId);
Customer cust = (Customer)request.getSession().getAttribute("USER");
System.out.println("USER "+cust);
%>
<tr>
<td><%=poId %></td>
<td><%=shippingId %></td>
<td><%=cust.getAddress() %><br> <%=cust.getCity() %>,<%=cust.getState() %> - <%=cust.getZipCode() %></td>
</tr>
</table>
<br>
</body>
</html>