<%-- 
    Document   : error, shows the error page if picture is not available
    Created on : Feb 12, 2016, 6:13:57 PM
    Author     : nmudgal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error 404</title>
    </head>
    <body>
        <div class="container">
            <div class="row-header">
                <h1>SORRY!!! We Screwed Up</h1>
                <h2>Can't Find any pictures to display.<br>Please try after sometime or go to:</h2>
            </div>
            <div class="row-content">
                <div class="col-sm-6 col-sm-offset-3">
                    <a href="http://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/">
                        <h1 class="text-capitalize text-primary">Smithsonian Migratory Bird Center</h1>
                    </a>
                </div>
            </div>
            
        </div>
        
    </body>
</html>
