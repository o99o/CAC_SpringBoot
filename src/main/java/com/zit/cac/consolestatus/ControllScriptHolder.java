package com.zit.cac.consolestatus;

import java.net.URISyntaxException;

/**
 * author:h
 * create time:下午03:52:18
 * 
 */
public class ControllScriptHolder {
	
	private static ControllScriptHolder scriptHolder = null;
	
	private String workingDir;
	
	public static ControllScriptHolder getSingleton() {
		if (scriptHolder == null) {
			scriptHolder = new ControllScriptHolder();
		}
		return scriptHolder;
	}
	
	
	public String getWorkingDir() {
		if (workingDir == null) {
			String jarPath = null;
			try {
				jarPath = this.getClass().getProtectionDomain().getCodeSource()
						.getLocation().toURI().getPath();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			workingDir = getRealDirPath(jarPath);
		}
		return workingDir;
	}

	private String getRealDirPath(String rawPath) {
		if (rawPath.endsWith("tomcat.jar")) {
			int index = rawPath.lastIndexOf("tomcat.jar");
			StringBuffer pathBuffer = new StringBuffer(rawPath.substring(0,
					index));
			return pathBuffer.toString();
		} else {
			return rawPath;
		}
	}
	
	// 获得tomcat/lib路径
	public String getTomcatLibPath() {
		String workingDir=getWorkingDir();
		String res=workingDir.substring(0, workingDir.indexOf("webapps"))+"lib/";
		return res;
	}
	
	public static void main(String[] args) {
		String str="D:/zlits_cac333/server/tomcat/webapps/cac333/WEB-INF/classes/";
		String res=str.substring(0, str.indexOf("webapps"))+"lib/";
		System.out.println("res:"+res);
	}
}
