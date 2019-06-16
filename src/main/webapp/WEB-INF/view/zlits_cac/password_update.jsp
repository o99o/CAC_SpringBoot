<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib uri="/WEB-INF/privilege.tld"  prefix="privilege" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script>
</style>
</head>
<body>
<!-- url: cacInto/updatePassword.do -->
	<div>
		<div class="layui-form layui-inline" >
			<div class="layui-block layui-form-item">
				<label class="layui-form-label">用户名：</label>
				<div class="layui-input-inline">
					<input class="layui-input" disabled="disabled" name="id" id="userName" value="${currentUser.userName}" autocomplete="off">
				</div>
			</div>
			<div class="layui-block layui-form-item">
				<label class="layui-form-label">原密码：</label>
				<div class="layui-input-inline">
					<input class="layui-input" lay-verify="oldPassword" name="oldPassword" type="password"  name="id" id="userName" autocomplete="off">
				</div>
			</div>
			<div class="layui-block layui-form-item">
				<label class="layui-form-label">新密码：</label>
				<div class="layui-input-inline">
					<input class="layui-input" lay-verify="newPassword" name="newPassword" type="password"  name="id" id="userName" autocomplete="off">
				</div>
			</div>
			<div class="layui-block layui-form-item">
				<label class="layui-form-label">确认密码：</label>
				<div class="layui-input-inline">
					<input class="layui-input" lay-verify="newPassword2" name="newPassword2" type="password" name="id" id="userName" autocomplete="off">
				</div>
			</div>
			<div class="layui-block layui-form-item">
				<button class="layui-btn" lay-submit lay-filter="pwdUpdate">保存</button>
			</div>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/layui/src/layui.js" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/common/js/jquery-1.12.3.js" charset="utf-8"></script>
<script type="text/javascript">
layui.use('form', function(){
	var form = layui.form;

	//自定义验证规则
	  form.verify({
		//旧密码字符长度限制
		oldPassword: function(value){
			    //密码最大长度
			    var passwordMaxLength = 20;
		      if(strLengthValid(value,passwordMaxLength)){
		        return '密码长度应少于20个字符，请修改！';
		      }
		    },
		 //新密码字符长度限制
		newPassword: function(value){
			 //密码最大长度
		    var passwordMaxLength = 20;
	      if(strLengthValid(value,passwordMaxLength)){
	        return '密码长度应少于20个字符，请修改！';
	      }

	      if(""==value||null==value){
		        return '密码不能为空，请填写！';
		      }
	    },
	    //确认密码长度限制
	    newPassword2: function(value){
	    	 //密码最大长度
		    var passwordMaxLength = 20;
	      if(strLengthValid(value,passwordMaxLength)){
	        return '密码长度应少于20个字符，请修改！';
	      }
	    },
	  });
	
	form.on('submit(pwdUpdate)', function(data){

		  if(data.field.newPassword!=data.field.newPassword2){
				layer.alert('确认密码输入错误！');
				return false;
			}
			if(data.field.oldPassword!='${currentUser.password}'){
				layer.alert('原密码错误！');
				return false;
			}
			var newPassword = data.field.newPassword;
			 $.ajax({
			        type:'POST',
			        dataType: "json",
			        url: "${pageContext.request.contextPath}/cacInto/updatePassword",
			        data:{'newPassword' : newPassword},
			        success:function(data){
				        layer.alert("修改成功！");
			        }
			    })
		});

	
})

</script>
</html>