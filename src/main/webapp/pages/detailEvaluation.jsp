<%--
  Created by IntelliJ IDEA.
  User: justinyang
  Date: 13-12-26
  Time: PM7:00
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${evaluationExtraPropertyWrappers}" varStatus="ws" var="evaluationExtraPropertyWrapper">
    <div class="form-group">
        <div>
            <input name="evaluationExtraPropertyWrappers[${ws.index}].extraPropertyId" value="${evaluationExtraPropertyWrapper.extraPropertyId}" hidden="hidden"/>
            <textarea class="form-control" rows="3" placeholder="${evaluationExtraPropertyWrapper.extraPropertyName}" name="evaluationExtraPropertyWrappers[${ws.index}].extraPropertyValue" />
        </div>
    </div>
</c:forEach>
