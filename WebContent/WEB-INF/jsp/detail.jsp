<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 引入jstl 所有相同的东西都可以这样引用，不用重复写了 -->
<%@ include file="common/tag.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>秒杀详情页</title>
	
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="../bootstrap/js/jquery/2.0.0/jquery.min.js"></script>
	<link href="../bootstrap/css/bootstrap/3.3.6/bootstrap.min.css"
		rel="stylesheet">
	<script src="../bootstrap/js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<script src="../bootstrap/js/myjs.js"></script>
	<link href="../bootstrap/css/mycss.css" rel="stylesheet"> 
	
</head>
<body>

	<div class = "container">
		<div class = "panel panel-info">
			<div class = "panel-heading text-center">
				<h1>${seckill.name}</h1>
			</div>
			<div class = "panel-body">
				<h2 class = "text-danger">
					<!-- 显示time图标 -->
					<span class="glyphicon glyphicon-time"></span>
					<!-- 展示倒计时 -->
					<span class="glyphicon" id="seckillBox"></span>
				</h2>
			</div>
		</div>
	</div>
	
	<!-- 登录弹出层，输入电话 -->
	<div id="killPhoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>秒杀电话：
					</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="killphone" id="killphoneKey"
								placeholder="填手机号^O^" class="form-control" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<span id="killphoneMessage" class="glyphicon"></span>
					<button type="button" id="killPhoneBtn" class="btn btn-success">
						<span class="glyphicon glyphicon-phone"></span> Submit
					</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- jQuery cookie操作插件 -->
	<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
	<!-- jQury countDonw倒计时插件  -->
	<script src="https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.js"></script>
	<script type="text/javascript">
		$(function(){
			//使用EL表达式传入参数
			seckill.detail.init({
				seckillId : ${seckill.seckillId},
				startTime : ${seckill.startTime.time},//毫秒
				endTime : ${seckill.endTime.time}
			});
		});
	</script>
	
</body>
</html>



















