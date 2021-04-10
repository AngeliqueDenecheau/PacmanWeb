<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Inscription / Connection</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
		<%@ include file="navbar.jsp" %>
		<br>
		
		<div class="card border-primary col-lg-4 offset-lg-4 col-md-6 offset-md-3 col-sm-8 offset-sm-2 col-10 offset-1">
		  <div class="card-header">
		    <ul class="nav nav-pills justify-content-center card-header-pills">
		      <li class="nav-item" role="presentation">
			    <button class="nav-link <c:if test="${param.type == 'subscribe' || requestScope.type == 'subscribe'}">active</c:if>" id="pills-subscribe-tab" data-bs-toggle="pill" data-bs-target="#pills-subscribe" type="button" role="tab" aria-controls="pills-subscribe" aria-selected="true">Inscription</button>
			  </li>
		      <li class="nav-item" role="presentation">
			    <button class="nav-link <c:if test="${param.type == 'login' || requestScope.type == 'login' || (empty param.type && empty requestScope.type)}">active</c:if>" id="pills-login-tab" data-bs-toggle="pill" data-bs-target="#pills-login" type="button" role="tab" aria-controls="pills-login" aria-selected="false">Connection</button>
			  </li>
		    </ul>
		  </div>
		  <div class="card-body">
		    <div class="tab-content" id="pills-tabContent">
			  <div class="tab-pane fade <c:if test="${param.type == 'subscribe' || requestScope.type == 'subscribe'}">show active</c:if>" id="pills-subscribe" role="tabpanel" aria-labelledby="pills-subscribe-tab">
				<c:if test="${requestScope.type == 'subscribe' && !empty requestScope.form.erreurMsg}"><div class="alert alert-danger" role="alert">${requestScope.form.erreurMsg}</div></c:if>
				<form method="post" action="<c:url value="connection?type=subscribe"/>">
				  <div class="mb-3">
				    <label for="login" class="form-label">Login</label>
				    <input type="text" class="form-control" id="login" name="login" value="<c:out value="${param.login}"/>" aria-describedby="loginHelp" required>
				    <div id="loginHelp" class="form-text">Entre 3 et 20 caractères.</div>
				  </div>
				  <div class="mb-3">
				    <label for="email" class="form-label">Adresse Email</label>
				    <input type="email" class="form-control" id="email" name="email" value="<c:out value="${param.email}"/>" required>
				  </div>
				  <div class="mb-3">
				    <label for="password" class="form-label">Mot de passe</label>
				    <input type="password" class="form-control" id="password" name="password" aria-describedby="passwordHelp" required>
				    <div id="passwordHelp" class="form-text">Entre 8 et 20 caractères.<br>Doit contenir au moins un chiffre, une lettre et un caractère spécial.</div>
				  </div>
				  <div class="mb-3">
				    <label for="confirm_password" class="form-label">Confirmer le mot de passe</label>
				    <input type="password" class="form-control" id="confirm_password" name="confirm_password" required>
				  </div>
				  <div class="mb-3 form-check">
				    <input type="checkbox" class="form-check-input" id="rememberme" name="rememberme">
				    <label class="form-check-label" for="rememberme">Se souvenir de moi</label>
				  </div>
				  <button type="reset" class="btn btn-danger">Réinitialiser</button>
				  <button type="submit" class="btn btn-success">Valider</button>
				</form>
			  </div>
			  <div class="tab-pane fade <c:if test="${param.type == 'login' || requestScope.type == 'login' || (empty requestScope.type && empty param.type)}">show active</c:if>" id="pills-login" role="tabpanel" aria-labelledby="pills-login-tab">
				<c:if test="${requestScope.type == 'login' && !empty requestScope.form.erreurMsg}"><div class="alert alert-danger" role="alert">${requestScope.form.erreurMsg}</div></c:if>
				<form method="post" action="<c:url value="connection?type=login"/>">
				  <div class="mb-3">
				    <label for="login" class="form-label">Login</label>
				    <input type="text" class="form-control" id="login" name="login" value="<c:out value="${param.login}"/>" aria-describedby="emailHelp" required>
				  </div>
				  <div class="mb-3">
				    <label for="password" class="form-label">Mot de passe</label>
				    <input type="password" class="form-control" id="password" name="password" required>
				  </div>
				  <div class="mb-3 form-check">
				    <input type="checkbox" class="form-check-input" id="rememberme" name="rememberme">
				    <label class="form-check-label" for="rememberme">Se souvenir de moi</label>
				  </div>
				  <button type="reset" class="btn btn-danger">Réinitialiser</button>
				  <button type="submit" class="btn btn-success">Valider</button>
				</form>
			  </div>
			</div>
		  </div>
		</div>
    </body>
</html>