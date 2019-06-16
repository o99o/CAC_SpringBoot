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
<title>按钮管理</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script> 
</head>
<body>
<div>
	<div class="layui-form layui-inline" >
		<div class="layui-inline layui-form-item">
			<button class="layui-btn  iconAdd" data-type="search">新增</button>
		</div>
	</div>
</div>
<table id="operationManage" lay-filter="operationManagefilter"></table>
<script type="text/html" id="iconBar">
		<button class="layui-btn layui-btn-xs iconAdd" lay-event="edit" data-type="search">修改</button>
		<button class="layui-btn layui-btn-xs layui-btn-danger iconUpdate" lay-event="del" data-type="search">删除</button>
	</script>
<script type="text/javascript">
	$('.iconAdd').on('click', function(){
		var menuId = "${sessionScope.menu.menuId}";
		layer.open({
	        type:2,
	        title:"新增按钮",
	        area: ['400PX','240PX'],
	        content:['${pageContext.request.contextPath}/cacOperation/buttonAddPop/'+menuId+'', 'no'], //iframe的url，no代表不显示滚动条
	        end: function () {
	            location.reload();
	          }
	    });
  	});
</script>
<script charset="utf-8">
		var menuId = ${sessionScope.menuId};
		layui.use('table', function(){
			  var table = layui.table;
			  //方法级别渲染
			  table.render({
				    elem: '#operationManage'
				        ,url: '${pageContext.request.contextPath}/cacOperation/operationList'
				        ,method: 'post'
				        ,request: {
				       	  	pageName: 'curr' //页码的参数名称，默认：page
				       		,limitName: 'limit' //每页数据量的参数名，默认：limit
				        		} 
				        ,page:true
				        ,where: {
				        	menuId: menuId
				        }
				        ,id: 'popOperationTableId'//用户table重载
				        ,limit: 3
				        ,limits:[3,5,10]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
				        ,cols: [[
				          {field:'operationId', title:'按钮编号'}
				          ,{field:'operationName', title:'按钮名称'}
				          ,{field:'menuName', title:'所属菜单'}
				          ,{fixed: 'right', title:'操作', toolbar: '#iconBar',align: 'center'}
				        ]]
			  });

			//监听工具条
			  table.on('tool(operationManagefilter)', function(obj){
			    if(obj.event === 'del'){
				    
			    	 layer.confirm('真的删除行么', function(index){
					        var ids = obj.data.operationId;
					        $.ajax({
					        type:'POST',
					        url: "${pageContext.request.contextPath}/cacOperation/deleteOperation",
					        data:{"idsStr":ids},
					        success:function(data){
					        	layer.open({
		    		                type: 1
		    		                ,offset:'auto' //offset: 'auto'	默认坐标，即垂直水平居中
		    		                ,content: '<div style="padding: 20px 100px;">删除成功</div>'
		    		                ,btn: '关闭全部'
		    		                ,btnAlign: 'c' //按钮居中
		    		                ,shade: 0 //不显示遮罩
						        	,end:function(){
						            	location.reload();//刷新表格
						            }
		    		              });
						        }
					    	}) 
					    	//关闭弹窗
					        layer.close(index); 
					      });
			    } else if(obj.event === 'edit'){

			    	layer.open({
			        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
			            type:2,
			            title:"修改按钮",
			            area: ['400px','240px'],
			            content:['${pageContext.request.contextPath}/cacOperation/buttonEditPop/'+obj.data.operationId+'', 'no'], //iframe的url，no代表不显示滚动条
			            end:function(){
			            	location.reload();//刷新表格
			            }
			        });
			    }
			  });
			});
</script>
</body>
</html>