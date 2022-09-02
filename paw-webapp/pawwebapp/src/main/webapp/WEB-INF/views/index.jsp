<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
    <head>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link rel="stylesheet" href="<c:url value="/resources/css/materialize.css"/>" type="text/css"
              media="screen,projection">
        <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" type="text/css"
              media="screen,projection">
        <link href='https://fonts.googleapis.com/css?family=Manrope' rel='stylesheet'>

        <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>

        <title>MainPage</title>
    </head>
    <body>
        <%@ include file="navbar.jsp"%>
        <div class="row">
            <div class="column column1">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eu efficitur metus.
                Interdum et malesuada fames ac ante ipsum primis in faucibus. Duis non eros dignissim, s
                agittis velit id, tincidunt nulla. Vivamus laoreet elit ligula, id consequat turpis sagittis ac.
                Morbi mi diam, faucibus pharetra augue at, hendrerit gravida arcu. Fusce quis fringilla purus,
                eu bibendum nibh. Donec non neque eget magna vehicula faucibus ac sed leo. Pellentesque fermentum
                enim et elit maximus, vitae elementum ipsum imperdiet. Pellentesque pellentesque metus quam, nec
                porta nulla pharetra in. Duis mauris nisl, imperdiet ac urna vitae, volutpat venenatis odio. Sed
                ornare erat ut neque tincidunt rutrum. Quisque interdum dolor vitae fringilla mollis. Quisque vel
                hendrerit odio
                <div style="display:flex; justify-content:center; margin-top:3vh;">
                    <a class="waves-effect waves-light btn standard-button" href="">Comencemos</a>
                </div>
            </div>
            <div class="column column2">
                <img style="width:60%; position:relative;" src="<c:url value="/resources/images/imagenLanding.png"/>" alt="Sustainability for all!">
            </div>
            <span class="stretch"></span>
        </div>

        <%@ include file="footer.jsp"%>
    </body>
</html>