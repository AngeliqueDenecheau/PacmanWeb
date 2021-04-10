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
		
		<%@ include file="/WEB-INF/alerts.jsp" %>
		<!-- 
		<c:if test="${!empty requestScope.succes}"><div class="alert alert-success" role="alert"><c:out value="${requestScope.succes}"/></div><br></c:if>
		<c:if test="${!empty requestScope.erreur}"><div class="alert alert-danger" role="alert"><c:out value="${requestScope.erreur}"/></div><br></c:if>
		 -->
		
		<div class="d-flex align-items-start ms-5">
		  <div class="nav flex-column nav-pills me-3" id="v-pills-tab" role="tablist" aria-orientation="vertical">
		    <button class="nav-link active" id="v-pills-profil-tab" data-bs-toggle="pill" data-bs-target="#v-pills-profil" type="button" role="tab" aria-controls="v-pills-profil" aria-selected="true">Mon profil</button>
		    <button class="nav-link" id="v-pills-games-tab" data-bs-toggle="pill" data-bs-target="#v-pills-games" type="button" role="tab" aria-controls="v-pills-games" aria-selected="false">Mes parties</button>
		    <button class="nav-link" id="v-pills-cosmetics-tab" data-bs-toggle="pill" data-bs-target="#v-pills-cosmetics" type="button" role="tab" aria-controls="v-pills-cosmetics" aria-selected="false">Mes cosmétiques</button>
		    <button class="nav-link" id="v-pills-mazes-tab" data-bs-toggle="pill" data-bs-target="#v-pills-mazes" type="button" role="tab" aria-controls="v-pills-mazes" aria-selected="false">Mes labyrinthes</button>
		    <button class="nav-link" id="v-pills-settings-tab" data-bs-toggle="pill" data-bs-target="#v-pills-settings" type="button" role="tab" aria-controls="v-pills-settings" aria-selected="false">Paramètres</button>
		  </div>
		  <div class="tab-content" id="v-pills-tabContent">
		    <div class="tab-pane fade show active" id="v-pills-profil" role="tabpanel" aria-labelledby="v-pills-profil-tab">
				<p><img src="images/users/<c:out value="${empty sessionScope.user.image ? 'user0.png' : sessionScope.user.image}"/>" class="img-thumbnail" alt="Photo de profil de l'utilisateur" style="height:200px"></p>
				<p>Login : <c:out value="${sessionScope.user.login}" /></p>
				<p>Email : <c:out value="${sessionScope.user.email}" /></p>
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
				<c:if test="${!empty requestScope.games}">
					<table class="table table-striped text-center">
					  <thead>
					    <tr>
					      <th scope="col">ID de la partie</th>
					      <th scope="col">Date</th>
					      <th scope="col">Mode de jeu</th>
					      <th scope="col">Résultat</th>
					    </tr>
					  </thead>
					  <tbody>
					    <c:forEach items="${requestScope.games}" var="mapGames" varStatus="boucle">
							<tr>
						      <th scope="row"><c:out value="${mapGames.game_id}"></c:out></th>
						      <td><joda:format value="${mapGames.created}" pattern="dd/MM/yyyy HH:mm:ss"></joda:format></td>
						      <td><c:out value="${mapGames.gamemode == 'singleplayer' ? '1 joueur' : '2 joueurs'}"></c:out></td>
						      <td class="<c:out value="${mapGames.winner == sessionScope.user.user_id ? 'table-success' : 'table-danger'}"></c:out>"><c:out value="${mapGames.winner == sessionScope.user.user_id ? 'Victoire' : 'Défaite'}"></c:out></td>
						    </tr>
						</c:forEach>
					   </tbody>
					</table>
				</c:if>
			</div>
		    <div class="tab-pane fade" id="v-pills-cosmetics" role="tabpanel" aria-labelledby="v-pills-cosmetics-tab">
			    <form method="post" action="<c:url value="skins"/>">
					<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
					  <li class="nav-item" role="pacmans">
					    <button class="nav-link active" id="pills-pacmans-tab" data-bs-toggle="pill" data-bs-target="#pills-pacmans" type="button" role="tab" aria-controls="pills-pacmans" aria-selected="true">Pacmans</button>
					  </li>
					  <li class="nav-item" role="ghosts">
					    <button class="nav-link" id="pills-ghosts-tab" data-bs-toggle="pill" data-bs-target="#pills-ghosts" type="button" role="tab" aria-controls="pills-ghosts" aria-selected="false">Fantômes</button>
					  </li>
					</ul>
					<div class="tab-content" id="pills-tabContent">
					  <div class="tab-pane fade show active" id="pills-pacmans" role="tabpanel" aria-labelledby="pills-pacmans-tab">
						<div class="card-group">
							<div class="card">
							    <img src="images/cosmetics/default_pacman.png" class="card-img-top" alt="Cosmétic">
							    <div class="card-footer">
							      <small class="text-muted">Pacman</small>
							    </div>
							    <div class="card-body">
								    <c:choose>
									    <c:when test="${sessionScope.user.pacman_skin <= 0}">
									    	<button type="button" class="btn btn-secondary col-12" disabled>Sélectionné</button>
									    </c:when>
									    <c:otherwise>
									    	<!-- <a href="update?pacman_skin=0"><button type="button" class="btn btn-primary col-12">Sélectionner</button></a> -->
									    	<button type="submit" class="btn btn-primary col-12" name="submit" value="pacman_0">Sélectionner</button>
										</c:otherwise>
									</c:choose>								
							    </div>
							</div>
							<c:forEach items="${requestScope.skinsPacmans}" var="mapSkinsPacmans" varStatus="boucle">
							  <div class="card">
							    <img src="images/cosmetics/<c:out value="${mapSkinsPacmans.image_filename}"></c:out>" class="card-img-top" alt="Cosmétic">
							    <div class="card-footer">
							      <small class="text-muted"><c:out value="${mapSkinsPacmans.name}"></c:out></small>
							    </div>
							    <div class="card-body">
							    	<c:choose>
									    <c:when test="${mapSkinsPacmans.cosmetic_id == sessionScope.user.pacman_skin}">
									    	<button type="button" class="btn btn-secondary col-12" disabled>Sélectionné</button>
									    </c:when>
									    <c:otherwise>
									    	<button type="submit" class="btn btn-primary col-12" name="submit" value='pacman_<c:out value="${mapSkinsPacmans.cosmetic_id}"></c:out>'>Sélectionner</button>
										</c:otherwise>
									</c:choose>		
							    </div>
							  </div>
							</c:forEach>
						</div>
					  </div>
					  <div class="tab-pane fade" id="pills-ghosts" role="tabpanel" aria-labelledby="pills-ghosts-tab">
						<div class="card-group">
							<div class="card">
							    <img src="images/cosmetics/default_ghost.png" class="card-img-top" alt="Cosmétic">
							    <div class="card-footer">
							      <small class="text-muted">Blinky</small>
							    </div>
							    <div class="card-body">
								    <c:choose>
									    <c:when test="${sessionScope.user.ghost_skin <= 0}">
									    	<button type="button" class="btn btn-secondary col-12" disabled>Sélectionné</button>
									    </c:when>
									    <c:otherwise>
									    	<button type="submit" class="btn btn-primary col-12" name="submit" value="ghost_0">Sélectionner</button>
										</c:otherwise>
									</c:choose>	
							    </div>
							</div>
							<c:forEach items="${requestScope.skinsGhosts}" var="mapSkinsGhosts" varStatus="boucle">
							  <div class="card">
							    <img src="images/cosmetics/<c:out value="${mapSkinsGhosts.image_filename}"></c:out>" class="card-img-top" alt="Cosmétic">
							    <div class="card-footer">
							      <small class="text-muted"><c:out value="${mapSkinsGhosts.name}"></c:out></small>
							    </div>
							    <div class="card-body">
							    	<c:choose>
									    <c:when test="${mapSkinsGhosts.cosmetic_id == sessionScope.user.ghost_skin}">
									    	<button type="button" class="btn btn-secondary col-12" disabled>Sélectionné</button>
									    </c:when>
									    <c:otherwise>
									    	<button type="submit" class="btn btn-primary col-12" name="submit" value='ghost_<c:out value="${mapSkinsGhosts.cosmetic_id}"></c:out>'>Sélectionner</button>
										</c:otherwise>
									</c:choose>		
							    </div>
							  </div>
							</c:forEach>
						</div>
					  </div>
					</div>
				</form>
			</div>
		    <div class="tab-pane fade" id="v-pills-mazes" role="tabpanel" aria-labelledby="v-pills-mazes-tab">
				<div class="row row-cols-1 row-cols-md-4 g-4 col-lg-10 offset-lg-1">
				    <div class="col">
						<div class="card h-100 mb-3">
						    <img src="images/mazes/default_maze.png" class="card-img-top" alt="Labyrinthe">
						    <div class="card-footer">
						      <small class="text-muted">Labyrinthe classique</small>
						    </div>
						</div>
					</div>
					<c:forEach items="${requestScope.mazes}" var="mapMazes" varStatus="boucle">
					    <div class="col">
						  <div class="card h-100 mb-3">
						    <img src="images/mazes/<c:out value="${mapMazes.filename}"></c:out>" class="card-img-top" alt="Labyrinthe">
						    <div class="card-footer">
						      <small class="text-muted"><c:out value="${mapMazes.name}"></c:out></small>
						    </div>
						  </div>
						</div>
					</c:forEach>
				</div>
			</div>
		    <div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">
				<a href="<c:url value="delete"/>"><button type="button" class="btn btn-outline-danger">Supprimer mon compte</button></a>
			</div>
		  </div>
		</div>
	</body>
</html>