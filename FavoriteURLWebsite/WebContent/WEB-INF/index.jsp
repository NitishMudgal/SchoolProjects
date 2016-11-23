<!-- 
/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="template-top.jsp" />

<html>
    <head>
        <title>HW4 -- Index Page</title>
        <script type="text/javascript">
			function updateCount(favoriteId, userId, e) {
				document.getElementById("hiddenCount").value=favoriteId;
				document.getElementById("hiddenUserId").value=userId;
				document.getElementById("updateCount").submit();
			}	
		</script>
    </head>
    
	<body>	
		<h2>Please select a User to check his favorite urls.</h2>		
		<c:forEach var="error" items="${errors}">
			<h3 style="color:red"> ${error} </h3>
		</c:forEach>
		<c:if test="${empty favoriteList && not empty userName}">
			<h3>There are no favorite urls for ${userName}.</h3>
		</c:if>		
		<c:if test="${not empty favoriteList}">
			<h3>Favorite urls for ${userName}.</h3>
			<div style=" margin-left: 10em;">
			<form action="favorite.do" method="post" id="updateCount">
				<input type="hidden" id="hiddenCount" name="favoriteId">
				<input type="hidden" id="hiddenUserId" name="userId">
					<c:forEach var="favorite" items="${favoriteList}">
						<ul id="${favorite.userId}" style="list-style: none;">
						<li><a target="_blank"  href="${favorite.url}" onclick="updateCount(${favorite.favoriteId}, ${favorite.userId}, event)">${favorite.url}</a></li>
						<li>${favorite.comment}</li>
						<li>${favorite.clickCount} Click(s)</li>
					</ul>
					</c:forEach>					
			</form>
		</div>
		</c:if>
		<jsp:include page="template-bottom.jsp" />
	</body>
</html>