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
<title>修改菜单</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cac-all.js"></script>
</head>
<body>
<script type="text/javascript">
	//选择角色弹层
	var menuIdOriginal;
	var parentIdOriginal;
	var Menu = {};

layui.use('form', function(){
		
		var form = layui.form;

		 //自定义验证规则
		  form.verify({
			 //备注字符长度限制
			menuDescription: function(value){
			    //备注最大长度
			    var menuDescriptionMaxLength = 200;
		      if(strLengthValid(value,menuDescriptionMaxLength)){
		        return '备注长度应少于200个字符，请修改！';
		      }
		    },
		    //图片名称长度限制
		    iconCls: function(value){

	    	var iconClsState = $("#iconCls").attr("disabled");

	    	if("disabled"!=iconClsState){
				//图片名称最大长度
				var iconClsMaxLength = 50;
				//图片名称格式验证
				if(photoFormatValid(value)){
					  return '图片名称格式不正确，请修改！';
				   }
				if(strLengthValid(value,iconClsMaxLength)){
				   	  return '图片名称长度应少于50字符，请修改！';
				 }
		      }
		    },
		    //菜单路径长度限制
		    menuUrl: function(value){
			    //路径最大长度
			    var menuUrlMaxLength = 100;
		      if(strLengthValid(value,menuUrlMaxLength)){
		        return '路径长度应少于100字符，请修改！';
		      }
		      
		      if(requestUri(value)){
		    	  return "路径只允许出现“字母”、“数字”、“/”、“.”字符，请修改！";
			      
			      }
		    },
		    //序号的长度限制
		    seq: function(value){
			    //路径最大长度
			    var seqMaxLength = 5;

			  if((!NpeValid(value)) &&numberValid(value)){
				  return '序号必须是数字，请修改！';
				  }  
		      if((!NpeValid(value)) && strLengthValid(value,seqMaxLength)){
		        return '序号数字长度应少于5位，请修改！';
		      }
		    }
		  });

		//监听提交
		  form.on('submit(updateMenuInfo)', function(data){
			  var menuName = $("input[name=menuName]").val();
				var iconCls = $("input[name=iconCls]").val();
				var menuUrl = $("input[name=menuUrl]").val();
				var seq = $("input[name=seq]").val();
				var menuDescription = $("textarea[name=menuDescription]").val();
				var menuId = menuIdOriginal;
				//通过ajax调用修改的方法
				Menu.menuId = menuIdOriginal;
				Menu.menuName = menuName;
				Menu.iconCls = iconCls;
				Menu.menuUrl = menuUrl;
				Menu.seq = seq;
				Menu.menuDescription = menuDescription;
				Menu.parentId = parentIdOriginal;
				// 请求、
				$.ajax({
			        type:'POST',
			        dataType: "json",
			        data:Menu,
			        url: "${pageContext.request.contextPath}/cacMenu/reserveMenu",
			        success:function(){
			        	layui.use('layer',function(){
			        		layer = layui.layer
			        		layer.open({
				                type: 1
				                ,offset:'auto' //offset: 'auto'	默认坐标，即垂直水平居中
				                ,content: '<div style="padding: 20px 100px;">修改成功</div>'
				                ,btn: '关闭全部'
				                ,btnAlign: 'c' //按钮居中
				                ,shade: 0 //不显示遮罩
				                ,yes: function(){
				                	var index=parent.layer.getFrameIndex(window.name);
									  parent.layer.close(index);
				                }
				              });
				        	})
			        },
			       error:function(){
			    	   layer.open({
			                type: 1
			                ,offset:'auto' //offset: 'auto'	默认坐标，即垂直水平居中
			                ,content: '<div style="padding: 20px 100px;">修改失败</div>'
			                ,btn: '关闭全部'
			                ,btnAlign: 'c' //按钮居中
			                ,shade: 0 //不显示遮罩
			                ,yes: function(){
			                	var index=parent.layer.getFrameIndex(window.name);
								  parent.layer.close(index);
			                }
			              });
				       }
				});
		  });
	 
	});

	$(document).ready(function(){
		var data = ${sessionScope.data};
		if(null!=data){
			menuIdOriginal = data.menuId;
			parentIdOriginal = data.parentId;
			menuLevel=data.level;
			$("input[name=menuName]").val(data.menuName);
			$("input[name=iconCls]").val(data.iconCls);
			$("input[name=menuUrl]").val(data.menuUrl);
			$("input[name=seq]").val(data.seq);
			$("textarea[name=menuDescription]").val(data.menuDescription);
			
			$("#menuName").attr("disabled","disabled");
			if(-1==menuLevel){
				$("#iconCls").attr("disabled","disabled");
				$("#menuUrl").attr("disabled","disabled");
				$("#seq").attr("disabled","disabled");
			}else if(1==menuLevel){
				//$("#menuUrl").attr("disabled","disabled");
			}else if(2==menuLevel){
				$("#iconCls").attr("disabled","disabled");
			}
		}
		
	}); 
</script>
	
<!-- 需要弹出的修改和新增菜单界面 -->
<div class="layui-row" id="popUpdateUser">
    <div class="layui-col-md10">
        <form class="layui-form" lay-filter="popUpdateUser" id="addAndUpdateEmployeeForm">
            <div class="layui-form-item">
                <label class="layui-form-label">名称：</label>
                <div class="layui-input-inline">
                    <input id="menuName" type="text" name="menuName" class="layui-input">
                </div>
                <label class="layui-form-label">图标：</label>
                <div class="layui-input-inline">
                	<input id="iconCls" type="text" lay-verify="iconCls" name="iconCls" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">路径：</label>
                <div class="layui-input-inline">
                    <input id="menuUrl" type="text" lay-verify="menuUrl" name="menuUrl" class="layui-input">
                    <input type="text" name="menuId" style="display: none;" class="layui-input">
                </div>
                <label class="layui-form-label">序号：</label>
                <div class="layui-input-inline">
                	<input id="seq" type="text" lay-verify="seq" name="seq" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">备注：</label>
                <div class="layui-input-block">
                    <textarea id="menuDescription" placeholder="请输入内容" lay-verify="menuDescription" class="layui-textarea" style="width:80%;" name="menuDescription"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button type="button" class="layui-btn layui-btn-normal updateAndAddMenuInfoSubmit" lay-submit="" lay-filter="updateMenuInfo" >保存</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>