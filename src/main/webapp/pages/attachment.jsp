<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="../source/static/bootstrap/css/bootstrap.css"
	rel="stylesheet" />
<link href="../source/static/bootstrap/css/bootstrap-theme.css"
	rel="stylesheet" />
<script src="../source/static/bootstrap/js/bootstrap.js"></script>
<table width="99%">
	<c:forEach items="${attachments}" varStatus="ws" var="attachment">
		<tr>
			<td>${attachment.attachmentType}</td>
			<td>${attachment.name}</td>
			<td class="btn btn-mini"><a href="../upload${attachment.url}" target="_blank">下载</a></td>
		</tr>
	</c:forEach>
</table>