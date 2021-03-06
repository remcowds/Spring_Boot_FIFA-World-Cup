<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<spring:url value="/css/style.css" var="urlCss" />
<link rel="STYLESHEET" href="${urlCss}" type="text/css" />
<title>FIFA</title>
</head>
<body>

	<c:choose>
		<c:when test="${verkocht == 0}"></c:when>
		<c:when test="${verkocht == 1}">
			<p>${verkocht}ticket werd aangekocht</p>
		</c:when>
		<c:otherwise>
			<p>${verkocht}tickets werden aangekocht</p>
		</c:otherwise>
	</c:choose>

	<c:if test="${uitverkocht}">
		<p>De voetbalmatch is uitverkocht!</p>
	</c:if>

	<h1>Fifa World Cup Qatar 2022</h1>

	<form:form method="POST" action="fifa">

		<span>stadiums</span>

		<select name="stadium">
			<c:forEach var="stadium" items="${stadiums}">
				<option>${stadium}</option>
			</c:forEach>
		</select>

		<br />
		<br />

		<input type="submit" value="Voer uit" />

	</form:form>

	<form:form action='logout' method='POST'>
		<input type="submit" value="Log out" />
	</form:form>

</body>
</html>