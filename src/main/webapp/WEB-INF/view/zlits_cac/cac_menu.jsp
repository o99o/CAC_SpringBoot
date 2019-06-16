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
<title>菜单管理 </title>
<script type="text/javascript" 
src="${pageContext.request.contextPath}/js/cac-all.js"></script>
</head>
<body>
<div id="menuTree"></div>
<script type="text/html" id="usernameTpl">
  <a href="" class="layui-table-link">{{d.username || ''}}</a>
</script>
<script>
//顶级菜单id;
var topLevelMenuId = 1;

function delMenu(nodeId) {
	if(nodeId !== topLevelMenuId){
		layer.confirm('真的删除行么', function(index){
	        $.ajax({
	        type:"POST",
	        dataType: 'json',
	        async:false,
	        url: "${pageContext.request.contextPath}/cacMenu/deleteMenu",
	        data:{"deleteMenuId":nodeId},
	        success:function(data){
	        	resultContent=data.msg;
	        	resultSuccess=data.success;
	        	
		      	//关闭弹窗
        		layer.confirm(resultContent, function(index){
        			location.reload(); 
        			layer.close(index);
	        		});
		     }
	       ,error:function(){
	    	    layer.alert("删除失败！");
		       }
	    	});
	      });
	}else{
		layui.use("layer", function(){
 			var resultLayer = layui.layer;
 			resultLayer .open({
	        		  content: "当前菜单不允许被删除！",
	        		  btn: ['关闭'],
	        		  yes: function(){
	        			//刷新父页面
	 					location.reload(); 
	        		  }
	        		}); 
 			});
		}
		}
	 

function buttonManager(nodeId){

	if(nodeId !== topLevelMenuId){
		layer.open({
	        type:2,
	        title:"按钮管理",
	        area: ['700px','350px'],
	        content:['${pageContext.request.contextPath}/cacOperation/buttonManagePop/'+nodeId+'', 'no'], //iframe的url，no代表不显示滚动条
	        end: function () {
	            location.reload();
	          }
	    });
		}else{
			layui.use("layer", function(){
	 			var resultLayer = layui.layer;
	 			resultLayer .open({
		        		  content: "当前菜单不允许对按钮进行操作！",
		        		  btn: ['关闭'],
		        		  yes: function(){
		        			//刷新父页面
		 					location.reload(); 
		        		  }
		        		}); 
	 			});
			}
	
}
/**
 * 新增菜单
 */
function addMenu(nodeId){
	$.ajax({
        type:'POST',
        dataType: "json",
        url: "${pageContext.request.contextPath}/cacMenu/judgeMenuLevel",
        data:{"menuId":nodeId},
        success:function(data){
            if("true"==data){
            	layer.open({
                    type:2,
                    title:"新增菜单信息",
                    area: ['750px','350px'],
                    content:['${pageContext.request.contextPath}/cacMenu/menuAddPop/'+nodeId+'', 'no'], //iframe的url，no代表不显示滚动条
                    end: function () {
                        location.reload();
                      }
                });
               }else{
            	   layer.alert("当前菜单不支持新增子菜单，请选择上一级菜单新增！");	
                   }
	        }
    	})
	
	
}
function updateMenu(nodeId){
	layer.open({
    	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        type:2,
        title:"修改菜单信息",
        area: ['750px','350px'],
        content:['${pageContext.request.contextPath}/cacMenu/menuUpdatePop/'+nodeId+'', 'no'], //iframe的url，no代表不显示滚动条
        end: function () {
            location.reload();
          }
    });
}

//有操作按钮
var layoutHasOperation = [
    { name: '菜单名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: 'width: 60%' },
    {
        name: '操作',
        headerClass: 'value_col',
        colClass: 'value_col',
        render: function(row) {
            return '<privilege:operation operationId="10029" clazz="layui-btn layui-btn-xs"  onClick="updateMenu(' + row.id + ')" name="修改"  iconCls="" layEvent="edit"></privilege:operation><privilege:operation operationId="10028" clazz="layui-btn layui-btn-xs"  onClick="addMenu(' + row.id + ')" name="新增"  iconCls="" layEvent="add"></privilege:operation><privilege:operation operationId="10031" clazz="layui-btn layui-btn-xs"  onClick="buttonManager(' + row.id + ')" name="按钮管理"  iconCls="" layEvent="manage"></privilege:operation><privilege:operation operationId="10030" clazz="layui-btn layui-btn-danger layui-btn-xs"  onClick="delMenu(' + row.id + ')" name="删除"  iconCls="" layEvent="del"></privilege:operation>'; //列渲染
        }
    },
];
//没有操作的按钮
var layoutNoOperation = [
    { name: '菜单名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: 'width: 60%' },
];

/**
 * 渲染tree-table数据
 */
layui.use(['form', 'tree', 'layer'], function() {
    var layer = layui.layer, tree = layui.tree, form = layui.form;
	//请求menu树数据
	$.ajax({
        type:'POST',
        dataType:'text',
        url: "${pageContext.request.contextPath}/cacMenu/treeGridMenu",
        data:{"parentId":-1},
        success:function(result){
        	var myobj = eval("["+result+"]");


          var thisPageOperationIds=new Array();
  		  thisPageOperationIds[0]="10029";
		  thisPageOperationIds[1]="10028";
		  thisPageOperationIds[2]="10030";
		  thisPageOperationIds[3]="10031";

  		  var allOperationIds = '${sessionScope.currentOperationIds}'; 
  		   
  		  var result = judgePageOperationArea(allOperationIds, thisPageOperationIds); 

  		  if(result){
  			layui.treeGird({
        		  elem: '#menuTree'
            	  ,spreadable:true//设置是否全部展开
        		  ,nodes: myobj[0]
          	  	,layout: layoutHasOperation
        		});
  			form.render;
  	  		  }else{
  	  			layui.treeGird({
  	      		  elem: '#menuTree'
  	          	  ,spreadable:true//设置是否全部展开
  	      		  ,nodes: myobj[0]
  	  		,layout: layoutNoOperation
  	      		});
  	  		form.render;
  	  	  		  }
        	
        	
        	
	       }
    	});
	});

</script>
</body>
</html>