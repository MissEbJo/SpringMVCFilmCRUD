<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film Removal Tool</title>
</head>
<body>
	<h1>
		<c:choose>
			<c:when test="${ message != null }">
				${message}
			</c:when>
		</c:choose>
	</h1>
	<br>
	<p>
		<a href="index.html">Home</a>
	</p>
</body>
</html>
