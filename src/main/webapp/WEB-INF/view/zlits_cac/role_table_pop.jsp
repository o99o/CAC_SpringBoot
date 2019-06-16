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
<title>表格操作 - layui</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/src/css/layui.css">
<style>
body{padding: 20px; /*overflow-y: scroll;*/}
#popSearchRole{

}
</style>
</head>
<body>
	<div class="layui-form" >
		<div class="layui-inline layui-form-item">
			<label class="layui-form-label">用户名：</label>
			<div class="layui-input-inline">
				<input class="layui-input"  name="id" id="userName" autocomplete="off">
			</div>
			<label class="layui-form-label">用户角色：</label>
			<div class="layui-input-inline">
			
				<select name="roleId" id="roleId">
					<option value="">请选择</option>
				</select>
			</div>
			<button class="layui-btn" data-type="reload">搜索</button>
		</div>
	</div> 
	<table id="userTable" lay-filter="userTable" ></table>

	<script type="text/html" id="barDemo">
  		<div>
		<privilege:operation operationId="10004"  clazz="layui-btn layui-btn-xs" onClick="updateUserDialog()"   name="修改"  iconCls="icon-edit" layEvent="edit" ></privilege:operation>
		<privilege:operation operationId="10018" clazz="layui-btn layui-btn-danger layui-btn-xs"  onClick="" name="删除"  iconCls="icon-remove" layEvent="del"></privilege:operation>
		</div>
	</script>
	<script type="text/html" id="toolbarDemo">
  		<i class="layui-icon">&#xe654;</i>
  		<i class="layui-icon">&#xe640;</i>
	</script>

	<script src="${pageContext.request.contextPath}/layui/src/layui.js" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/common/js/jquery-1.12.3.js" charset="utf-8"></script>
	
	<!-- 需要弹出的修改和新增员工界面 -->
	<div class="layui-row" id="popUpdateUser" style="display:none;">
	    <div class="layui-col-md10">
	        <form class="layui-form" lay-filter="popUpdateUser" id="addAndUpdateEmployeeForm">
	            <div class="layui-form-item">
	                <label class="layui-form-label">用户名：</label>
	                <div class="layui-input-inline">
	                    <input type="text" name="userName"  class="layui-input">
	                </div>
	                <label class="layui-form-label">密码：</label>
	                <div class="layui-input-inline">
	                	<input type="text" name="password"  class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">角色：</label>
	                <div class="layui-input-inline">
	                    <input type="text" name="roleName" class="layui-input">
	                </div>
	                <div class="layui-input-inline">
	                	<button type="button" onclick="selectRolePop()" class="layui-btn layui-btn-primary layui-btn-radius">选择角色</button>
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">备注：</label>
	                <div class="layui-input-block">
	                    <textarea placeholder="请输入内容" class="layui-textarea" name="userDescription"></textarea>
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <div class="layui-input-block">
	                    <button type="button" class="layui-btn layui-btn-normal">提交</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	
	<!-- 选择角色的按钮 -->
	<div class="layui-row" id="popSearchRole" style="display:none;">
		<table id="popSearchRoleTable" lay-filter="popSearchRoleTable"></table>
	</div>
	
	
