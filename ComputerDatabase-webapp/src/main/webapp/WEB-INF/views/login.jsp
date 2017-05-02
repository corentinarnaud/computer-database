<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="title"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="/ComputerDatabase/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="/ComputerDatabase/css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="/ComputerDatabase/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"><spring:message code="application"/> - <spring:message code="title"/></a>
		</div>
	</header>
	    <section id="main">
	<div class="container">
        <form name="f" action="/login" method="POST">               
            <fieldset>
                <legend><spring:message code="login"/></legend>
                <c:if test="${ error }">
                <div class="alert alert-error">    
                    <spring:message code="invalid"/>
                </div>
                </c:if>
                <c:if test="${ logout }">
                <div class="alert alert-success"> 
                    You have been logged out.
                </div>
                </c:if>
                <label for="username"><spring:message code="username"/></label>
                <input type="text" id="username" name="username"/>        
                <label for="password"><spring:message code="password"/></label>
                <input type="password" id="password" name="password"/>    
                <div class="form-actions">
                    <button type="submit" class="btn"><spring:message code="connection"/></button>
                </div>
            </fieldset>
        </form>
    </div>
    </section>
</body>
</html>