<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<title>Insert title here</title>
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/bootstrap.min.css" ></link>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light px-5">
	  <a class="navbar-brand" href="#">Navbar</a>
	
	  <div class="collapse navbar-collapse">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item active">
	        <a class="nav-link" href="#">Users</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#">Categories</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#">Products</a>
	      </li>
	    </ul>
	  </div>
	</nav>

	<div class="mt-5 col-10 offset-1">
		<form:form
			modelAttribute="user"
			method="POST"
			action="${ pageContext.request.contextPath }/admin/users/update/${user.id}">
			<input type="hidden" name="_method" value="put" />
			<div class="form-group mt-3">
				<label for="name">Name</label>
			    <form:input path="username" class="form-control" autocomplete="off" />
				<form:errors path="username" element="span" cssClass="text-danger" />
			</div>
			<div class="form-group mt-3">
				<label for="email">Email</label>
			    <form:input path="email" class="form-control" id="email" name="email" autocomplete="off" />
				<form:errors path="email" element="span" cssClass="text-danger" />
			</div>
			<div class="form-group mt-3">
			    <label for="password">Password</label>
			    
			    <form:hidden path="password" />
			    
			    <input type="password" value="*********" class="form-control" readonly/>
			    <form:errors path="password" element="span" cssClass="text-danger" />
			</div>
			<div class="form-group mt-3">
				<label for="admin">Tài khoản</label>
				<form:select path="admin" id="admin" class="form-control">
					<form:option value="1">Admin</form:option>
					<form:option value="0">Member</form:option>
				</form:select>
				<form:errors path="admin" element="span" cssClass="text-danger" />
			</div>
			<div class="form-group mt-3">
				<label for="photo">Image</label>
			    <input type="file" class="form-control" id="photo" name="photo">
			</div>
			<div class="form-group mt-3">
				<label for="status">Trạng thái</label>
				<form:select path="activated" id="activated" class="form-control">
					<form:option value="1">Đang hoạt động</form:option>
					<form:option value="0">Vô hiệu hóa</form:option>
				</form:select>
				<form:errors path="activated" element="span" cssClass="text-danger" />
			</div>
			<div class="form-group mt-3">
				<button class="btn btn-primary">Submit</button>
				<button type="reset" class="btn btn-danger">Clear</button>
			</div>
		</form:form>
	</div>
</body>
</html>