<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Connection / Inscription</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
		<%@ include file="/WEB-INF/navbar.jsp" %>
		<br>
		
		<div class="card border-primary col-lg-4 offset-lg-4 col-md-6 offset-md-3 col-sm-8 offset-sm-2 col-10 offset-1">
			<div class="card-body">
				<form method="post" action="<c:url value="update"/>" enctype="multipart/form-data">
				  <c:if test="${!empty requestScope.form.erreur}"><div class="alert alert-danger" role="alert">${requestScope.form.erreur}</div></c:if>
				  <div class="mb-3">
				  	<img src="images/users/<c:out value="${empty sessionScope.user.image ? 'user0.png' : sessionScope.user.image}"/>" class="img-thumbnail" alt="Photo de profil de l'utilisateur" style="height:200px"><input class="form-control" type="file" id="image" name="image" aria-describedby="imageHelp">
					<div id="imageHelp" class="form-text">Formats acceptés : jpeg, jpg, png, webp, svg</div>
				  </div>
				  <div class="mb-3">
				    <label for="email" class="form-label">Addresse Email</label>
				    <input type="email" class="form-control" id="email" name="email" value="<c:out value="${sessionScope.user.email}"/>">
				  </div>
				  <div class="mb-3">
				    <label for="login" class="form-label">Login</label>
				    <input type="text" class="form-control" id="login" name="login" aria-describedby="loginHelp" value="<c:out value="${sessionScope.user.login}"/>">
					<div id="loginHelp" class="form-text">Entre 3 et 20 caractères.</div>
				  </div>
				  <div class="mb-3">
				    <label for="password" class="form-label">Nouveau mot de passe</label>
				    <input type="password" class="form-control" id="password" name="password" aria-describedby="passwordHelp">
				    <div id="passwordHelp" class="form-text">Entre 8 et 20 caractères.<br>Doit contenir au moins un chiffre, une lettre et un caractère spécial.</div>
				  </div>
				  <div class="mb-3">
				    <label for="confirm_password" class="form-label">Confirmer le nouveau mot de passe</label>
				    <input type="password" class="form-control" id="confirm_password" name="confirm_password">
				  </div>
				  <button type="submit" class="btn btn-primary">Valider les modifications</button>
				</form>
			</div>
		</div>
    </body>
</html>