</body>
	<script type="text/javascript">
	
	// 打开新增用户信息对话框
    function openUserAddDialog(){
    	$("#dlg").dialog("open").dialog("setTitle","添加用户信息");
    	$("#userName").removeAttr("readonly");
    	url = 'reserveUser';
    }
	//选择角色弹层
	function selectRolePop(){
		//加载角色table数据
		
		//将table弹层
		layui.use("layer", function(){
		 var layer = layui.layer;
			layer.open({
	        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	            type:2,
	            title:"选择角色",
	            area: ['40%','60%'],
	            //content:$("#popSearchRole").html()
	            content:['guodu.html', 'no'] //iframe的url，no代表不显示滚动条
	        });
		});

	}
	ROLE_TABLE;
	//加载角色table数据
	function loadRoleTableData(){
		layui.use(["form","table"], function(){
			  ROLE_TABLE =layui.table;
			  var form = layui.form;
			  //方法级别渲染
			  //新增返回值，目的为了“重载”。
			  ROLE_TABLE.render({
				    elem: '#popSearchRoleTable'
				        ,height:350
				        ,width:450
				        ,url: '${pageContext.request.contextPath}/cacRole/cacRoleList'
				        ,method: 'post'
				        ,request: {
				       	  	pageName: 'curr' //页码的参数名称，默认：page
				       		,limitName: 'limit' //每页数据量的参数名，默认：limit
				        		} 
				        ,page:true
				        ,id: 'popSearchRoleTableId'//用户table重载
				        ,limit: 5
				        ,limits:[5,10,15,20,50]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
				        ,toolbar: '#toolbarDemo'
				        ,cols: [[
				          {type: 'checkbox', fixed: 'left'}
				          ,{field:'roleId', title:'编号', width:50}
				          ,{field:'roleName', title:'角色名称', width:150}
				          ,{field:'roleDescription', title:'备注', width:150}
				        ]]
			  });

			 /*  table.on('checkbox(popSearchRoleTable)', function(obj){
				    alert("监听表格复选框选择");
				  });

			//监听排序
			   table.on('sort(popSearchRoleTable)', function(obj){
			    console.log(this, obj.field, obj.type)
			    //return;
			    //服务端排序
			    table.reload('popSearchRoleTable', {
			      initSort: obj
			      //,page: {curr: 1} //重新从第一页开始
			      ,where: { //重新请求服务端
			        key: obj.field //排序字段
			        ,order: obj.type //排序方式
			      }
			    });
			  }); */
			 // table.render(null,'popSearchRoleTable')
			  	//搜索查询
				var $ = layui.$, active = {
				    reload: function(){
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
				};

				$(document).on('click', '.layui-btn', function() {
					var type = $(this).data('type');
				    active[type] ? active[type].call(this) : '';
					});				
			  /* $('.layui-btn').on('click', function(){
			    var type = $(this).data('type');
			    active[type] ? active[type].call(this) : '';
			  }); */ 
			});
		}
	
	 // 删除用户
		function deleteUser(){
			var selectedRows=$("#dg").datagrid('getSelections');
			if(selectedRows.length==0){
				$.messager.alert('系统提示','请选择要删除的数据！');
				return;
			}
			var strIds=[];
			for(var i=0;i<selectedRows.length;i++){
				strIds.push(selectedRows[i].userId);
			}
			var ids=strIds.join(","); 
			$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
				if(r){
					$.post("deleteUser",{ids:ids},function(result){
						if(result.success){
							$.messager.alert('系统提示',"您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
							$("#dg").datagrid("reload");
						}else{
							$.messager.alert('系统提示',result.errorMsg);
						}
					},"json");
				}
			});
		}
	</script>
	<script charset="utf-8" >
	layui.use(["form","table"], function(){
		  ROLE_TABLE =layui.table;
		  var form = layui.form;
		  //方法级别渲染
		  //新增返回值，目的为了“重载”。
		  ROLE_TABLE.render({
			    elem: '#popSearchRoleTable'
			        ,height:350
			        ,width:450
			        ,url: '${pageContext.request.contextPath}/cacRole/cacRoleList'
			        ,method: 'post'
			        ,request: {
			       	  	pageName: 'curr' //页码的参数名称，默认：page
			       		,limitName: 'limit' //每页数据量的参数名，默认：limit
			        		} 
			        ,page:true
			        ,id: 'popSearchRoleTableId'//用户table重载
			        ,limit: 5
			        ,limits:[5,10,15,20,50]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
			        ,toolbar: '#toolbarDemo'
			        ,cols: [[
			          {type: 'checkbox', fixed: 'left'}
			          ,{field:'roleId', title:'编号', width:50}
			          ,{field:'roleName', title:'角色名称', width:150}
			          ,{field:'roleDescription', title:'备注', width:150}
			        ]]
		  });
	});

	
	var tableIns;
	var popForm;
	layui.use(["form","table"], function(){
		  var table = layui.table;
		  var form = layui.form;
		  popForm=layui.form;
		  //方法级别渲染
		  //新增返回值，目的为了“重载”。
		  table.render({
			    elem: '#userTable'
			        ,height: 450
			        ,url: '${pageContext.request.contextPath}/cacUser/userList'
			        ,method: 'post'
			        ,request: {
			       	  	pageName: 'curr' //页码的参数名称，默认：page
			       		,limitName: 'limit' //每页数据量的参数名，默认：limit
			        		} 
			        ,page:true
			        ,id: 'testReload'
			        ,limit: 5
			        ,limits:[5,10,15,20,50]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
			        ,toolbar: '#toolbarDemo'
			        ,cols: [[
			          {type: 'checkbox', fixed: 'left'}
			          ,{field:'userName', title:'用户名', width:150}
			          ,{field:'password', title:'密码', width:150}
			          ,{field:'roleName', title:'用户角色', width:150}
			          ,{field:'userDescription', title:'备注', width:150}
			          ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
			        ]]
		  });
		  	//搜索查询
		var $ = layui.$, active = {
		    reload: function(){
		      var userName = $('#userName');
		      var roleId = $('#roleId option:selected');
		      //执行重载
		      //testReload为table 的id标识
		      table.reload('testReload', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		        	userName: userName.val()
		            ,roleId: roleId.val()
		        }
		      });
		    }
		};
	  $('.layui-btn').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	  }); 
	  //监听表格复选框选择
	  table.on('checkbox(userTable)', function(obj){
	  });
	  //监听工具条
	   table.on('tool(userTable)', function(obj){//userTable为table的filter属性值
		   var data = obj.data;
		   //formData = data;
		    if(obj.event === 'del'){
		      layer.confirm('真的删除行么', function(index){
		        obj.del();
		        layer.close(index); 
		      });
		    } else if(obj.event === 'edit'){
		    	
				layer.open({
		        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
		            type:1,
		            title:"修改用户信息",
		            area: ['70%','70%'],
		            content:$("#popUpdateUser").html()
		        });
		        //给弹出层表单加载值
				setFormValue(data);
				//预加载弹出层table数据
//				loadRoleTableData();
		    	
		    }
	  }); 
	  //监听排序
	   table.on('sort(userTable)', function(obj){
	    console.log(this, obj.field, obj.type)
	    //return;
	    //服务端排序
	    table.reload('userTable', {
	      initSort: obj
	      //,page: {curr: 1} //重新从第一页开始
	      ,where: { //重新请求服务端
	        key: obj.field //排序字段
	        ,order: obj.type //排序方式
	      }
	    });
	  });
		   
	});
	//动态 加载选择角色数据  并新增 到select
	$(document).ready(function () {
			layui.use('form', function(){
			var form = layui.form;
			// 请求
		    $.ajax({
		        type:'POST',
		        dataType: "json",
		        url: "${pageContext.request.contextPath}/cacRole/roleCombobox",
		        success:function(data){
		            var optionStr = "";
		            for (var i = 0; i < data.data.length; i++) {
		            	optionStr += "<option value='" + data.data[i].roleId + "'>"
						+ data.data[i].roleName + "</option>";
					}
		            $("#roleId").append(optionStr);
		            //form表单的select的下拉框需要重新渲染，否则会有问题
		            form.render('select');
		        }
		    })
		});
	});
	function setFormValue(data){
		popForm.val("popUpdateUser", {
			  "userName":data.userName 
			 ,"password":data.password
			 ,"roleName":data.roleName
			 ,"userDescription":data.userDescription
			});
  		popForm.render(null,'popUpdateUser')

  		}
</script>
</html>