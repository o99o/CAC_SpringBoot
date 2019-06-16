<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>公共应用组件</title>
  <script type="text/javascript" src="/js/cac-all-main.js"></script>
  <script type="text/javascript">
	//iframe自适应
	$(window).on('resize', function() {
		// 设置Iframe窗口大小
		configIframe();
	}).resize();
	
	function configIframe(){
		var $content = $('.layui-body');
		$content.height($(this).height() - 60);
		$content.width($(this).width() - 200); 
		$content.find('iframe').each(function() {
			$(this).height($content.height());
			$(this).width($content.width());
		});
	}
	/**
	 * 添加顶部（header）按钮颜色变换的效果
	 */
	 function vehicleManageIcon (){
		 $('#childPage').attr('src',"${pageContext.request.contextPath}/cacUser/cacUserIndex");
		 
		 var imgSrcStr1 = $("#otherSys").children("img").attr("src");
			imgSrcStr1 = imgSrcStr1.replace('_selected', '');
			$("#otherSys").children("img").attr("src",imgSrcStr1);

			var imgSrcStr2 = $("#userManage").children("img").attr("src");
			imgSrcStr2 = imgSrcStr2.replace('_selected', '');
			$("#userManage").children("img").attr("src",imgSrcStr2);
		 }
	
	 function systemMonitorIcon(){
		 $('#childPage').attr('src',"${pageContext.request.contextPath}/cacRole/cacRoleIndex");
		 
		 var imgSrcStr1 = $("#vehicleManage").children("img").attr("src");
			imgSrcStr1 = imgSrcStr1.replace('_selected', '');
			$("#vehicleManage").children("img").attr("src",imgSrcStr1);

			var imgSrcStr2 = $("#otherSys").children("img").attr("src");
			imgSrcStr2 = imgSrcStr2.replace('_selected', '');
			$("#otherSys").children("img").attr("src",imgSrcStr2);
		 }
	 
	 function otherSysIcon(){
		 $('#childPage').attr('src',"${pageContext.request.contextPath}/cacInto/intoUpdatePassword");
	 
		 var imgSrcStr1 = $("#userManage").children("img").attr("src");
			imgSrcStr1 = imgSrcStr1.replace('_selected', '');
			$("#userManage").children("img").attr("src",imgSrcStr1);

			var imgSrcStr2 = $("#vehicleManage").children("img").attr("src");
			imgSrcStr2 = imgSrcStr2.replace('_selected', '');
			$("#vehicleManage").children("img").attr("src",imgSrcStr2);
		 }

	 $(document).ready(function(){
		// 设置Iframe窗口大小
		configIframe();

		var element={};
		//初始化获取左侧菜单数据
		$.ajax({
	        type:'POST',
	        dataType:'json',
	        url: "${pageContext.request.contextPath}/cacInto/menuTree.do",
	        data:{"parentId":-1},
	        success:function(data){
	        	renderLeftMenu(data.children);

	        	//添加左侧菜单图标动态效果
    			changeLeftMenuIconStyle();
    			
				//控制header图标
    			$(".headerMenu").click(function(){
    				$(this).parent(".layui-nav-item").addClass("layui-nav-itemed");
    				$(this).parent(".layui-nav-item").siblings().removeClass("layui-nav-itemed");
    				var sameLevelLi = $(this).parent(".layui-nav-item").siblings().length;

    				//清空左侧菜单
    				$("#sideMenu").children().remove(); 
    				
     				//重新渲染左侧菜单
        			renderLeftMenu(data.children);
        			element.init();
        			
        			//添加左侧菜单图标动态效果
        			changeLeftMenuIconStyle();
        			});
		        }
	    	});

		//控制左侧菜单图标动态样式
		function changeLeftMenuIconStyle(){
			//鼠标移入一级主菜单
        	$(".layui-nav-item a[class='leftMenu'], .layui-nav-item a[class='headerMenu']").mouseenter(function(){
        		var	imgSrcStr = $(this).children("img").attr("src");
        	 		imgSrcStr = imgSrcStr.replace('_selected', '');
        	 		var imgSrcBegin = imgSrcStr.substring(0,imgSrcStr.indexOf("."));
	        	 	var imgSrcEnd = imgSrcStr.substring(imgSrcStr.indexOf("."));

	        	 	var	imgSrcStrSelected = imgSrcBegin+"_selected"+imgSrcEnd;
	        		$(this).children("img").attr("src",imgSrcStrSelected);
    		});
    		
        	//鼠标移出一级主菜单
			$(".layui-nav-item a[class='leftMenu'], .layui-nav-item a[class='headerMenu']").mouseleave(function(){
				var	imgSrcStrLeave = $(this).children("img").attr("src");
				var judgeExtendState = $(this).parent(".layui-nav-item").is('.layui-nav-itemed');

				if(!judgeExtendState){
					var imgSrcStrII = imgSrcStrLeave.replace('_selected', '');
	        		$(this).children("img").attr("src",imgSrcStrII);
					}
        		
    			});
			}
	    
		//渲染左侧菜单	
	    function renderLeftMenu(obj){
				var dynamicMenuLi="";
				var dynamicMenuLiBegin = "<li class='layui-nav-item'>";
				var dynamicMenuLiEnd = "</li>"
				var dynamicMenuDlBegin = "<dl class='layui-nav-child'>"
				var dynamicMenuDlEnd = "</dl>"
				var dynamicMenuDl="";
				var dynamicMenuDd = "<dd><a href='javascript:;'>列表一</a></dd>"
			for (var i = 0; i < obj.length; i++) {
				//判断是否只有一级菜单，并且新增一级菜单的链接地址
				//var oneLevelMenuHasUrlResult  = null!=obj[i].children && obj[i].children.length>0 && (null == obj[i].children.menuUrl||"" == obj[i].children.menuUrl);
				
				var oneLevelMenuHasUrlResult  = null!=obj[i].children && obj[i].children.length>0 ;
				if(oneLevelMenuHasUrlResult){
					dynamicMenuLi = dynamicMenuLi+dynamicMenuLiBegin+"<a class='leftMenu' href='javascript:;' ><img alt='' src='${pageContext.request.contextPath}/images/menu/"+obj[i].iconCls+"'>"+obj[i].text+"</a>";
				}else{
					dynamicMenuLi = dynamicMenuLi+dynamicMenuLiBegin+"<a class='leftMenu' href='javascript:;' myurl='"+obj[i].menuUrl+"' onclick='jumpChildPage(this);'><img alt='' src='${pageContext.request.contextPath}/images/menu/"+obj[i].iconCls+"'>"+obj[i].text+"</a>";
					}
				
				 if(null!=obj[i].children&&obj[i].children.length>0){

					 dynamicMenuLiBegin = "<li class='layui-nav-item'>";
					 dynamicMenuLiEnd = "</li>"
					 dynamicMenuDlBegin = "<dl class='layui-nav-child'>"
					 dynamicMenuDlEnd = "</dl>"
					 dynamicMenuDl="";
					 dynamicMenuDd = "<dd><a href='javascript:;'>列表一</a></dd>"
					for (var j = 0; j < obj[i].children.length; j++) {
						
						dynamicMenuDlBegin= dynamicMenuDlBegin+"<dd><a href='javascript:void(0);' myurl='"+obj[i].children[j].menuUrl+"' onclick='jumpChildPage(this);'>"+obj[i].children[j].text+"</a></dd>";
						}
					dynamicMenuDl=dynamicMenuDlBegin+dynamicMenuDlEnd
					
					dynamicMenuLi = dynamicMenuLi+dynamicMenuDl+dynamicMenuLiEnd;
					}
				} 

				
				$("#sideMenu").append(dynamicMenuLi);

				layui.use('element', function(){
    				  element = layui.element;
    				});
		    }
		});
	//左侧子菜单菜点击事件
	function jumpChildPage(url){
		//重置顶部菜单样式
		resetHeaderMenuStyle();

		//给左侧菜单动态添加url
		var myurl="";
			for (var i = 0; i < url.attributes.length; i++) {
				if('myurl'==url.attributes[i].name){
					myurl = url.attributes[i].nodeValue;
					}
			}
		var urlResu = "${pageContext.request.contextPath}/";
		$("#childPage").attr("src",urlResu+myurl) 

		}
	//重置顶部菜单样式
	function resetHeaderMenuStyle(){
		var otherSysSrcStr = $("#otherSys").children("img").attr("src");
		otherSysSrcStr = otherSysSrcStr.replace('_selected', '');
		$("#otherSys").children("img").attr("src",otherSysSrcStr);
		var vehicleManageSrcStr = $("#vehicleManage").children("img").attr("src");
		vehicleManageSrcStr = vehicleManageSrcStr.replace('_selected', '');
		$("#vehicleManage").children("img").attr("src",vehicleManageSrcStr);
	 	var userManageSrcStr = $("#userManage").children("img").attr("src");
	 	userManageSrcStr = userManageSrcStr.replace('_selected', '');
		$("#userManage").children("img").attr("src",userManageSrcStr);
		$(".menuTop li").attr("class", "layui-nav-item");
		}
	//退出系统
	function exitSystem(){
		window.location.href="${pageContext.request.contextPath}/cacInto/cacLogout.do";
		}
