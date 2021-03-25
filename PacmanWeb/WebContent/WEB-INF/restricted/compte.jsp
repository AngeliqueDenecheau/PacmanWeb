<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Compte utilisateur</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
		<%@ include file="/WEB-INF/navbar.jsp" %>
		<br>
		
		<c:if test="${!empty requestScope.succes}"><div class="alert alert-success" role="alert"><c:out value="${requestScope.succes}"/></div><br></c:if>
		<c:if test="${!empty requestScope.erreur}"><div class="alert alert-danger" role="alert"><c:out value="${requestScope.erreur}"/></div><br></c:if>
		
		<div class="d-flex align-items-start ms-5">
		  <div class="nav flex-column nav-pills me-3" id="v-pills-tab" role="tablist" aria-orientation="vertical">
		    <button class="nav-link active" id="v-pills-profil-tab" data-bs-toggle="pill" data-bs-target="#v-pills-profil" type="button" role="tab" aria-controls="v-pills-profil" aria-selected="true">Mon profil</button>
		    <button class="nav-link" id="v-pills-games-tab" data-bs-toggle="pill" data-bs-target="#v-pills-games" type="button" role="tab" aria-controls="v-pills-games" aria-selected="false">Mes parties</button>
		    <button class="nav-link" id="v-pills-cosmetics-tab" data-bs-toggle="pill" data-bs-target="#v-pills-cosmetics" type="button" role="tab" aria-controls="v-pills-cosmetics" aria-selected="false">Mes cosmétiques</button>
		    <button class="nav-link" id="v-pills-settings-tab" data-bs-toggle="pill" data-bs-target="#v-pills-settings" type="button" role="tab" aria-controls="v-pills-settings" aria-selected="false">Paramètres</button>
		  </div>
		  <div class="tab-content" id="v-pills-tabContent">
		    <div class="tab-pane fade show active" id="v-pills-profil" role="tabpanel" aria-labelledby="v-pills-profil-tab">
				<p><img src="images/users/<c:out value="${empty sessionScope.user.image ? 'user0.png' : sessionScope.user.image}"/>" class="img-thumbnail" alt="Photo de profil de l'utilisateur" style="height:200px"></p>
				<p>Login : <c:out value="${sessionScope.user.login}" /></p>
				<p>Email : <c:out value="${sessionScope.user.email}" /></p>
				<p>Token : <a>Afficher le token</a></p>
				<p>Argent : <c:out value="${sessionScope.user.money}" /></p>
				<p>Score : <c:out value="${sessionScope.user.score}" /></p>
				<br>
				<p>Compte créé le : <joda:format value="${sessionScope.user.created}" pattern="dd/MM/yyyy HH:mm:ss"></joda:format></p>
				<br>
				<a href="<c:url value="update"/>"><button type="button" class="btn btn-outline-primary">Modifier mon profil</button></a>
			</div>
		    <div class="tab-pane fade" id="v-pills-games" role="tabpanel" aria-labelledby="v-pills-games-tab">
				<p>Nombre de parties jouées : <c:out value="${sessionScope.user.parties_jouees}" /></p>
				<p>Nombre de parties gagnées : <c:out value="${sessionScope.user.parties_gagnees}" /></p>
			</div>
		    <div class="tab-pane fade" id="v-pills-cosmetics" role="tabpanel" aria-labelledby="v-pills-cosmetics-tab">...</div>
		    <div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">
				<a href="<c:url value="delete"/>"><button type="button" class="btn btn-outline-danger">Supprimer mon compte</button></a>
			</div>
		  </div>
		</div>
	</body>
</html>