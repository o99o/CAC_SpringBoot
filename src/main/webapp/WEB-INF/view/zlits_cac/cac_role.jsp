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
<title>角色管理</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script>
</head>
	
<body>
	<form class="layui-form">
	<div id="treeOpen" style="display: none;"></div>
	</form> 
	
	
	<div class="layui-form">
		<div class="layui-inline layui-form-item">
			<label class="layui-form-label">角色名称：</label>
			<div class="layui-input-inline">
				<input class="layui-input"  name="roleName" id="roleName" autocomplete="off">
			</div>
			<button class="layui-btn" data-type="reload">查询</button>
			<privilege:operation operationId="10022"  clazz="layui-btn roleInsert" onClick="addRoleInfo()"   name="新增"  iconCls="icon-add" layEvent="add" ></privilege:operation>
			
		</div>
	</div>
<table id="roleManage" lay-filter="roleManage"></table>

<script type="text/html" id="roleBar">
	<privilege:operation operationId="10027"  clazz="layui-btn rolePower layui-btn-xs" onClick=""   name="授权"  iconCls="icon-edit" layEvent="power" ></privilege:operation>
	<privilege:operation operationId="10024"  clazz="layui-btn layui-btn-xs" onClick=""   name="修改"  iconCls="icon-edit" layEvent="edit" ></privilege:operation>
	<privilege:operation operationId="10025" clazz="layui-btn layui-btn-danger layui-btn-xs"  onClick="" name="删除"  iconCls="icon-remove" layEvent="del"></privilege:operation>
</script>

<script type="text/html" id="usernameTpl">
  <a href="" class="layui-table-link">{{d.username || ''}}</a>
</script>



<script type="text/html" id="roleToolBar">
  <i class="layui-icon">查询结果</i>
</script>

