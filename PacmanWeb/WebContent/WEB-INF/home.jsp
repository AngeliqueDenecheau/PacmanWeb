<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>HOME</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
		<%@ include file="navbar.jsp" %>
		<br>
		
		<c:if test="${!empty requestScope.erreur}"><div class="alert alert-danger" role="alert"><c:out value="${requestScope.erreur}"/></div><br></c:if>
		
		<div class="d-grid gap-2 col-6 mx-auto">
		  <a href="<c:url value="files/client_jeu_pacman.png"/>"><button class="btn btn-info" type="button">Télécharger le jeu Pacman</button></a>
		</div>
	</body>
</html>