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
    <c:when test="${! empty film}">
      <ul>
        <li>Film Id: ${film.id}</li>
        <li>${film.title}</li>
        <li>${film.description}</li>
        <li>${film.releaseYear}</li>
        <li>${film.language}</li>
        <li>${film.length}</li>
        <li>${film.rating}</li>
        <li>${film.specialFeatures}</li>
        <li>${film.actors}</li>
        <!-- add catergory -->
      </ul>
     <!--  <form action="NextState.do">
      <button type="submit">Next</button>
      </form>
      <form action="PrevState.do">
      <button type="submit">Prev</button>
      </form> -->
    </c:when>
    <c:otherwise>
      <p>No film found</p>
    </c:otherwise>
  </c:choose>
  <p><a href="index.html">Home</a></p> 
</body>
</html>