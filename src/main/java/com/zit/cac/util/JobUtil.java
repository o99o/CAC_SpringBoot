package com.zit.cac.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zit.cac.entity.Log;
import com.zit.cac.service.AttachmentService;
import com.zit.cac.service.LogService;

/**
 *@author: o99o
 *@date: 2015-8-13上午11:02:07
 *@version:
 *@description： 定时器
 */
@Component
public class JobUtil {
	
	private Log log;
	@Autowired
	private LogService<Log> logService;
	
	/**
	 * 定时器
	 * 每周三自动备份操作记录日志（星期三晚上2点）
	 */
	@Scheduled(cron = "0 0 2 ? * WED")
	public void backup(){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 *  导出到硬盘
	 * @param handers
	 * @param list
	 * @param excleName
	 */
	@SuppressWarnings("deprecation")
	private void exportExcelToDisk(String[] handers, List<Log> list, String excleName) {
		
		try {
			//创建工作簿
			HSSFWorkbook wb = new HSSFWorkbook();
			//第一个sheet
			HSSFSheet sheet = wb.createSheet("操作记录备份");
			//第一个sheet第一行为标题
			HSSFRow rowFirst = sheet.createRow(0);
			rowFirst.setHeight((short) 500);
			for (int i = 0; i < handers.length; i++) {
				// 设置列宽
				sheet.setColumnWidth((int) i, (int) 4000);
			}
			//写标题了
			for (int i = 0; i < handers.length; i++) {
			    //获取第一行的每一个单元格
			    HSSFCell cell = rowFirst.createCell(i);
			    //往单元格里面写入值
			    cell.setCellValue(handers[i]);
			}
			for (int i = 0;i < list.size(); i++) {
			    //获取list里面存在是数据集对象
			    log = list.get(i);
			    //创建数据行
			    HSSFRow row = sheet.createRow(i+1);
			    //设置对应单元格的值（每行的高度）
			    row.setHeight((short)400);   
			    //"序号","操作人","IP地址","操作时间","操作模块","操作类型","详情"
			    row.createCell(0).setCellValue(i+1);
			    row.createCell(1).setCellValue(log.getUserName());
			    row.createCell(2).setCellValue(log.getIp());
			    row.createCell(3).setCellValue(log.getCreateTime());
			    row.createCell(4).setCellValue(log.getOperation());
			    row.createCell(5).setCellValue(log.getModule());
			    row.createCell(6).setCellValue(log.getContent());
			}
			//写出文件（path为文件路径含文件名）
				OutputStream os;
				String path = this.getClass().getClassLoader().getResource("/").getPath();
				os = new FileOutputStream(new File(path.substring(1,path.length()-16)+"logs"+File.separator+"backup"+File.separator+excleName+".xls"));
				wb.write(os);
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	

}
