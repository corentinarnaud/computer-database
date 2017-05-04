<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<form name="f" action="#" method="POST">
				<fieldset>
					<legend>
						<spring:message code="register" />
					</legend>
					<c:if test="${param.error ne null}">
						<div class="alert alert-error">
							<spring:message code="exist" />
						</div>
					</c:if>
					<label for="username"><spring:message code="username" /></label> 
					<input type="text" id="username" name="${user.name}" /> 
					<label for="password"><spring:message code="password" /></label>
					<input type="password" id="password" name="${ user.password }" />
					<label for="repeatPassword"><spring:message code="repeatPassword" /></label>
					<input type="password" id="repeatPassword" name="${ user.matchingPassword }" />
					<div class="form-actions">
						<button type="submit" class="btn">
							<spring:message code="register" />
						</button>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</fieldset>
			</form>
		</div>
	</section>
</body>
</html>