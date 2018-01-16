<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 引入jstl 所有相同的东西都可以这样引用，不用重复写了 -->
<%@ include file="common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>秒杀列表页</title>
	<!-- 静态包含  所有相同的东西都可以这样引用，不用重复写了 -->
	<%@ include file="common/head.jsp" %>
</head>
<body>

	<div class = "container">
		<div class = "panel panel-info"> <!-- panel-primary panel-info -->
			<div class = "panel-heading text-center">
				<h2>秒杀列表</h2>
			</div>
			<div class = "panel-body">
				<table class = "table table-bordered table-hover">
					<thead>
						<tr>
							<th class="col-md-2">名称</th>
							<th class="col-md-1">库存</th>
							<th class="col-md-2">开始时间</th>
							<th class="col-md-2">结束时间</th>
							<th class="col-md-2">创建时间</th>
							<th class="col-md-1">详情页</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sk" items="${list }">
							<tr>
								<td>${sk.name }</td>
								<td>${sk.number }</td>
								<td>
									<fmt:formatDate value="${sk.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<fmt:formatDate value="${sk.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<fmt:formatDate value="${sk.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<a class = "btn btn-info" href = "/seckill/${sk.seckillId }/detail" target="_blank">link</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

</body>
</html>



















