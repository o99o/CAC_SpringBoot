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
<title>新增修改角色</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script>
</head>
<body>
	
<script type="text/javascript">
	//选择角色弹层
	var roleIdOriginal;
	var Role = {};



layui.use('form', function(){
		
		var form = layui.form;

		 //自定义验证规则
		  form.verify({
			  roleName: function(value){
		      if(NpeValid(value)){
		        return '角色名称不能为空，请填写！';
		      }
		      if(strLengthValid(value,20)){
			        return '角色名称长度应少于20个字符，请修改！';
			      }
		    },
		    roleDescription: function(value){
		      if(strLengthValid(value,200)){
		        return '备注长度应少于200个字符，请修改！';
		      }
		    }
		  });

		//监听提交
		  form.on('submit(updateRoleInfo)', function(data){
			  var roleName = $("input[name=roleName]").val();
				var roleDescription = $("textarea[name=roleDescription]").val();
				var roleId = roleIdOriginal;
				//通过ajax调用修改的方法
				Role.roleName = roleName;
				Role.roleDescription = roleDescription;
				Role.roleId = roleId;
				// 请求
				$.ajax({
			        type:'POST',
			        dataType: "json",
			        data:Role,
			        url: "${pageContext.request.contextPath}/cacRole/reserveRole",
			        success:function(result){
						resultContent=result.msg;
						resultSuccess=result.success;
			        	
			        	layui.use("layer", function(){
			    			var layer = layui.layer;
			    			layer.open({
				        		  content: resultContent,
				        		  btn: ['确定'],
				        		  yes: function(resultIndex, layero){
				        			  // 关闭提示框
					 				  layer.close(resultIndex);
				        			  
				 					 // 关闭父窗口
				        			 if(true==resultSuccess){
				        				 var index=parent.layer.getFrameIndex(window.name);
										 parent.layer.close(index);
										 parent.layui.table.reload('roleTalbeReload',{page:{curr:1}});
				        			 }
				        			 
				 					
				        		  }
				        		}); 
			    			});
			        }
				});
		  });
	 
	});

	$(document).ready(function(){
		var data = ${sessionScope.data};
		if(null!=data){
			roleIdOriginal = data.roleId;
			$("input[name=roleName]").val(data.roleName);
			$("input[name=roleDescription]").val(data.roleDescription);
			$("textarea[name=roleDescription]").val(data.roleDescription);
			$("#roleName").attr("disabled","disabled");
		}
		
	}); 
</script>

<!-- 需要弹出的修改和新增角色界面 -->
<div class="layui-row" id="popUpdateAndAddRole">
    <div class="layui-col-md10">
        <form class="layui-form" lay-filter="popUpdateAndAddRole" id="updateAndAddRoleForm">
            <div class="layui-form-item">
                <label class="layui-form-label">角色名称：</label>
                <div class="layui-input-inline">
                    <input id="roleName" type="text" lay-verify="roleName" name="roleName" class="layui-input">
                </div>
                
            </div>
            <div class="layui-form-item">
            	<label class="layui-form-label">备注：</label>
                <div class="layui-input-block">
                	<textarea placeholder="请输入内容" lay-verify="roleDescription" class="layui-textarea" name="roleDescription"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button type="button" class="layui-btn layui-btn-normal updateAndAddUserInfoSubmit" 
                    onclick="submitListener()" lay-submit="" lay-filter="updateRoleInfo">保存</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>