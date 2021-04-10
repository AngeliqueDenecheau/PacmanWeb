<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Classement des joueurs</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
		<%@ include file="/WEB-INF/navbar.jsp" %>
		<br>
		
		<ol class="list-group list-group-numbered col-6 offset-3">
			<c:forEach items="${requestScope.users}" var="user" varStatus="boucle">
			  <li class="list-group-item d-flex justify-content-between align-items-start">
			    <div class="ms-2 me-auto">
			      <div class="fw-bold"><c:out value="${boucle.index + 1}"></c:out>. <c:out value="${user.login}"></c:out></div>
			      <fmt:formatNumber type="number" maxFractionDigits="1" value="${user.parties_jouees > 0 ? user.parties_gagnees * 100 / user.parties_jouees : 0}" />
			      <c:out value="% de victoire"></c:out>
			    </div>
			    <span class="badge bg-primary rounded-pill">Score : <c:out value="${user.score}"></c:out></span>
			  </li>
			</c:forEach>
		</ol>
	</body>
</html>