</script>
</head>
  
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">CAC平台</div>
    <!--顶部导航栏 -->
    <ul class="layui-nav layui-layout-left menuTop">
      <li class="layui-nav-item"><a href="javascript:vehicleManageIcon();" id="vehicleManage" class="headerMenu"><img alt="" src="${pageContext.request.contextPath}/images/menu/safe_manage.png">用户管理</a></li>
      <li class="layui-nav-item"><a href="javascript:systemMonitorIcon();" id="userManage" class="headerMenu"><img alt="" src="${pageContext.request.contextPath}/images/menu/safe_manage.png">角色管理</a></li>
      <li class="layui-nav-item">
        <a href="javascript:otherSysIcon();" id="otherSys" class="headerMenu"><img alt="" src="${pageContext.request.contextPath}/images/menu/safe_manage.png">修改密码</a>
      </li>
    </ul>
    
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item"><img alt="" src="${pageContext.request.contextPath}/images/menu/user_icon.png">
        ${currentUser.userName}
        <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
      </li>
      <li class="layui-nav-item"><a href="javascript:exitSystem();">退出</a></li>
    </ul>
  </div>
  
  
  <!--左侧导航栏 -->
  <div class="layui-side ">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree menuLeft"  lay-filter="side" id="sideMenu">
      
      </ul>
    </div>
  </div>
  
  <div class="layui-body" style="overflow: hidden;">
    <!-- 内容主体区域 -->
    <div >
    	<iframe id="childPage" src="" frameborder="0"></iframe>
    </div>
  </div>
</div>

</body>
</html>
