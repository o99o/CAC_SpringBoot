<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传模块 - layui</title>
<script type="text/javascript" 
src="${pageContext.request.contextPath}/js/cac-all.js"></script>
<style>
body{padding: 50px 100px;}
.layui-upload-img{width: 92px; height: 92px; margin: 0 10px 10px 0;}
hr{margin: 30px 0;}
</style>
</head>
<body>

<div class="layui-upload">
  <button type="button" class="layui-btn layui-btn-normal" id="uploadPic">上传图片</button>
  <button type="button" class="layui-btn" id="beginUploadPic">开始上传</button>
  <div class="layui-upload-list layui-inline">
    <img class="layui-upload-img" src="" id="picture">
    <p id="pictureText"></p>
  </div>
</div>
<hr>

<div class="layui-upload">
	<div class="layui-upload">
	  <button type="button" class="layui-btn layui-btn-normal" id="selectFile">上传文件</button>
	  <button type="button" class="layui-btn" id="startUploadFile">开始上传</button>
	  <p id="allFileText"></p>
	</div> 
</div>
<script src="${pageContext.request.contextPath}/layui/src/layui.js"></script>
<script>
layui.use('upload', function(){
  var $ = layui.jquery
  ,upload = layui.upload;

  //上传图片
  var uploadInst = upload.render({
    elem: '#uploadPic'
    ,url: '${pageContext.request.contextPath}/cacFile/fixUpload'
    ,auto: false
    ,bindAction: '#beginUploadPic'
    ,size: 50 //限制文件大小，单位 KB
    ,exts: "jpg|png|gif|bmp|jpeg"
   	,before: function(obj){
   	      obj.preview(function(index, file, result){
   	        $('#picture').attr('src', result); 
   	      });
   	    }
    ,done: function(result){
    	layer.msg("上传成功!",{icon:1});
        
      
    }
    ,error: function(){
      var demoText = $('#pictureText');
      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
      demoText.find('.demo-reload').on('click', function(){
        uploadInst.upload();
      });
    }
  });


//上传文件
  upload.render({
    elem: '#selectFile'
    ,url: '${pageContext.request.contextPath}/cacFile/fixUpload'
    ,accept: 'file'
    ,auto: false
    ,bindAction: '#startUploadFile'
    ,size: 1024*40 //限制文件大小，单位 KB
    ,done: function(result){
    	if(result.msg != true){
        	layer.msg(result.msg, {icon:1});
            }else if(result.msg == true){
            	layer.msg("上传成功!",{icon:1});
                }else{
                	layer.msg("上传失败!",{icon:1});
                    }
    }
  });
  
});
</script>
</body>
</html>