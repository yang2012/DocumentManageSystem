<%--
  Created by IntelliJ IDEA.
  User: justinyang
  Date: 13-12-26
  Time: PM7:00
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form action="/evaluation/commiteval" method="post" id="detailEvaluationForm" role="form" class="form-horizontal">
    <input name="documentId" value="${documentId}" hidden="hidden"/>
    <input name="evaluation.type" value="1" hidden="hidden"/>
    <div>
        <label for="point">Point:</label>
        <input name="evaluation.point" id="point"/>
    </div>
    <textarea name="evaluation.content" placeholder="content" rows="3"/>
    <c:forEach items="${evaluationExtraPropertyWrappers}" varStatus="ws" var="evaluationExtraPropertyWrapper">
        <div class="form-group">
            <div class="col-sm-10">
                <input name="evaluationExtraPropertyWrappers[${ws.index}].extraPropertyId" value="${evaluationExtraPropertyWrapper.extraPropertyId}" hidden="hidden"/>
                <textarea class="form-control" rows="3" placeholder="${evaluationExtraPropertyWrapper.extraPropertyName}" name="evaluationExtraPropertyWrappers[${ws.index}].extraPropertyValue" />
            </div>
        </div>
    </c:forEach>
    <div class=" col-xs-4 pull-right">
        <button type="button" class="btn btn-default">保存为草稿</button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button type="submit" class="btn btn-primary">发布评论</button>
    </div>
</form>
