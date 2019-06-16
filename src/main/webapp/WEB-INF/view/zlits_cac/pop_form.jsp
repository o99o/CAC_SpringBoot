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
<title>弹出层页面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/src/css/layui.css">
<script src="${pageContext.request.contextPath}/layui/src/layui.js" charset="utf-8"></script>
</head>
<body>
<!-- 需要弹出的修改和新增员工界面 -->
	<div class="layui-row" id="popUpdateTest" style="display: none;">
	    <div class="layui-col-md10">
	        <form class="layui-form" lay-filter="formTestFilter" id="addAndUpdateEmployeeForm">
	            <div class="layui-form-item">
	                <label class="layui-form-label">用户名：</label>
	                <div class="layui-input-inline">
	                    <input type="text" name="userName" id="userNameId" class="layui-input">
	                </div>
	                <label class="layui-form-label">密码：</label>
	                <div class="layui-input-inline">
	                	<input type="text" name="password" id="passwordId" class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">角色：</label>
	                <div class="layui-input-inline">
	                    <input type="text" name="roleName" class="layui-input">
	                </div>
	                <div class="layui-input-inline">
	                	<button type="button" onclick="selectRole()" class="layui-btn layui-btn-normal">角色名称</button>
	                </div>
	            </div>
	            <!-- <div class="layui-form-item">
	                <label class="layui-form-label">备注：</label>
	                <div class="layui-input-block">
	                    <textarea placeholder="请输入内容" class="layui-textarea" name="userDescription"></textarea>
	                </div>
	            </div> -->
	            <div class="layui-form-item">
	                <div class="layui-input-block">
	                    <button type="button" class="layui-btn layui-btn-normal">提交</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
</body>
</html>