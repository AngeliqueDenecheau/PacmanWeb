<div class="col-10 offset-1">
   	<c:if test="${!empty sessionScope.erreur}">
	    <div class="alert alert-danger" role="alert"><c:out value="${sessionScope.erreur}"></c:out></div><br>
	    <c:remove var="erreur" scope="session" />
	</c:if>
   	<c:if test="${!empty sessionScope.succes}">
	    <div class="alert alert-success" role="alert"><c:out value="${sessionScope.succes}"></c:out></div><br>
	    <c:remove var="succes" scope="session" />
	</c:if>
</div>