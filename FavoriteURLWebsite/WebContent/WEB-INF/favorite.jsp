<!-- 
/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="template-top.jsp" />
<!-- 
 * Name: Nitish Mudgal
 * AndrewId : nmudgal
 * Date: 30 Sept 2016
 * Course No : 08672
 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Favorite</title>
	<script type="text/javascript">
		function updateCount(urlId, e) {
			document.getElementById("hiddenCount").value=urlId;
			document.getElementById("updateCount").submit();
		}
	
	</script>
	<link href="mystyle.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div>
		<div>
			<c:forEach var="error" items="${errors}">
				<h3 style="color:red"> ${error} </h3>
			</c:forEach>
		</div>
		<div>
			<h4 style="color:green;"> ${successMessage} </h4>
		</div>
		
		<div id="favoritefields">
			<h2>Favorites for ${user.firstName} ${user.lastName}</h2>
			<h3>Please provide the complete url.</h3>
			<form action="favorite.do" method="post">
				<label for="url" class="label">
					URL:
				</label>
				<input name="url" value="" id="url" type="text" required style="margin-left: 4.1em;"><br>
				<label for="comment" class="label">
					Comment:
				</label>
				<input name="comment" value="" id="comment" type="text" required style="margin-left: 1.75em;"><br>
				<button name="action" type="submit" value="AddFavorite">Add Favorite</button><br>
			</form>
		</div>
			
		<div style=" margin-left: 10em;">
			<form action="favorite.do" method="post" id="updateCount">
				<input type="hidden" id="hiddenCount" name="favoriteId">
					<c:forEach var="favorite" items="${favoriteList}">
						<ul id="${favorite.userId} }" style="list-style: none;">
						<li><a target="_blank" href="${favorite.url}" onclick="updateCount(${favorite.favoriteId}, event)">${favorite.url}</a></li>
						<li>${favorite.comment}</li>
						<li>${favorite.clickCount} Click(s)</li>
					</ul>
					</c:forEach>
					
			</form>
		</div>
	</div>
	<jsp:include page="template-bottom.jsp" />
</body>
</html>