<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<spring:url value="/css/style.css" var="urlCss" />
<link rel="STYLESHEET" href="${urlCss}" type="text/css" />
<title>FIFA</title>
</head>
<body>
	<h1>Fifa World Cup Qatar 2022</h1>

	<h2>Stadion: ${stadium}</h2>

	<table>
		<tr>
			<th>Nr</th>
			<th>Voetbalmatch</th>
			<th>Datum</th>
			<th>Aftrap</th>
			<th>Tickets</th>
		</tr>

		<c:forEach var="wedstrijd" items="${wedstrijden}">
			<tr>
				<td><a href="/fifa/${wedstrijd.getId()}">${wedstrijd.getId()}</a></td>
				<td>${wedstrijd.getLanden()[0]}- ${wedstrijd.getLanden()[1]}</td>
				<td>${wedstrijd.getDag()}november</td>
				<td>${wedstrijd.getUur()}</td>
				<td>${wedstrijd.getTickets()}</td>
			</tr>
		</c:forEach>

	</table>

	<form:form action='logout' method='POST'>
		<input type="submit" value="Log out" />
	</form:form>

</body>
</html>