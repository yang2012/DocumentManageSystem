<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form action="/document/commitupload" method="post">
<c:forEach items="${extraPropertyWrappers}" varStatus="ws" var="extraPropertyWrapper">
    <div class="form-group">
    <label for="extraPropertyWrappers[${ws.index}].extraPropertyId" class="col-sm-2 control-label">
        ${extraPropertyWrapper.extraPropertyName}
    </label>
    <div class="col-sm-10">
        <input name="extraPropertyWrappers[${ws.index}].extraPropertyId" value="${extraPropertyWrapper.extraPropertyId}" hidden="hidden"/>
        <input class="form-control" placeholder="${extraPropertyWrapper.extraPropertyName}" name="extraPropertyWrappers[${ws.index}].extraPropertyValue" />
    </div>
    </div>
</c:forEach>
</form>