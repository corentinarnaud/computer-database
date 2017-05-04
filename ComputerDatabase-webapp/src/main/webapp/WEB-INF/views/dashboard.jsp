<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tags.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title><spring:message code="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
			<a class="navbar-brand" href="/ComputerDatabase/dashboard"> <spring:message
					code="application" /> - <spring:message code="title" /></a>
		<form action="logout" method="post">
			<input type="submit" value="Log out" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
		<form action="registration" method="get">
			<input type="submit" value="Register" class="btn btn-primary"/>
		</form>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${ nbComputer }
				<spring:message code="found" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> 
							<input type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="/ComputerDatabase/addComputer"><spring:message
							code="add" /></a> <a class="btn btn-default" id="editComputer"
						href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="delete"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a
							href="<h:link page="${ 1 }" nbElements="${ elements }" 
								search="${ search }" orderBy="1"/>"><spring:message
									code="name" /></a></th>
						<th><a
							href="<h:link page="${ 1 }" nbElements="${ elements }" 
								search="${ search }" orderBy="2"/>"><spring:message
									code="introduced" /></a></th>
						<!-- Table header for Discontinued Date -->
						<th><a
							href="<h:link page="${ 1 }" nbElements="${ elements }" 
								search="${ search }" orderBy="3"/>"><spring:message
									code="discontinued" /></a></th>
						<!-- Table header for Company -->
						<th><a
							href="<h:link page="${ 1 }" nbElements="${ elements }" 
								search="${ search }" orderBy="4"/>"><spring:message
									code="company" /></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<spring:message code="date" var="date" />
				<tbody id="results">
					<c:forEach var="computer" items="${ listComputer }">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${ computer.getId() }"></td>
							<td><a href="editComputer?id=${ computer.getId() }"
								onclick="">${ computer.getName() }</a></td>
							<td><fmt:parseDate value="${ computer.getIntroduced() }"
									pattern="yyyy-MM-dd" var="introduce" /> <fmt:formatDate
									pattern="${ date }" value="${ introduce }" /></td>
							<td><fmt:parseDate value="${ computer.getDiscontinued() }"
									pattern="yyyy-MM-dd" var="discontinue" /> <fmt:formatDate
									pattern="${ date }" value="${ discontinue }" /></td>
							<td>${ computer.getCompany().getName() }</td>

						</tr>
					</c:forEach>


				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container">
			<div class="container text-center">
				<ul class="pagination">
					<c:if test="${ currentPage >1 }">
						<li><a id="firstpage"
							href=<h:link page="${ 1 }" nbElements="${ elements }" search="${ search }" orderBy="${ order }"/>
							aria-label="Begin"> <span aria-hidden="true">&laquo;</span>
						</a></li>
						<li><a id="prev"
							href=<h:link page="${ currentPage - 1}" nbElements="${ elements }" search="${ search }" orderBy="${ order }"/>
							aria-label="Previous"> <span aria-hidden="true">&lt;</span>
						</a></li>
					</c:if>
					<c:choose>
						<c:when test="${ currentPage <= 3 }">
							<c:forEach var="i" begin="1" end="${ maxPage < 5 ? maxPage : 5 }">
								<c:choose>
									<c:when test="${ i == currentPage }">
										<li><a style="color: #FF0000">${ i }</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href=<h:link page="${ i }" nbElements="${ elements }" search="${ search }" orderBy="${ order }"/>>${ i }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:when test="${ currentPage > maxPage-3 }">
							<c:forEach var="i" begin="${ maxPage - 4 }" end="${ maxPage }">
								<c:choose>
									<c:when test="${ i == currentPage }">
										<li><a style="color: #FF0000">${ i }</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href=<h:link page="${ i }" nbElements="${ elements }" search="${ search }" orderBy="${ order }"/>>${ i }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="i" begin="${ currentPage - 2 }"
								end="${ currentPage + 2 }">
								<c:choose>
									<c:when test="${ i == currentPage }">
										<li><a style="color: #FF0000">${ i }</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href=<h:link page="${ i }" nbElements="${ elements }" search="${ search }" orderBy="${ order }"/>>${ i }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					<c:if test="${ currentPage < maxPage }">
						<li><a id="next"
							href=<h:link page="${ currentPage + 1}" nbElements="${ elements }" search="${ search }" orderBy="${ order }"/>
							aria-label="Next"> <span aria-hidden="true">&gt;</span>
						</a></li>
						<li><a id="lastpage"
							href=<h:link page="${ maxPage }" nbElements="${ elements }" search="${ search }" orderBy="${ order }"/>
							aria-label="End"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>
				</ul>
				<div class="pull-right">
					<ul class="pagination">
						<li><a id="a1"
							href=<h:link page="${ currentPage }" nbElements="10" search="${ search }"/>>10</a></li>
						<li><a id="a2"
							href=<h:link page="${ currentPage }" nbElements="50" search="${ search }"/>>50</a></li>
						<li><a id="a3"
							href=<h:link page="${ currentPage }" nbElements="100" search="${ search }"/>>100</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>
	<script src="/ComputerDatabase/js/jquery.min.js"></script>
	<script src="/ComputerDatabase/js/bootstrap.min.js"></script>
	<script src="/ComputerDatabase/js/dashboard.js"></script>

</body>
</html>