<script>
layui.use('table', function(){
  var table = layui.table;


    var thisPageOperationIds=new Array();
	thisPageOperationIds[0]="10027"
	thisPageOperationIds[1]="10024"
	thisPageOperationIds[1]="10025"

  	var allOperationIds = '${sessionScope.currentOperationIds}';
  	var result = judgePageOperationArea(allOperationIds, thisPageOperationIds);
if(result){
	//渲染
	  table.render({
		    elem: '#roleManage'
		        ,url: '${pageContext.request.contextPath}/cacRole/cacRoleList'
		        ,method: 'post'
		        ,request: {
		       	  	pageName: 'curr' //页码的参数名称，默认：page
		       		,limitName: 'limit' //每页数据量的参数名，默认：limit
		        		} 
		        ,page:true
		        ,id: 'roleTalbeReload'
		        ,limit: 10
		        ,limits:[10,15,20,50]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
		        ,toolbar: '#roleToolBar'
		        ,cols: [[
		           {field:'roleId', title:'编号名',align: 'left'}
		          ,{field:'roleName', title:'角色名称',align: 'left'}
		          ,{field:'roleDescription', title:'备注',align: 'left'}
		          ,{fixed: 'right', title:'操作', toolbar: '#roleBar',align: 'left'}
		        ]]
	  });
	
}else{
	//渲染
	  table.render({
		    elem: '#roleManage'
		        ,url: '${pageContext.request.contextPath}/cacRole/cacRoleList'
		        ,method: 'post'
		        ,request: {
		       	  	pageName: 'curr' //页码的参数名称，默认：page
		       		,limitName: 'limit' //每页数据量的参数名，默认：limit
		        		} 
		        ,page:true
		        ,id: 'roleTalbeReload'
		        ,limit: 10
		        ,limits:[10,15,20,50]//每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
		        ,toolbar: '#roleToolBar'
		        ,cols: [[
		           {field:'roleId', title:'编号名',align: 'left'}
		          ,{field:'roleName', title:'角色名称',align: 'left'}
		          ,{field:'roleDescription', title:'备注',align: 'left'}
		        ]]
	  });
	
}
  	
  
  
	//搜索查询
	var $ = layui.$, active = {
	    reload: function(){
	      var roleName = $('#roleName');
	      //执行重载
	      //testReload为table 的id标识
	      table.reload('roleTalbeReload', {
	        page: {
	          curr: 1 //重新从第 1 页开始
	        }
	        ,where: {
	        	roleName: roleName.val()
	        }
	      });
	    }
	  };
	  
	  $('.layui-btn').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	  });
	  //监听表格复选框选择
	  table.on('checkbox(roleManage)', function(obj){
	    console.log(obj)
	  });
	  
	  //监听单元格编辑
	  table.on('edit(roleManage)', function(obj){
	    var value = obj.value //得到修改后的值
	    ,data = obj.data //得到所在行所有键值
	    ,field = obj.field; //得到字段
	    
	  });
  
	  //监听工具条
	  table.on('tool(roleManage)', function(obj){
	    var data = obj.data;
	    if(obj.event === 'del'){
	    	 layer.confirm('真的删除行么', function(index){
			        var deleteRoleId = obj.data.roleId;
					//超级管理员角色
			        var superRoleId = 1;
					if(deleteRoleId !== superRoleId){
						$.ajax({
					        type:'POST',
					        url: "${pageContext.request.contextPath}/cacRole/deleteRole",
					        data:{"deleteRoleId":deleteRoleId},
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
			  			 					 table.reload('roleTalbeReload',{page:{curr:1}});
			 			        		  }
			 			        		}); 
			 		    			});
						        }
					    	})
						}else{
							layui.use("layer", function(){
		 		    			var resultLayer = layui.layer;
		 		    			resultLayer .open({
		 			        		  content: "当前角色不允许被删除！",
		 			        		  btn: ['关闭'],
		 			        		  yes: function(resultIndex, layero){
		 			        			 // 关闭本级提示框
		  			 					 resultLayer.close(resultIndex);
		  			 					 // 关闭上级提示框
		  			 					 layer.close(index);
		  			 					 // 刷新
		  			 					 table.reload('roleTalbeReload',{page:{curr:1}});
		 			        		  }
		 			        		}); 
		 		    			});
							}
			        
			        
			    	//关闭弹窗
			        layer.close(index); 
			      });
	    } else if(obj.event === 'edit'){
	    	layer.open({
	        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	            type:2,
	            title:"修改角色信息",
	            area: ['60%','60%'],
	            content:['${pageContext.request.contextPath}/cacRole/roleInfoPop/'+data.roleId+'', 'no'] //iframe的url，no代表不显示滚动条
	        });
	    }else if(obj.event === 'power'){
	    	rolePower(obj.data.roleId);
		    }
	    
	  });
  
	  //监听排序
	  table.on('sort(roleManage)', function(obj){
	    console.log(this, obj.field, obj.type)
	    
	    //服务端排序
	    table.reload('roleManage', {
	      initSort: obj
	      //,page: {curr: 1} //重新从第一页开始
	      ,where: { //重新请求服务端
	        key: obj.field //排序字段
	        ,order: obj.type //排序方式
	      }
	    });
	  });
	});
	//监听新增角色信息层点击事件
	function addRoleInfo(){
		layer.open({
        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type:2,
            title:"新增角色信息",
            area: ['60%','60%'],
            content:['${pageContext.request.contextPath}/cacRole/roleInfoPop/-1', 'no'] //iframe的url，no代表不显示滚动条
        });
	} 
	//监听授权点击事件 加载树
	function rolePower(roleId){
		var xtreeMenu;
		layer.open({
        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type:1,
            title:"角色授权",
            area: ['60%','60%'],
            content: $('#treeOpen'),
            cancel: function(index, layero){ 
            	$(".layui-tree li").remove();
            	},
            btn: ['授权', '关闭']
            ,yes: function(index, layero){
            	//var checkedMenuArr = xtreeMenu.GetChecked();
                //按钮【按钮一】的回调
                
                var checkMenuObj = getCheckMenu();
                var menuStr="";
                for (var i = 0; i < checkMenuObj.length; i++) {
                    if(i==0){
                    	menuStr= menuStr+checkMenuObj[i].value;
                     }else{
                     	menuStr= menuStr+","+checkMenuObj[i].value;
                     }
				}
                //根据选中末级节点获取所有的选中的父级节点和末级节点menuId
                $.ajax({
    		        type:'POST',
    		        url: "${pageContext.request.contextPath}/cacRole/getAllCheckMenuId",
    		        data:{"checkedMenuIds":menuStr},
    		        success:function(menuIdsStr){
    		        	menuStr = menuIdsStr;
    		        	menuStr = menuStr.replace(/\r\n/g,"")
    		        	//授权按钮的id
    		        	var powerId = "10027";
    		        	if(roleId==1&&menuStr.indexOf(powerId) == -1){
    		        		layui.use("layer", function(){
		 		    			var resultLayer = layui.layer;
		 		    			resultLayer.open({
		 			        		  content: "当前用户角色不允许对“授权”按钮进行取消勾选",
		 			        		  btn: ['确定'],
		 			        		  yes: function(resultIndex, layero){
		 			        			 // 关闭本级提示框
		  			 					 resultLayer.close(resultIndex);
		 			        		  }
		 			        		}); 
		 		    			});
        		        	}else{
        		        		updatePrivilege(roleId,menuStr);
            		        }
    			        }
    		    	})
            }
        });  
		 $.ajax({
		        type:'POST',
		        dataType:'text',
		        url: "${pageContext.request.contextPath}/cacRole/chooseMenu",
		        data:{"roleId":roleId,"parentId":-1},
		        success:function(result){
		        	loadTree(result);
			        }
		    	})
	}
	//修改授权
	function updatePrivilege(roleId,menuStr){
		$.ajax({
	        type:'POST',
	        dataType:'text',
	        url: "${pageContext.request.contextPath}/cacRole/updateRoleMenu",
	        data:{"roleId":roleId,"menuStr":menuStr},
	        success:function(result){

	        	layer.open({
	                type: 1
	                ,offset:'auto' //offset: 'auto'	默认坐标，即垂直水平居中
	                ,content: '<div style="padding: 20px 100px;">授权成功</div>'
	                ,btn: '关闭全部'
	                ,btnAlign: 'c' //按钮居中
	                ,shade: 0 //不显示遮罩
	                ,yes: function(){
	                  layer.closeAll();
	                  location.reload(); 
	                }
	              });
		        },
		    error:function(){
		    	layer.open({
	                type: 1
	                ,offset:'auto' //offset: 'auto'	默认坐标，即垂直水平居中
	                ,content: '<div style="padding: 20px 100px;">授权失败</div>'
	                ,btn: '关闭全部'
	                ,btnAlign: 'c' //按钮居中
	                ,shade: 0 //不显示遮罩
	                ,yes: function(){
	                  layer.closeAll();
	                }
	              });
			    }
	    	})



		}
	
	function loadTree(result){
			layui.use(['form'], function () { 
				var myobj = eval("["+result+"]");
				var form = layui.form;
				xtreeMenu = new layuiXtree({
				      elem: 'treeOpen',   //(必填) 放置xtree的容器id，不要带#号
				       form: form,     //(必填) layui 的 form
				       isopen: true,  //加载完毕后的展开状态，默认值：true
				       ckall: true ,
				       data: myobj[0],
					   click: function (data) {  //节点选中状态改变事件监听，全选框有自己的监听事件
				       }     //(必填) json数组（数据格式在下面）
				});

			window.getCheckMenu = function(){
					 var CheckedMenu = xtreeMenu.GetChecked();
			           var $checkObj = $(CheckedMenu);
			           return $checkObj;
				}
			});
		} 
</script>
</body>
</html>