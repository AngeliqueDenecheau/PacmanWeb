<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Boutique de cosmétiques</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
		<%@ include file="/WEB-INF/navbar.jsp" %>
		<br>

		<c:choose>
		    <c:when test="${empty requestScope.cosmetics}">
		    	<div class="alert alert-danger" role="alert">Aucun cosmétique disponible.</div>
		    </c:when>
		    <c:otherwise>
				<div class="row row-cols-1 row-cols-md-4 g-4 col-lg-10 offset-lg-1">
			    	<c:if test="${!empty sessionScope.user}">Votre argent : <c:out value="${sessionScope.user.money}"></c:out><br><br></c:if>
					<c:forEach items="${requestScope.cosmetics}" var="mapCosmetics" varStatus="boucle">
			    	  <div class="col">
			    	  	<c:choose>
					    	<c:when test="${!empty requestScope.purchases[mapCosmetics.cosmetic_id]}">
					    		<div class="card h-100 border-success mb-3">
							      <img src="images/cosmetics/<c:out value="${mapCosmetics.image_filename}"></c:out>" class="card-img-top" alt="Cosmétique">
							      <div class="card-body">
							        <h5 class="card-title"><c:out value="${mapCosmetics.name}"></c:out></h5>
							      </div>
							      <ul class="list-group list-group-flush">
								    <li class="list-group-item"><c:out value="Type : ${mapCosmetics.type}"></c:out></li>
								    <li class="list-group-item"><c:out value="Prix : ${mapCosmetics.price}"></c:out></li>
								  </ul>
								  <div class="card-body">
									<button type="button" class="btn btn-success col-6 offset-3" disabled>Possédé</button>
							      </div>
							    </div>
					    	</c:when>
					    	<c:otherwise>
					    		<div class="card h-100 border-primary mb-3">
							      <img src="images/cosmetics/<c:out value="${mapCosmetics.image_filename}"></c:out>" class="card-img-top" alt="Cosmétique">
							      <div class="card-body">
							        <h5 class="card-title"><c:out value="${mapCosmetics.name}"></c:out></h5>
							      </div>
							      <ul class="list-group list-group-flush">
								    <li class="list-group-item"><c:out value="Type : ${mapCosmetics.type}"></c:out></li>
								    <li class="list-group-item"><c:out value="Prix : ${mapCosmetics.price}"></c:out></li>
								  </ul>
								  <div class="card-body">
									<button type="button" class="btn btn-primary col-6 offset-3">Acheter</button>
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