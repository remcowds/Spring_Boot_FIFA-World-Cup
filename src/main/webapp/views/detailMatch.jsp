<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<spring:url value="/css/style.css" var="urlCss"/>
        <link rel="STYLESHEET" href="${urlCss}" type="text/css" /> 
<title>Ticket kopen</title>
</head>
<body>	
	<h1>Fifa World Cup Qatar 2022</h1>
	<h2>Stadion: ${stadium}</h2>
	<h3>${land1} vs ${land2} op ${dag}-11 </h3>
	<h4>aantal tickets beschikbaar: ${aantalTickets}</h4>
	
	<!-- action nog aanpassen ig? -->
	<form:form method="POST" action="" modelAttribute="aankoop" >
		<div class="divken">
			<label>email:</label> <form:input path="email"  /> <form:errors path="email" cssClass="error"/>
		</div>
		
		<div class="divken">
			<label>aantal tickets: </label> <form:input path="aantalTickets" /> <form:errors path="aantalTickets" cssClass="error" />
		</div>
	
		
		<div class="divken">
			<label>voetbalCode1: </label> <form:input path="voetbalcode1" /> <form:errors path="voetbalcode1" cssClass="error"/>
		</div>
	
		<div class="divken">
			<label>voetbalCode2: </label> <form:input path="voetbalcode2" /> <form:errors path="voetbalcode2" cssClass="error"/>
		</div>

	
		<input type="submit" value="Koop" />
	
	</form:form>
	
</body>
</html>