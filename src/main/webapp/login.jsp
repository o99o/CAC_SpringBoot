<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="/js/cac-all.js"></script>
<link rel="stylesheet" type="text/css"  href="/style/alogin.css" />
<link rel="stylesheet" type="text/css" href="/style/main.css">
<title>用户登录</title>
<!-- 使登陆页保持为最大窗口 -->
<script type="text/javascript">  
    if (window != top)   
    top.location.href = location.href;   
</script>
</head>
<body style="overflow:hidden; background:url(/images/login/login_background.jpg) center no-repeat">
	<div style="width:100%;height:100%;background-position: center; background-attachment: fixed;background-size:100% 100%;">
		<div style="position: absolute;width:510px;height:225px;left:62%;top:50%; margin-left:-580px;margin-top:-130px;border:0px solid #00F;">
			<form id="form1" name="form1" action="${pageContext.request.contextPath}/cacInto/login" method="post">
				<div class="MAIN">
					<ul>
						<li class="topD"> 
							<ul class="login">
								<li>
									<span style="">
										<input id="userName" name="userName" type="text" class="txt" value="${userName }" /> 
									</span>
								</li>
								<li style="marigin-top:50px;">
									<span style="">
										<input id="password" name="password" type="password" class="txt" value="${password }" onkeydown= "if(event.keyCode==13) form1.submit()"/>
									</span>
								</li>
								<%-- <li>
									<span style="">
										<input type="text" value="${imageCode }" name="imageCode"  class="txtCode" id="imageCode" size="10" onkeydown= "if(event.keyCode==13)form1.submit()"/>&nbsp;
										
											<img onclick="javascript:loadimage();" title="换一张试试" name="randImage" id="randImage" src="${pageContext.request.contextPath}/images/image.jsp" width="60" height="25" border="1" align="absmiddle">
										
									</span>
									<div id="changeValidPicture" onclick="javascript:loadimage();" ><a href="#">换一张</a></div>
								</li> --%>
							</ul>
						</li>
						<li class="middle_C">
							<span class="btn"> 
								<input type="submit" id="submit" value="登录" />
							</span>
						</li>
					</ul>
				</div>	
				<font id="errorInfo" color="red">${error }</font>
			</form>
			<input onclick="reset()" type="button"  id="reset"  value="清除" />
		</div>
	</div>
</body>
</html>