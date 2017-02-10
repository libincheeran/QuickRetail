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
<h1>Confirmation Page</h1>
<br>
<table id="table1" style="width:100%">
<tr>
<th>Product</th>
<th>Retailer</th>
<th>Price</th>
</tr>
<%
Map<String,StockGroups> map = (Map<String,StockGroups>)request.getAttribute("STOCK_GROUP_MAP");
String totalValue = (String)request.getAttribute("TOTAL_CHARGES");

Set<String> set = map.keySet();
for ( String s : set ){
	%> <tr> <%
	StockGroups sg = map.get(s);
	for ( StockDetail sd : sg.getStockDetailList()) {
		%><td><%= sd.getProduct().getProductName() %></td>
		<td><%= sd.getRetailer().getRetailerName() %></td>
		<td><%= sd.getUnitPrice() %></td>
		</tr> <%
	} %>
	<tr>
	   <td bgcolor="#FF0000" colspan ="2"> Retail Transportation Charges </td>
	   <td><%= sg.getZonePrice() %>
	</tr>
<% } %>
<tr> 
<td colspan="2"> Total Charges : </td>
<td bgcolor="#FF0000" ><%=totalValue %> </td>
</tr>
</table>
<br>
<form action="../app/controller" method="post">
<input class="myButton" type="submit" name="Confirm" value="Confirm Your Purchase">
<input type="hidden" name="action" value="process">
<input type="hidden" name="activity" value="confirm">
</form>
</body>
</html>