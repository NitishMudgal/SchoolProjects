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
        <title>HW4 -- Login Page</title>
    </head>
    
	<body>
	
		<h2>HW4 Login</h2>
		
		<c:forEach var="error" items="${errors}">
			<h3 style="color:red"> ${error} </h3>
		</c:forEach>
		<h2>Already have an account?</h2>
		<form action="login.do" method="POST">
		    <table>
		        <tr>
		            <td style="font-size: x-large">Email Address:</td>
		            <td>
		                <input type="text" name="emailAddress" value="${loginForm.emailAddress}" />
		            </td>
		        </tr>
		        <tr>
		            <td style="font-size: x-large">Password:</td>
		            <td><input type="password" name="password" /></td>
		        </tr>
		        <tr>
		            <td colspan="2" align="left">
		                <input type="submit" name="action" value="Login" />
		            </td>
		        </tr>
			</table>
		</form>
		<h2>New User?</h2>
		<form action="login.do" method="POST">
		    <table>
		        <tr>
		            <td style="font-size: x-large">Email Address:</td>
		            <td>
		                <input type="text" name="emailAddress" value="${registerForm.emailAddress}" />
		            </td>
		        </tr>
		        <tr>
		            <td style="font-size: x-large">First Name:</td>
		            <td>
		            	<input type="text" name="firstName" value="${registerForm.firstName}" />
		            </td>
		        </tr>
		        <tr>
		            <td style="font-size: x-large">Last Name:</td>
		            <td><input type="text" name="lastName" value="${registerForm.lastName}" /></td>
		        </tr>
		        <tr>
		            <td style="font-size: x-large">Password:</td>
		            <td><input type="password" name="password" /></td>
		        </tr>
		        <tr>
		            <td style="font-size: x-large">Confirm Password:</td>
		            <td><input type="password" name="confirmPassword" /></td>
		        </tr>
		        <tr>
		            <td colspan="2" align="left">
		                <input type="submit" name="action" value="Register" />
		            </td>
		        </tr>
			</table>
		</form>
		<jsp:include page="template-bottom.jsp" />
	</body>
</html>