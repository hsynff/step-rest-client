<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Favicons -->
<link href="${pageContext.request.contextPath}/resources/img/favicon.png" rel="icon">
<link href="${pageContext.request.contextPath}/resources/img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Font awesome -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">

<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!--external css-->
<link href="${pageContext.request.contextPath}/resources/lib/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style-responsive.css" rel="stylesheet">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script
        src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"></script>

<!-- js placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/resources/lib/bootstrap/js/bootstrap.min.js"></script>
<script class="include" type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jquery.dcjqaccordion.2.7.js"></script>
<script src="${pageContext.request.contextPath}/resources/lib/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/lib/jquery.nicescroll.js" type="text/javascript"></script>
<!--common script for all pages-->
<script src="${pageContext.request.contextPath}/resources/lib/common-scripts.js"></script>
<!--script for this page-->


<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
    $(document).ready(function() {

        <c:if test="${message ne null}">
            alert('${message}');
        </c:if>

        $('#example').DataTable();
    } );
</script>


<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/topic.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/user.js"></script>