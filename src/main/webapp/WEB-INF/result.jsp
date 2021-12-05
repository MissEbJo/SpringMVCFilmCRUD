<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Films</title>
</head>
<body>
	<c:choose>
	<c:when test="${! empty films }">
	<c:forEach var="actor" items="${films }">
	<ul>
				<li>Film Id: ${films.id}</li>
				<li>${films.category}</li>
				<li>${films.title}</li>
				<li>${films.description}</li>
				<li>${films.releaseYear}</li>
				<li>${films.language}</li>
				<li>${films.length}</li>
				<li>${films.rating}</li>
				<li>${films.specialFeatures}</li>
	</c:forEach>
		</c:when>
		<c:when test="${! empty film}">
		
			<ul>
				<li>Film Id: ${film.id}</li>
				<li>${film.category}</li>
				<li>${film.title}</li>
				<li>${film.description}</li>
				<li>${film.releaseYear}</li>
				<li>${film.language}</li>
				<li>${film.length}</li>
				<li>${film.rating}</li>
				<li>${film.specialFeatures}</li>
				<c:if test="${film.actors.size() != 0}">
					<div>
						<h2>Starring:</h2>
						<h4>
							<c:forEach var="actor" items="${film.actors}">
								<ul>
									<li>${actor.firstName} ${actor.lastName}</li>
								</ul>
							</c:forEach>
						</h4>
					</div>
				</c:if>

				<!-- add catergory -->
			</ul>

	
		</c:when>
		<c:when test="${! empty message}">
			<p>${message }</p>
		</c:when>
		<c:otherwise>
			<p>No film found</p>
		</c:otherwise>
	</c:choose>
	<p>
		<a href="index.html">Home</a>
	</p>
	<br>
	<h3>Remove Film by ID</h3>
	<form action="RemoveFilmData.do" method="POST">
		Film Id: <input type="number" name="filmId" /> <input type="submit"
			value="Remove Film Data" />
	</form>
</body>
</html>