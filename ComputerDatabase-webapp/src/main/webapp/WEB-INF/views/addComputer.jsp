<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored="false"%>
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
            <a class="navbar-brand" href="/ComputerDatabase/dashboard"> <spring:message code="application"/> - <spring:message code="title"/> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="add"/></h1>
                    <form action="addComputer" method="POST">
                        <fieldset>
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <div class="form-group">
                                <label for="computerName"><spring:message code="name"/></label>
                                <input name="computerName" type="text" class="form-control" 
                                id="computerName" placeholder="Computer name" 
     							maxlength="550" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="introduced"/></label>
                                <input name="introduced" type="date" class="form-control" id="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="discontinued"/></label>
                                <input name="discontinued" type="date" class="form-control" id="discontinued" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="company"/></label>
                                <select name="companyId" class="form-control" id="companyId" >
                                	<option value="0">--</option>
                                	<c:forEach var="company" items="${ listCompany }">
                                    	<option value="${ company.getId() }">${ company.getName() }</option>
                                    </c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            <spring:message code="or"/>
                            <a href="dashboard" class="btn btn-default"><spring:message code="cancel"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="/ComputerDatabase/js/validation.js"></script>
</body>
</html>