<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<spring:url value="/css/style.css" var="urlCss"/>
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
					<td><a href="/fifa/${wedstrijd.getWedstrijd().getId()}">${wedstrijd.getWedstrijd().getId()}</a></td>
					<td>${wedstrijd.getWedstrijd().getLanden()[0]} - ${wedstrijd.getWedstrijd().getLanden()[1]}</td>
					<td>${wedstrijd.getWedstrijd().getDag()} november</td>
					<td>${wedstrijd.getWedstrijd().getUur()}</td>				
					<td>${wedstrijd.getTickets()}</td>				
				</tr>
		</c:forEach>
		
	</table>

</body>
</html>