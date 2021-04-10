<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>HOME</title>
		<link href="style.css" rel="stylesheet">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
		<%@ include file="navbar.jsp" %>
		<br>
		
		<%@ include file="/WEB-INF/alerts.jsp" %>
		<!-- <c:if test="${!empty requestScope.erreur}"><div class="alert alert-danger" role="alert"><c:out value="${requestScope.erreur}"/></div><br></c:if> -->
		
	    <div>
		    <div class="scrollcolors col-2 offset-5">
		    	<a href="<c:url value="files/Client.jar"/>"><span>Télécharger le jeu Pacman</span></a>
		    </div>
			<br>
			<div class="col-2 offset-5 text-center">
				<div class="alert alert-info" role="alert">
				  Nombre total de téléchargements : <c:out value="${applicationScope.downloadcounter}"></c:out>
				</div>
			</div>
	    </div>
	</body>
</html>