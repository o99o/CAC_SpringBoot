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
<title>选择角色</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script>
<script type="text/html" id="toolbarUser">
  		<i class="layui-icon">查询结果</i>
</script>
</head>
<body>
<script charset="utf-8">
//选择角色弹层
//加载角色table数据
$(document).ready(function(){
		var getCheckData;
		layui.use(["form","table"], function(){
			  var ROLE_TABLE =layui.table;
			  var form = layui.form;
			  //方法级别渲染
			  //新增返回值，目的为了“重载”。
			  ROLE_TABLE.render({
				    elem: '#popSearchRoleTable'
				        ,url: '${pageContext.request.contextPath}/cacRole/cacRoleList'
				        ,method: 'post'
				        ,request: {
				       	  	pageName: 'curr' //页码的参数名称，默认：page
				       		,limitName: 'limit' //每页数据量的参数名，默认：limit
				        		} 
				        ,page:true
				        ,id: 'popSearchRoleTableId'//用户table重载
				        ,limit: 3
				        ,limits:[3,5,10]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
				        ,toolbar: '#toolbarUser'
				        ,cols: [[
				          {field:'roleId', title:'编号',event: 'setSign'}
				          ,{field:'roleName', title:'角色名称',event: 'setSign'}
				          ,{field:'roleDescription', title:'备注',event: 'setSign'}
				        ]]
			  });
			  ROLE_TABLE.render(null,"popSearchRoleTable");


			  //监听单元格事件
			  ROLE_TABLE.on('tool(popSearchRoleTable)', function(obj){
			    var data = obj.data;
			    if(obj.event === 'setSign'){
			    	getCheckData =data;
			    }
			  });

			//搜索查询
				var $ = layui.$, active = {
				search: function(){
				      var roleName = $('#roleName');
				      //执行重载
				      //testReload为table 的id标识
				      ROLE_TABLE.reload('popSearchRoleTableId', {
				        page: {
				          curr: 1 //重新从第 1 页开始
				        }
				        ,where: {
				        	roleName: roleName.val()
				        }
				      });
				    }
				,getCheckData: function(){ //获取选中数据
				      var checkStatus = ROLE_TABLE.checkStatus('popSearchRoleTableId')
				      ,data = checkStatus.data;
				      var dataStr = JSON.stringify(data);
				    }
				,isAll: function(){ //验证是否全选
				      var checkStatus = ROLE_TABLE.checkStatus('popSearchRoleTableId');
				       layer.msg(checkStatus.isAll ? '全选': '未全选')
				    }
				};
			  $('.roleSearch').on('click', function(){
			    var type = $(this).data('type');
			    active[type] ? active[type].call(this) : '';
			  }); 
			  
			  $('.getRoleConfirm').on('click', function(){
				  //关闭弹出层
				  if(null!=getCheckData){
					  parent.$('input[name=roleName]').val(getCheckData.roleName);
					  parent.$('input[name=roleId]').val(getCheckData.roleId);
					 }
				  
				  var index=parent.layer.getFrameIndex(window.name);
				  parent.layer.close(index);
				  }); 
			});

			
		});
</script>
<!-- 选择角色的按钮 -->
<div>
	<div class="layui-form layui-inline" >
		<div class="layui-inline layui-form-item">
			<label class="layui-form-label">角色名称：</label>
			<div class="layui-input-inline">
				<input class="layui-input"  name="id" id="roleName" autocomplete="off">
			</div>
			<button class="layui-btn roleSearch" data-type="search">查询</button>
		</div>
	</div>
</div>
<table id="popSearchRoleTable" lay-filter="popSearchRoleTable"></table>
<!-- 确定角色按钮 -->
<div>
	<div class="layui-form layui-inline" style="float: right;">
		<div class="layui-inline layui-form-item">
			<button class="layui-btn layui-btn-normal getRoleConfirm"  data-type="getCheckData">保存</button>
		</div>
	</div>
</div>
	
</body>
</html>