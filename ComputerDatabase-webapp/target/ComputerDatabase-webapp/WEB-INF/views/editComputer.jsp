<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tags.tld"%>
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
            <a class="navbar-brand" href="dashboard"> <spring:message code="application"/> - <spring:message code="title"/> </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${ computer.getId() }
                    </div>
                    <h1><spring:message code="editComputer"/></h1>

                    <form action="editComputer" method="POST">
                        <input name="id" type="hidden" value="${ computer.getId() }" id="id"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="name"/></label>
                                <input name="computerName" type="text" class="form-control" id="computerName" value="${ computer.getName() }" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="introduced"/></label>
                                <input name="introduced" type="date" class="form-control" id="introduced" 
                                	value="${ computer.getIntroduced() }"
                                	type="date" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="discontinued"/></label>
                                <input name="discontinued" type="date" class="form-control" id="discontinued" 
                                	value="${ computer.getDiscontinued() }"
                                	placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="company"/></label>
                                <select name="companyId" class="form-control" id="companyId" >
                                	<option value="0">--</option>
                                    <c:forEach var="company" items="${ listCompany }">
                                    <option value="${ company.getId() }" 
                                    	<c:if test="${ company.getId() == computer.company.getId()}">selected</c:if>
                                    >${ company.getName() }</option>
                                    
                                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
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