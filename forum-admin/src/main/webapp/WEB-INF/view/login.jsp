<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="Dashboard">
  <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
  <title>Dashio - Bootstrap Admin Template</title>
  <c:import url="${pageContext.request.contextPath}/WEB-INF/fragment/imports.jsp"></c:import>
</head>

<body>
  <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
  <div id="login-page">
    <div class="container">
      <form class="form-login" method="post" action="${pageContext.request.contextPath}/us?action=doLogin">
        <h2 class="form-login-heading">sign in now</h2>
        <div class="login-wrap">
          <input type="email" name="email" class="form-control" placeholder="E-Mail" autofocus>
          <br>
          <input type="password" name="password" class="form-control" placeholder="Password">
          <label class="checkbox">
            <span class="pull-right">
            </span>
            </label>
          <button class="btn btn-theme btn-block" type="submit"><i class="fa fa-lock"></i> SIGN IN</button>
        </div>
      </form>
    </div>
  </div>
  <!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jquery.backstretch.min.js"></script>
  <script>
    $.backstretch("${pageContext.request.contextPath}/resources/img/login-bg.jpg", {
      speed: 500
    });
  </script>
</body>

</html>
