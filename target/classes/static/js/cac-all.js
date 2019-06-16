// 通用js和css
var appRootPath;

(function (){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	/*var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	appRootPath = prePath+postPath;*/
	appRootPath="";

	var jsHeader = "<script type='text/javascript' src='" + appRootPath + "/";
	var jsFooter = "'></script>";
	document.write(jsHeader + "common/js/jquery-1.12.3.js" + jsFooter);
	document.write(jsHeader + "common/js/common.js" + jsFooter);
	document.write(jsHeader + "layui/src/layui.js" + jsFooter);
	document.write(jsHeader + "layui/src/layui-xtree.js" + jsFooter);
	document.write(jsHeader + "common/js/user_input_validation_util.js" + jsFooter);
	document.write(jsHeader + "common/js/page_util.js" + jsFooter);
	
	var cssHeader = "<link rel='stylesheet' type='text/css' href='" + appRootPath + "/";
	var cssFooter = "'></link>";
	document.write(cssHeader + "layui/src/css/layui_1366.css" + cssFooter);
	document.write(cssHeader + "common/js/common.css" + cssFooter);
})();
