<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="topicId" hidden value="${topic.id}">
<input type="text" id="title" value="${topic.title}">
<br>
<textarea id="desc" cols="30" rows="10" >${topic.description}</textarea>
<br>
<p>User name: ${topic.user.firstname} ${topic.user.lastname}</p>
<p>Share date: ${topic.shareDate}</p>

