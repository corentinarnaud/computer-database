<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/ComputerDatabase/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="/ComputerDatabase/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="/ComputerDatabase/css/main.css" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><spring:message
					code="application" /> - <spring:message code="title" /></a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<form:form name="f" action="#" method="POST" commandName="user">
				<fieldset>
					<table>
						<tr>
							<td><spring:message code="register" /></td>
							<td><c:if test="${param.error ne null}">
									<div class="alert alert-error">
										<spring:message code="exist" />
									</div>
								</c:if></td>
						</tr>
						<tr>
							<td><spring:message code="username" /></td>
							<td><form:input type="text" id="username" path="name" /></td>
							<td><form:errors path="name" cssClass="error" element="div"/></td>
						</tr>
						<tr>
							<td><spring:message code="password" /></td>
							<td><form:input type="password" id="password"
									path="password" /></td>
							<td><form:errors path="password" cssClass="error" element="div"/></td>
						</tr>
						<tr>
							<td><spring:message code="repeatPassword" /></td>
							<td><form:input type="password" id="repeatPassword"
									path="matchingPassword" /></td>
							<td><form:errors cssClass="error" element="div"/>
							</td>
						</tr>
						<tr>
							<td>
								<button type="submit" class="btn">
									<spring:message code="register" />
								</button>
							</td>
						</tr>
					</table>
					<!-- <input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" /> -->
				</fieldset>
			</form:form>
		</div>
	</section>
</body>
</html>