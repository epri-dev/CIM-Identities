<%-- 
    Document   : CIMIdentitiesDataEntry
    Created on : Jun 7, 2017, 11:51:27 AM
    Author     : pdlo003
--%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enter Data for CIM</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script>  
        $('.enter_link').click(function() { 
        $(this).parent().fadeOut(500);
        });
        </script>
        <style>
            #splashscreen {
                position:absolute;
                top:0;
                left:0;
                bottom:0;
                width:100%;
                background-color:white;
            }
        </style>  
    </head>
    <body>
    <div id="splashscreen">
    <h2>Welcome!</h2>
    <img src="splash.JPG" />
    <br><br><br><br>
    <a href="DataEntry.jsp" class="enter_link">Accept</a>
</div>
    </body>
</html>


