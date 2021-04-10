<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Boutique de labyrinthes</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
		<%@ include file="/WEB-INF/navbar.jsp" %>
		<br>
		
		<%@ include file="/WEB-INF/alerts.jsp" %>

		<c:choose>
		    <c:when test="${empty requestScope.mazes}">
		    	<div class="alert alert-danger" role="alert">Aucun labyrinthe disponible.</div>
		    </c:when>
		    <c:otherwise>
		    	<div class="col-2 offset-5">
					<c:if test="${!empty sessionScope.user}">
				    	<div class="alert alert-warning text-center" role="alert">
							Votre argent : <c:out value="${sessionScope.user.money}"></c:out>
						</div>
			    	</c:if>
			    </div>
		    
				<div class="row row-cols-1 row-cols-md-4 g-4 col-lg-10 offset-lg-1">
					<c:forEach items="${requestScope.mazes}" var="mapMazes" varStatus="boucle">
			    	  <div class="col">
			    	  	<c:choose>
					    	<c:when test="${!empty requestScope.purchases[mapMazes.maze_id]}">
					    		<div class="card h-100 border-success mb-3">
							      <img src="images/mazes/<c:out value="${mapMazes.filename}"></c:out>" class="card-img-top" alt="Labyrinthe">
							      <div class="card-body">
							        <h5 class="card-title"><c:out value="${mapMazes.name}"></c:out></h5>
							      </div>
							      <ul class="list-group list-group-flush">
								    <li class="list-group-item"><c:out value="Prix : ${mapMazes.price}"></c:out></li>
								  </ul>
								  <div class="card-body">
									<button type="button" class="btn btn-success col-6 offset-3" disabled>Possédé</button>
							      </div>
							    </div>
					    	</c:when>
					    	<c:otherwise>
					    		<div class="card h-100 border-primary mb-3">
							      <img src="images/mazes/<c:out value="${mapMazes.filename}"></c:out>" class="card-img-top" alt="Labyrinthe">
							      <div class="card-body">
							        <h5 class="card-title"><c:out value="${mapMazes.name}"></c:out></h5>
							      </div>
							      <ul class="list-group list-group-flush">
								    <li class="list-group-item"><c:out value="Prix : ${mapMazes.price}"></c:out></li>
								  </ul>
								  <div class="card-body">
									<a href="buy?id_maze=<c:out value="${mapMazes.maze_id}"></c:out>"><button type="button" class="btn btn-primary col-6 offset-3">Acheter</button></a>
							      </div>
							    </div>
					    	</c:otherwise>
						</c:choose>
					  </div>
			    	</c:forEach>
			  	</div>
		    </c:otherwise>
		</c:choose>
	</body>
</html>