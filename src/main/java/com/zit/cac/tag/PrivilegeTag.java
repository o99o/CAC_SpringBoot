package com.zit.cac.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zit.cac.util.StringUtil;

/**
 *@author: o99o
 *@date: 2015-8-5下午04:47:18
 *@version:
 *@description：
 */
public class PrivilegeTag extends TagSupport {

	private static final long serialVersionUID = -532517444654109642L;
	/**
	 *  对应Attribute,加上set方法。
	 */
	private String operationId; 
	/**
	 *  按钮名（添加）
	 */
	private String name;     
	/**
	 *  样式
	 */
	private String clazz;     
	/**
	 *  图标
	 */
	private String iconCls;   
	/**
	 *  点击事件
	 */
	private String onClick;   
	/**
	 *  layUI事件
	 */
	private String layEvent;  
	
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClazz(String classes) {
		this.clazz = classes;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	
	public void setLayEvent(String layEvent) {
		this.layEvent = layEvent;
	}

	/**
	 * 解析标签，形成原有的a标签
	 * <a href="javascript:reserveRole()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	 */
	@Override
	public int doStartTag() throws JspException {
		String currentOperationIds = (String) pageContext.getSession().getAttribute("currentOperationIds");
		if (StringUtil.isNotEmpty(currentOperationIds) && 
				StringUtil.existStrArr(operationId, currentOperationIds.split(","))) {
			StringBuffer sb = new StringBuffer();
			sb.append("<a href=\"javascript:");
			sb.append(onClick + "\"");
			sb.append("class=\""+clazz+"\"");
			sb.append("iconCls=\""+iconCls+"\"");
			sb.append("lay-event=\""+layEvent+"\"");
			sb.append("plain=\"true\" >");
			sb.append(name +"</a>");
			try {
				pageContext.getOut().write(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return EVAL_PAGE;
		}
		// 跳过body,body部分不会显示
		return SKIP_BODY; 
		/* 设置默认值 */
	}
	
}
