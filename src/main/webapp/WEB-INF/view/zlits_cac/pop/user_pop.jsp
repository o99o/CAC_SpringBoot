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
<title>新增修改用户</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script>
</head>
<body>
	
<script type="text/javascript">
	//选择角色弹层
	var userIdOriginal;
	var User = {};
	function selectRolePop(){
		//加载角色table数据
		//将table弹层
		layui.use("layer", function(){
			var layer = layui.layer;
			layer.open({
	        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	            type:2,
	            title:"选择角色",
	            area: ['500px','430px'],
	            offset: [ //为了演示，随机坐标
	                0,0
	              ],
	            shade: 0,
			    content:['${pageContext.request.contextPath}/cacUser/userRolePop', 'no'] //iframe的url，no代表不显示滚动条
	        });
		});
	}

	layui.use('form', function(){
		
		var form = layui.form;

		 //自定义验证规则
		  form.verify({

			  userName: function(value){
				    //用户名最大长度
				  var userNameMaxLength = 20;
			      if(strLengthValid(value,userNameMaxLength)){
			        return '用户名长度应少于20字符，请修改！';
			      }
			      if(""==value||null==value){
			    	  return '用户名不能为空，请输入！';
				      }
			    },
		    password: function(value){
			    //密码最大长度
			    var passwordMaxLength = 20;
		      if(strLengthValid(value,passwordMaxLength)){
		        return '密码长度应少于20字符，请修改！';
		      }
		      if(""==value||null==value){
		    	  return '密码不能为空，请输入！';
			      }
		    },
		    userDescription: function(value){
			    //备注最大长度
			    var userDescriptionMaxLength = 200;
		      if(strLengthValid(value,userDescriptionMaxLength)){
		        return '备注长度应少于200个字符，请修改！';
		      }
		    }
		  });

		//监听提交
		  form.on('submit(updateUserInfo)', function(data){
				var userName = $("input[name=userName]").val();
				var password = $("input[name=password]").val();
				/* var userRealName = $("input[name=userRealName]").val();
				var userPhone = $("input[name=userPhone]").val();
				var userEmail = $("input[name=userEmail]").val();
				var userAddress = $("input[name=userAddress]").val();
				var userDepartment = $("input[name=userDepartment]").val(); */
				var roleName = $("input[name=roleName]").val();
				var roleId = $("input[name=roleId]").val();
				var userDescription = $("textarea[name=userDescription]").val();
				var userId = userIdOriginal;
				//通过ajax调用修改的方法
				User.userName = userName;
				User.password = password;
			/* 	User.userRealName=userRealName;
				User.userPhone=userPhone;
				User.userEmail=userEmail;
				User.userAddress=userAddress;
				User.userDepartment=userDepartment; */
				User.roleName = roleName;
				User.roleId = roleId;
				User.userId = userId;
				User.userDescription = userDescription;
				// 请求
				$.ajax({
			        type:'POST',
			        dataType: "json",
			        data:User,
			        url: "${pageContext.request.contextPath}/cacUser/reserveUser",
			        success:function(result){
			        	resultContent=result.msg;
			        	resultSuccess=result.success;
			        	
			        	layui.use("layer", function(){
			    			var layer = layui.layer;
			    			layer.open({
				        		  content: resultContent,
				        		  btn: ['确定'],
				        		  yes: function(index, layero){
				        			 // 关闭父窗口
				        			 if(true==resultSuccess){
				        				 var index=parent.layer.getFrameIndex(window.name);
					 					 parent.layer.close(index);
					 					 parent.layui.table.reload('userTableReload',{page:{curr:1}}); 
				        			 }
				 					 // 关闭提示框
				 					 layer.close(index);
				        		  }
				        		}); 
			    			});
			        }
				});
		  });
	 
	});

	/* init */
	$(document).ready(function(){
		var data = ${sessionScope.data};
		if(null!=data){
			userIdOriginal = data.userId;
			$("input[name=userName]").val(data.userName);
			$("input[name=password]").val(data.password);
	/* 		$("input[name=userRealName]").val(data.userRealName);
			$("input[name=userPhone]").val(data.userPhone);
			$("input[name=userEmail]").val(data.userEmail);
			$("input[name=userAddress]").val(data.userAddress);
			$("input[name=userDepartment]").val(data.userDepartment); */
			$("input[name=roleName]").val(data.roleName);
			$("textarea[name=userDescription]").val(data.userDescription);
			$("#userName").attr("disabled","disabled");
		}
	}); 		
</script>

<!-- 需要弹出的修改和新增员工界面 -->
<div class="layui-row" id="popUpdateUser">
    <div class="layui-col-md10">
        <form class="layui-form" lay-filter="popUpdateUser" id="addAndUpdateEmployeeForm">
            <div class="layui-form-item">
                <label class="layui-form-label">用户名：</label>
                <div class="layui-input-inline">
                    <input id="userName" type="text" name="userName" lay-verify="userName"  class="layui-input">
                </div>
                <label class="layui-form-label">密码：</label>
                <div class="layui-input-inline">
                	<input type="password" name="password" lay-verify="password" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">角色：</label>
                <div class="layui-input-inline">
                    <input type="text" name="roleName"  class="layui-input">
                    <input type="text" name="roleId" style="display: none;" class="layui-input">
                </div>
                <div class="layui-input-inline">
                	<button type="button" onclick="selectRolePop()" class="layui-btn layui-btn-primary layui-btn-radius">选择角色</button>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">备注：</label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入内容" lay-verify="userDescription" class="layui-textarea" style="width:80%;" name="userDescription"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button type="button" class="layui-btn layui-btn-normal updateUserInfoSubmit" lay-submit="" lay-filter="updateUserInfo" >保存</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>