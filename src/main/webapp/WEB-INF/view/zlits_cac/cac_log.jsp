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
<title>日志管理</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script>
</head>
<body>
	<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">操作人：</label>
				<div class="layui-input-inline">
					<input class="layui-input" lay-verify="userName" name="userName" id="userName" autocomplete="off">
				</div>
				<label class="layui-form-label">操作类型：</label>
				<div class="layui-input-inline">
					<input class="layui-input" lay-verify="operation"  name="operation" id="operation" autocomplete="off">
				</div>
				<label class="layui-form-label">所属模块：</label>
				<div class="layui-input-inline">
					<input class="layui-input" lay-verify="module"  name="module" id="module" autocomplete="off">
				</div>
			</div>
			<div class="layui-form-item">
				
				<label class="layui-form-label">开始时间：</label>
				<div class="layui-input-inline">
					<input class="layui-input"  name="start" id="calendarTest1" placeholder="yyyy-MM-dd HH:mm:ss">
				</div>
				<label class="layui-form-label">结束时间：</label>
				<div class="layui-input-inline">
					<input class="layui-input"  name="end" id="calendarTest2" placeholder="yyyy-MM-dd HH:mm:ss">
				</div>
				<button class="layui-btn searchLog" data-type="reload" lay-submit="" lay-filter="searchLogFilter">查询</button>
				<!-- <button class="layui-btn logDownload" data-type="reload">后台日志下载</button> -->
			</div>
	</div>
	<table id="test" lay-filter="test"></table>
	
	<script type="text/html" id="barLog">
		<div>
		<privilege:operation operationId="10035"  clazz="layui-btn layui-btn-danger layui-btn-xs" onClick=""   name="删除"  iconCls="icon-remove" layEvent="del" ></privilege:operation>
		</div>
	</script>
	
	<script type="text/html" id="usernameTpl">
  		<a href="" class="layui-table-link">{{d.username || ''}}</a>
	</script>
	
	
	
	<script type="text/html" id="toolbarLog">
  		<i class="layui-icon">查询结果</i>
	</script>
	<script>
		var beginTime;
		var endTime;
		layui.use(['table','form'], function(){
		  var table = layui.table;
		  var form = layui.form;

		  var thisPageOperationIds=new Array();
		  thisPageOperationIds[0]="10035"

		  var allOperationIds = '${sessionScope.currentOperationIds}'; 
		   
		  var result = judgePageOperationArea(allOperationIds, thisPageOperationIds); 

			if(result){
				//渲染
				  table.render({
					    elem: '#test'
					        ,url: '${pageContext.request.contextPath}/cacLog/cacLogList'
					        ,method: 'post'
					        ,request: {
				       	  	 pageName: 'curr' //页码的参数名称，默认：page
				       		,limitName: 'limit' //每页数据量的参数名，默认：limit
					        		} 
					        ,page:true
					        ,id: 'testReload'
					        ,limit: 10
					        ,limits:[10,15,20,50]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
					        ,toolbar: '#toolbarLog'
					        ,cols: [[
					          {field:'logId', sort: true, title:'序号',align: 'center'}
					          ,{field:'userName', title:'操作人',align: 'center'}
					          ,{field:'createTime', title:'操作时间',align: 'center'}
					          ,{field:'operation', title:'操作类型',align: 'center', sort: true}
					          ,{field:'ip', title:'地址',align: 'center'}
					          ,{field:'module', title:'操作模块',align: 'center'}
					          ,{field:'content', title:'日志详情',align: 'center'}
					          ,{fixed: 'right', title:'操作', toolbar: '#barLog',align: 'center'}
					        ]]
				  });
			}else{
				//渲染
				  table.render({
					    elem: '#test'
					        ,url: '${pageContext.request.contextPath}/cacLog/cacLogList'
					        ,method: 'post'
					        ,request: {
				       	  	 pageName: 'curr' //页码的参数名称，默认：page
				       		,limitName: 'limit' //每页数据量的参数名，默认：limit
					        		} 
					        ,page:true
					        ,id: 'testReload'
					        ,limit: 10
					        ,limits:[10,15,20,50]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
					        ,toolbar: '#toolbarLog'
					        ,cols: [[
					          {field:'logId', sort: true, title:'序号',align: 'center'}
					          ,{field:'userName', title:'操作人',align: 'center'}
					          ,{field:'createTime', title:'操作时间',align: 'center'}
					          ,{field:'operation', title:'操作类型',align: 'center', sort: true}
					          ,{field:'ip', title:'地址',align: 'center'}
					          ,{field:'module', title:'操作模块',align: 'center'}
					          ,{field:'content', title:'日志详情',align: 'center'}
					        ]]
				  });
			}
		  
		  
		//搜索查询
	  		var $ = layui.$, active = {
			    reload: function(){
			      var userName = $('#userName');
			      var operation = $('#operation');
			      var module = $('#module');
			      var start = $('#start');
			      var end = $('#end');
			      //执行重载
			      //testReload为table 的id标识
			      table.reload('testReload', {
			        page: {
			          curr: 1 //重新从第 1 页开始
			        }
			        ,where: {
			        	userName: userName.val()
			            ,operation: operation.val()
			            ,module: module.val()
			            ,start: beginTime
			            ,end: endTime
			        }
			      });
			    }
			  };
			  //搜索查询
			  $('.searchLog').on('click', function(){
			    var type = $(this).data('type');
			    active[type] ? active[type].call(this) : '';
			  });
			  //日志下载
			  $('.logDownload').on('click', function(){
				  location.href="../cacLog/downloadLog4j";
				  });
		
		  //监听表格复选框选择
		  table.on('checkbox(test)', function(obj){
		    console.log(obj)
		  });
		  
		  //监听单元格编辑
		  table.on('edit(test)', function(obj){
		    var value = obj.value //得到修改后的值
		    ,data = obj.data //得到所在行所有键值
		    ,field = obj.field; //得到字段
		    
		  });
		  
		  //监听工具条
		  table.on('tool(test)', function(obj){
		    var data = obj.data;
		    layer.confirm('真的删除行么', function(index){
		        obj.del();
		        var deleteLogId = obj.data.logId;
		        $.ajax({
		        type:'POST',
		        url: "${pageContext.request.contextPath}/cacLog/deleteLog",
		        data:{"deleteLogId":deleteLogId},
		        success:function(data){
		        	layer.close(index); 
			        }
		    	})
		    	//关闭弹窗
		        layer.close(index); 
		      });
		  });
		  
		  //监听排序
		  table.on('sort(test)', function(obj){
		    console.log(this, obj.field, obj.type)
		    
		    //return;
		    //服务端排序
		    table.reload('test', {
		      initSort: obj
		      //,page: {curr: 1} //重新从第一页开始
		      ,where: { //重新请求服务端
		        key: obj.field //排序字段
		        ,order: obj.type //排序方式
		      }
		    });
		  });
		});


		//时间控件
		layui.use('laydate', function(){
			  var laydate = layui.laydate;
			  
			  //常规用法
			  laydate.render({
			    elem: '#calendarTest1'
			    ,type: 'datetime'
		    	                           
		    	,done: function(value, date, endDate){
		    		//得到日期生成的值，如：2017-08-18                                                  
			    	//得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds
			    	//得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。  
			    	beginTime = value;
		    	  }
			  });
			  laydate.render({
			    elem: '#calendarTest2'
				,type: 'datetime'
				,done: function(value, date, endDate){
					endTime = value;
				  }
			  });
		});
	</script>
</body>
</html>