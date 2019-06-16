package com.zit.cac.util;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
* <p>Title: Jackson2Util</p>
* <p>Description: </p>
* <p>Company: ZIT</p>
* @author lijiangtao
* @date   2018年6月20日
*/
public class Jackson2Util {
	public static MappingJackson2JsonView jsonView()
	{
		MappingJackson2JsonView mjjv=new MappingJackson2JsonView();
		mjjv.setExtractValueFromSingleKeyModel(true);
		return mjjv;
	}
}
