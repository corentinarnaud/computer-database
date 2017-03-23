<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>Computer Database</title>
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
			<a class="navbar-brand" href="/ComputerDatabase/dashboard">
				Application - Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${ nbComputer } Computersfound</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="/ComputerDatabase/addComputer">Add Computer</a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
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
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${ listComputer }">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${ computer.getId() }"></td>
							<td><a href="editComputer" onclick="">${ computer.getName() }</a></td>
							<td>${ computer.getIntroducedFormated() }</td>
							<td>${ computer.getDiscontinuedFormated() }</td>
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
						<li><a href="/ComputerDatabase/dashboard?page=${ 1 }"
							aria-label="Begin"> <span aria-hidden="true">&laquo;</span>
						</a></li>
						<li><a
							href="/ComputerDatabase/dashboard?page=${ currentPage - 1}"
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
										<li><a href="/ComputerDatabase/dashboard?page=${ i }">${ i }</a></li>
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
										<li><a href="/ComputerDatabase/dashboard?page=${ i }">${ i }</a></li>
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
										<li><a href="/ComputerDatabase/dashboard?page=${ i }">${ i }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					<c:if test="${ currentPage < maxPage }">
						<li><a
							href="/ComputerDatabase/dashboard?page=${ currentPage + 1}"
							aria-label="Next"> <span aria-hidden="true">&gt;</span>
						</a></li>
						<li><a href="/ComputerDatabase/dashboard?page=${ maxPage }"
							aria-label="End"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>
				</ul>
				<div class="pull-right">
					<ul class="pagination">
						<li><a href="/ComputerDatabase/dashboard?elements=10">10</a></li>
						<li><a href="/ComputerDatabase/dashboard?elements=25">25</a></li>
						<li><a href="/ComputerDatabase/dashboard?elements=50">50</a></li>
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