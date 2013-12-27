<%--
  Created by IntelliJ IDEA.
  User: justinyang
  Date: 13-12-25
  Time: PM7:00
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form action="/document/commitupload" method="post">
<c:forEach items="${documentExtraPropertyWrappers}" varStatus="ws" var="documentExtraPropertyWrapper">
    <div class="form-group">
    <label for="documentExtraPropertyWrappers[${ws.index}].extraPropertyId" class="col-sm-2 control-label">
        ${documentExtraPropertyWrapper.extraPropertyName}
    </label>
    <div class="col-sm-10">
        <input name="documentExtraPropertyWrappers[${ws.index}].extraPropertyId" value="${documentExtraPropertyWrapper.extraPropertyId}" hidden="hidden"/>
        <input class="form-control" placeholder="${documentExtraPropertyWrapper.extraPropertyName}" name="documentExtraPropertyWrappers[${ws.index}].extraPropertyValue" />
    </div>
    </div>
</c:forEach>
</form>