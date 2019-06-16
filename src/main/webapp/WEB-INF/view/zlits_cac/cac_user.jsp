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
<title>用户管理</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script>
</head>
<body>
	<div>
		<div class="layui-form layui-inline" >
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
				<button class="layui-btn roleSearch" data-type="search">查询</button>
				<privilege:operation operationId="10019"  clazz="layui-btn userInsert" onClick=""  name="新增"  iconCls="icon-add" layEvent="add" ></privilege:operation>
				
			</div>
		</div>
	</div>
	<table id="popSearchRoleTable" lay-filter="popSearchRoleTable"></table>
	<table id="userTable" lay-filter="userTable" ></table>

	<script type="text/html" id="barUser">
  		<div>
		<privilege:operation operationId="10020"  clazz="layui-btn layui-btn-xs" onClick=""   name="修改"  iconCls="icon-edit" layEvent="edit" ></privilege:operation>
		<privilege:operation operationId="10021" clazz="layui-btn layui-btn-danger layui-btn-xs"  onClick="" name="删除"  iconCls="icon-remove" layEvent="del"></privilege:operation>
		</div>
	</script>
	<script type="text/html" id="toolbarUser">
  		<i class="layui-icon">查询结果</i>
	</script>

</body>
	<script type="text/javascript" charset="utf-8">
	var tableIns;
	var popForm;
	
	layui.use(["form","table"], function(){
		  var table = layui.table;
		  tableIns =table;
		  var form = layui.form;
		  popForm=layui.form;
		  //方法级别渲染
		  //新增返回值，目的为了“重载”。
		  var thisPageOperationIds=new Array();
		  thisPageOperationIds[0]="10020"
		  thisPageOperationIds[1]="10021"

		  var allOperationIds = '${sessionScope.currentOperationIds}'; 
		  //判断页面操作区是否有按钮	
		   
		  var result = judgePageOperationArea(allOperationIds, thisPageOperationIds);
		  
		//操作区没有按钮
		  if(result){
			  table.render({
				    elem: '#userTable'
				        ,url: '${pageContext.request.contextPath}/cacUser/userList'
				        ,method: 'post'
				        ,request: {
				       	  	pageName: 'curr' //页码的参数名称，默认：page
				       		,limitName: 'limit' //每页数据量的参数名，默认：limit
				        		} 
				        ,page:true
				        ,id: 'userTableReload'
				        ,limit: 10
				        ,limits:[10,15,20,50]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
				        ,toolbar: '#toolbarUser'
				        ,cols: [[
				          {field:'userName',align: 'left', title:'用户名'}
				          ,{field:'roleName',align: 'left', title:'用户角色'}
				          ,{field:'userDescription',align: 'left', title:'备注'}
				          ,{fixed: 'right', title:'操作', align: 'left', toolbar: '#barUser'}
				        ]]
			  });
			  }else{
				  table.render({
					    elem: '#userTable'
					        ,url: '${pageContext.request.contextPath}/cacUser/userList'
					        ,method: 'post'
					        ,request: {
					       	  	pageName: 'curr' //页码的参数名称，默认：page
					       		,limitName: 'limit' //每页数据量的参数名，默认：limit
					        		} 
					        ,page:true
					        ,id: 'userTableReload'
					        ,limit: 10
					        ,limits:[10,15,20,50]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
					        ,toolbar: '#toolbarUser'
					        ,cols: [[
					          {field:'userName',align: 'left', title:'用户名'}
					          ,{field:'roleName',align: 'left', title:'用户角色'}
					          ,{field:'userDescription',align: 'left', title:'备注'}
					        ]]
				  });

				  }
		  
		  
		  	//搜索查询
		var $ = layui.$, active = {
				search: function(){
		      var userName = $('#userName');
		      var roleId = $('#roleId option:selected');
		      //执行重载
		      //testReload为table 的id标识
		      table.reload('userTableReload', {
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
	  $('.roleSearch').on('click', function(){
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
		        var deleteUserId = obj.data.userId;
		        //超级用户id
		        var superUserId = 1;
		        if(deleteUserId!==superUserId){
		        	$.ajax({
				        type:'POST',
				        url: "${pageContext.request.contextPath}/cacUser/deleteUser",
				        data:{"deleteUserId":deleteUserId},
				        success:function(data){
				        	resultContent=data.msg;

				        	if(true==data.success){
				        		obj.del();
				        	}
				        	layui.use("layer", function(){
		 		    			var resultLayer = layui.layer;
		 		    			resultLayer .open({
		 			        		  content: resultContent,
		 			        		  btn: ['确定'],
		 			        		  yes: function(resultIndex, layero){
		 			        			 // 关闭本级提示框
		  			 					 resultLayer.close(resultIndex);
		  			 					 // 关闭上级提示框
		  			 					 layer.close(index);
		  			 					 // 刷新
		  			 					 table.reload('userTableReload',{page:{curr:1}});
		 			        		  }
		 			        		}); 
		 		    			});
					        }
				    	})
			        }else{
			        	layui.use("layer", function(){
	 		    			var resultLayer = layui.layer;
	 		    			resultLayer .open({
	 			        		  content: "当前用户不允许被删除！",
	 			        		  btn: ['关闭'],
	 			        		  yes: function(resultIndex, layero){
	 			        			 // 关闭本级提示框
	  			 					 resultLayer.close(resultIndex);
	  			 					 // 关闭上级提示框
	  			 					 layer.close(index);
	  			 					 // 刷新
	  			 					 table.reload('userTableReload',{page:{curr:1}});
	 			        		  }
	 			        		}); 
	 		    			});
						return;
				        }
		      });
		    } else if(obj.event === 'edit'){
		    	//var tmpData = encodeURI(JSON.stringify(data));
				layer.open({
		        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
		            type:2,
		            title:"修改用户信息",
		            area: ['750px','550px'],
		            content:['${pageContext.request.contextPath}/cacUser/userInfoPop/'+data.userId+'', 'no'] //iframe的url，no代表不显示滚动条
		        });
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
		            for (var i = 0; i < data.length; i++) {
		            	optionStr += "<option value='" + data[i].roleId + "'>"
						+ data[i].roleName + "</option>";
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

	// 监听新增用户信息层点击事件
	$('.userInsert').on('click', function(){
			//为了和修改User方法的保持一致，因此新增一个假参数
			layer.open({
	        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	            type:2,
	            title:"新增用户信息",
	            area: ['750px','550px'],
	            content:['${pageContext.request.contextPath}/cacUser/userInfoPop/-1', 'no'] //iframe的url，no代表不显示滚动条
	        });
		}); 
</script>
</html>