<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container-fluid">
    <a class="navbar-brand" href="<c:url value="/" />">PacmanWeb</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarText">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <!-- <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="mazes">Labyrinthes</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="cosmetics">Cosmetiques</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="ranking">Classement des joueurs</a>
        </li> -->
        <li class="nav-item">
          <a class="<c:out value="${requestScope.currentpage == 'mazes' ? 'nav-link active' : 'navlink'}" />" <c:if test="${requestScope.currentpage == 'mazes'}">aria-current="page"</c:if> href="mazes">Labyrinthes</a>
        </li>
        <li class="nav-item">
          <a class="<c:out value="${requestScope.currentpage == 'cosmetics' ? 'nav-link active' : 'navlink'}" />" <c:if test="${requestScope.currentpage == 'cosmetics'}">aria-current="page"</c:if> href="cosmetics">Cosmetiques</a>
        </li>
        <li class="nav-item">
          <a class="<c:out value="${requestScope.currentpage == 'ranking' ? 'nav-link active' : 'navlink'}" />" <c:if test="${requestScope.currentpage == 'ranking'}">aria-current="page"</c:if> href="ranking">Classement des joueurs</a>
        </li>
      </ul>
      <c:choose>
	    <c:when test="${!empty sessionScope.user}">
	    	<span class="navbar-nav nav-item">
	        	<a class="nav-link" href="<c:url value="compte" />">Mon compte</a>
	        </span>
	        <span class="navbar-nav nav-item">
	        	<a class="nav-link" href="<c:url value="deconnexion"/>">Déconnexion</a>
	      	</span>
	    </c:when>
	    <c:otherwise>
		    <span class="navbar-nav nav-item">
	        	<a class="nav-link" href="<c:url value="subscribe"/>">Inscription</a>
	      	</span>
	      	<span class="navbar-nav nav-item">
	        	<a class="nav-link" href="<c:url value="login"/>">Connection</a>
	      	</span>
	    </c:otherwise>
	  </c:choose>
    </div>
  </div>
</nav>