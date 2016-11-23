<!-- 
/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
 -->
<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Provide the url which you want to remove.
</p>
<c:forEach var="error" items="${errors}">
	<h3 style="color:red"> ${error} </h3>
</c:forEach>
<h3 style="color:green;"> <%=request.getAttribute("successMessage")%></h3>
<p>
	<form method="POST" action="deletefavorite.do">
		<table>
			<tr>
				<td> URL: </td>
				<td><input type="text" name="favoriteUrl" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="action" value="Delete Favorite"/>
				</td>
			</tr>
		</table>
	</form>
</p>

<jsp:include page="template-bottom.jsp" />
