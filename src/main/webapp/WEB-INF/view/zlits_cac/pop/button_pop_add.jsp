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
<title>新增按钮</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script>
</head>
<body>
	
<script type="text/javascript">
	$(document).ready(function(){
		var menuName = "${sessionScope.menuForOperate.menuName}";
		$("input[name=menuName]").val(menuName); 
		});
	 function submitListener(){
		var Operation = {};
		var menuName = $("input[name=menuName]").val();
		var operationName = $("input[name=operationName]").val();
		//通过ajax调用修改的方法
		Operation.menuName = menuName;
		Operation.operationName = operationName;
		
		Operation.menuId = "${sessionScope.menuForOperate.menuId}";
		// 请求、
		$.ajax({
	        type:'POST',
	        dataType: "json",
	        data:Operation,
	        url: "${pageContext.request.contextPath}/cacOperation/reserveOperation",
	        success:function(){
	        	layui.use('layer',function(){
	        		layer = layui.layer
	        		layer.open({
		                type: 1
		                ,content: '<div style="padding: 20px 100px;">新增成功</div>'
			            ,offset: 't'
		                ,btn: '关闭全部'
		                ,btnAlign: 'c' //按钮居中
		                ,shade: 0 //不显示遮罩
		                ,scrollbar:true 
		                ,yes: function(){
		                	var index=parent.layer.getFrameIndex(window.name);
							  parent.layer.close(index);
		                }
		              });
		        	})
	        }
		});
		} 
</script>
<!-- 需要弹出的修改按钮界面 -->
<div class="layui-row" id="popUpdateAndAddRole">
    <div class="layui-col-md10">
        <form class="layui-form" lay-filter="buttonEditAndAdd" id="buttonEditAndAdd">
            <div class="layui-form-item">
                <label class="layui-form-label">所属菜单：</label>
                <div class="layui-input-inline">
                    <input type="text" name="menuName" class="layui-input" readonly="readonly">
                </div>
            </div>
            <div class="layui-form-item">
            	<label class="layui-form-label">按钮名称：</label>
                 <div class="layui-input-inline">
                    <input type="text" name="operationName" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button type="button" class="layui-btn layui-btn-normal" onclick="submitListener()">保存</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>