<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Film</title>
</head>
<body>
  <h3>Update the Film</h3>
  <form action="UpdateFilmData.do" method="POST">
  
  <label for="filmTitle">Id:</label>
    <input type="number" name="id" value="${film.id}" readonly/>
    <br>
  <label for="filmTitle">Title:</label>
    <input type="text" name="title" value="${film.title}"/>
    <br>
    <%-- <label for="category">Category:</label>
    <input type="text" name="category.id" value="${film.category}"/>
    <br> --%>
    <label for="description">Description:</label>
    <input type="text" name="description" value="${film.description}"/>
    <br>
    <label for="releaseYear">Release Year:</label>
    <input type="number" name="releaseYear" value="${film.releaseYear}"/> 
    <br>
    <label for="languageId">Language ID:</label>
    <input type="number" name="languageId" value="${film.languageId}"/> 
    <br>
    <label for="length">Length:</label>
    <input type="number" name="length"  value="${film.length}"/>
    <br>
    <label for="rating">Rating:</label>
    <input type="text" name="rating" value="${film.rating}"/>
    <br> 
    <label for="specialFeatures">Special Features:</label>
    <input type="text" name="specialFeatures" value="${film.specialFeatures}"/>
    <br> 
    
    <input type="submit" value="Update Film"> 
    <br> 
  </form>
</body>
</html>