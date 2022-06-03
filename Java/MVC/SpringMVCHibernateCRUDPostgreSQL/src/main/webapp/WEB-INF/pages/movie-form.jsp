<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <!--
        <link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.1/themes/base/jquery-ui.css" rel="stylesheet" />
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.1/jquery-ui.min.js"></script>
        -->

        <!--
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        -->

        <!--
        <link href="//netdna.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//netdna.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
        -->

        <link href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap-4.1.3-dist/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css" />
        <script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery/jQuery-3.x/jquery-3.3.1.min.js"></script>

        <title>New/Edit Movie</title>
    </head>
    <body>
        <div class="container">
            <div class="col-md-5">
                <div class="form-area">
                    <form:form id="inputForm" action="saveMovie" method="post" modelAttribute="movie">
                        <form:hidden path="id"/>
                        <br style="clear:both">
                        <h4 style="margin-bottom: 25px; text-align: center;">New/Edit Movie</h4>
                        <p>Please enter values in all the fields.</p>
    			<div class="form-group">
                            <label for="title">Title</label>
			    <form:input type="text" path="title" class="form-control" id="title" name="name" placeholder="Title" required="true" />
			</div>
			<div class="form-group">
                            <label for="release_date">Release Date</label>
			    <form:input type="date" path="release_date" class="form-control" id="release_date" name="release_date" placeholder="mm/dd/yyyy" required="true" />
			</div>
			<div class="form-group">
                            <label for="price">Price</label>
			    <form:input type="number" step="0.01" min="0" path="price" class="form-control" id="price" name="price" placeholder="Price" required="true" />
			</div>
			<div class="form-group">
                            <label for="genre">Genre</label>
			    <form:input type="text" path="genre" class="form-control" id="genre" name="genre" placeholder="Genre" required="true" />
			</div>
        		<div class="form-group">
                            <label for="rating">Rating</label>
			    <form:input type="text" path="rating" class="form-control" id="rating" name="rating" placeholder="Rating" required="true" />
		        </div>           
                        <input type="submit" id="submit name="submit" value="Save" class="btn btn-primary pull-right">
                        </form:form>
                    <span></span>
                   <p>Revert to movies list&nbsp;<a href="./">here</a></p> 
                </div>
            </div>
        </div>
    </body>
</html>