package com.jp.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {
	private HSSFWorkbook wb = null;
	private HSSFSheet sheet = null;
	private HttpServletResponse servletResponse;
	
	
	public ExcelUtil() {
	}
	public ExcelUtil(HttpServletResponse servletResponse) {
		super();
		this.servletResponse = servletResponse;
	}
	public ExcelUtil(HSSFWorkbook wb, HSSFSheet sheet) {
		super();
		this.wb = wb;
		this.sheet = sheet;
	}

	public HSSFWorkbook getWb() {
		return wb;
	}
	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}
	public HSSFSheet getSheet() {
		return sheet;
	}
	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}
	public HttpServletResponse getServletResponse() {
		return this.servletResponse;
	}

	public static Object loadClassObject(String className) {
		Object o = null;
		try {
			Class clas = Class.forName(className);
			if (clas != null) {
				try {
					o = clas.newInstance();
				} catch (IllegalAccessException ex1) {
					ex1.printStackTrace();
				} catch (InstantiationException ex1) {
					ex1.printStackTrace();
				}
			} else
				o = null;
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return o;
	}


	public void showExcelFile(String excelName, List resultList, List listKey, List listName, String beanName) throws UnsupportedEncodingException {
		String filename = new String((excelName).getBytes(), "ISO8859-1");
		this.servletResponse.setCharacterEncoding("UTF-8");
		this.servletResponse.setContentType("application/vnd.ms-excel");
		this.servletResponse.setHeader("Content-Disposition", "attachment;filename=\"" + filename + ".xls\"");

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 12);
		HSSFCellStyle style = wb.createCellStyle();
		HSSFCellStyle styleContent = wb.createCellStyle();
		HSSFCellStyle styleFomatDouble = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFDataFormat format = wb.createDataFormat();
		createTableHead(sheet, style, font, listName);
		Object o = ExcelUtil.loadClassObject(beanName);
		if (o != null) {
			insertInfo(sheet, styleContent, styleFomatDouble, format, resultList, listKey, beanName);
		}
		try {
			wb.write(this.servletResponse.getOutputStream());
			this.servletResponse.getOutputStream().flush();
			this.servletResponse.getOutputStream().close();
		} catch (IOException ioe) {
		}
	}
	// 制作表头
	public void createTableHead(HSSFSheet sheet, HSSFCellStyle style, HSSFFont font, List listName) {
		style = createStyle(style, HSSFCellStyle.ALIGN_CENTER, font);
		HSSFRow row = sheet.createRow((short) 0);
		int i = 0;
		HSSFCell cell = null;
		for (Iterator it = listName.iterator(); it.hasNext();) {
			while (it.hasNext()) {
				String name = (String) it.next();
				cell = row.createCell((short) i);
				cell.setCellStyle(style);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(name);
				i++;
			}
		}
	}

	// 将信息加入excel表格中
	public void insertInfo(HSSFSheet sheet, HSSFCellStyle styleContent, HSSFCellStyle styleFomatDouble, HSSFDataFormat format, List vos, List listKey, String beanName) {
		int k = 0;
		//列
		HSSFRow row = null;
		//行
		HSSFCell cell = null;
		styleContent = createStyle(styleContent, HSSFCellStyle.ALIGN_LEFT, null);
		styleFomatDouble = createStyle(styleFomatDouble, HSSFCellStyle.ALIGN_RIGHT, null);
		Object o = ExcelUtil.loadClassObject(beanName);
		for (Iterator it = vos.iterator(); it.hasNext();) {
			while (it.hasNext()) {
				int i = 0;
				o = it.next();
				row = sheet.createRow((short) k + 1);
				for (int keyCount = 0; keyCount < listKey.size(); keyCount++) {
					cell = row.createCell((short) keyCount);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					Object obj;
					try {
						obj = o.getClass().getField(listKey.get(keyCount).toString()).getType();
						Object tempObj = o.getClass().getField(listKey.get(keyCount).toString()).get(o);
						if (obj.toString().equals("double") || obj.toString() == "double") {
							styleFomatDouble.setDataFormat(format.getFormat("0.00"));
							cell.setCellStyle(styleFomatDouble);
						}else {
							cell.setCellStyle(styleContent);
						}
						
						Double s= 0.00;
						if (obj.toString().equals("int") || obj.toString() == "int") {
							cell.setCellValue((double) Integer.parseInt(tempObj == null ? "" : o.getClass().getField(listKey.get(keyCount).toString()).get(o).toString()));
						} else if (obj.toString().equals("double") || obj.toString() == "double") {
							cell.setCellValue(Double.parseDouble(tempObj == null ? "" : o.getClass().getField(listKey.get(keyCount).toString()).get(o).toString()));
						} else if(obj.toString().equals("class java.lang.Double")||obj.toString() == "class java.lang.Double"){
							if(tempObj == null){
								s= 0.00;
							}else{
								s=Double.parseDouble(tempObj.toString());
							}
							//cell.setCellValue(s.toString());
							BigDecimal bd = new BigDecimal(s.toString());  
							//System.out.println(bd.toPlainString()); 
							cell.setCellValue(bd.toPlainString());
						}else {
							cell.setCellValue(tempObj == null ? "" : tempObj.toString());
						}
						tempObj = null;
						i++;
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
					}
				}
				k++;
			}
		}
	}
	// excel表格风格设置
	public HSSFCellStyle createStyle(HSSFCellStyle style, short s, HSSFFont font) {
		style.setBorderBottom((short) 1);
		style.setBorderLeft((short) 1);
		style.setBorderRight((short) 1);
		style.setBorderTop((short) 1);
		style.setAlignment(s);
		// style.setWrapText(true);自动换行
		if (font != null) {
			style.setFont(font);
		}
		return style;
	}

	//excelUtil2
	/**
	 * 将Excel读取出的单元格数据类型全部转换成String类型d
	 * @param cell
	 * @return
	 */
	public String getCellContent(HSSFCell cell){
		String res ="";
		if(cell==null)
			return res;
		if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
			res = cell.getRichStringCellValue().getString().trim();
		}else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
			boolean flag = HSSFDateUtil.isCellDateFormatted(cell);
			if(flag){
				Date celldate = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				res = sdf.format(celldate);
			}else{
				res=String.format("%.6f", cell.getNumericCellValue());
				//res = String.valueOf(cell.getNumericCellValue());
			}
		}else{
			res = "";
		}
		return res;
	}
	
	/**
	 * 设置单元格样式
	 * @param wb
	 * @param locked
	 * @param flag
	 * @return
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook wb,boolean locked,int flag){
		if(flag ==0){//标题
			HSSFCellStyle cellStyleG = wb.createCellStyle();		// 创建单元格样式 
			cellStyleG.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 指定单元格居中对齐 
			cellStyleG.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyleG.setWrapText(true);// 指定当单元格内容显示不下时自动换行
			cellStyleG.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellStyleG.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyleG.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//			cellStyleG.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
			HSSFFont font = wb.createFont(); 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗  
			font.setFontName("宋体"); 
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeightInPoints((short) 18);// 设置字体大小
			cellStyleG.setFont(font);
			
			//cellStyleG.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); //设置背景颜色
			//cellStyleG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //设置使用纯色填充
			if(locked)
				cellStyleG.setLocked(false);
			else
				cellStyleG.setLocked(false);
			return cellStyleG;
		}
		if(flag == 1){//标题 只有右边框
			HSSFCellStyle cellStyleG = wb.createCellStyle();		// 创建单元格样式 
			cellStyleG.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 指定单元格居中对齐 
			cellStyleG.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyleG.setWrapText(true);// 指定当单元格内容显示不下时自动换行
//			cellStyleG.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//			cellStyleG.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//			cellStyleG.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//			cellStyleG.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
			HSSFFont font = wb.createFont(); 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗  
			font.setFontName("宋体"); 
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeightInPoints((short) 18);// 设置字体大小
			cellStyleG.setFont(font);
			
			if(locked)
				cellStyleG.setLocked(false);
			else
				cellStyleG.setLocked(false);
			return cellStyleG;
		}
		if(flag == 2){//body
			HSSFCellStyle cellStyle = wb.createCellStyle();			// 创建单元格样式 
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);		// 指定单元格居中对齐 
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyle.setWrapText(true);// 指定当单元格内容显示不下时自动换行
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			HSSFFont font = wb.createFont(); 
//			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//			font.setFontName("宋体"); 
//			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeight((short)200);
			cellStyle.setFont(font);
			if(locked)
				cellStyle.setLocked(false);
			else
				cellStyle.setLocked(false);
			//cellStyle.setLocked(true);
			return cellStyle;
		}
		if(flag == 3){//带颜色
			HSSFCellStyle cellStyle = wb.createCellStyle();				// 创建单元格样式 
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);			// 指定单元格居中对齐 
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyle.setWrapText(true);// 指定当单元格内容显示不下时自动换行
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			if(locked)
				cellStyle.setLocked(false);
			else
				cellStyle.setLocked(false);
			//cellStyle.setLocked(true);
			return cellStyle;
		}
		if(flag == 4){//表头
			HSSFCellStyle cellStyleG = wb.createCellStyle();		// 创建单元格样式 
			cellStyleG.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 指定单元格居中对齐 
			cellStyleG.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyleG.setWrapText(true);// 指定当单元格内容显示不下时自动换行
			cellStyleG.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellStyleG.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyleG.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyleG.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
			HSSFFont font = wb.createFont(); 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗  
			font.setFontName("宋体"); 
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeightInPoints((short) 10);// 设置字体大小
			cellStyleG.setFont(font);
			
			if(locked)
				cellStyleG.setLocked(false);
			else
				cellStyleG.setLocked(false);
			return cellStyleG;
		}
		if(flag ==5){//标题
			HSSFCellStyle cellStyleG = wb.createCellStyle();		// 创建单元格样式 
			cellStyleG.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 指定单元格居中对齐 
			cellStyleG.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyleG.setWrapText(true);// 指定当单元格内容显示不下时自动换行
//			cellStyleG.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//			cellStyleG.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//			cellStyleG.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyleG.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			cellStyleG.setFillBackgroundColor(HSSFColor.WHITE.index);
			HSSFFont font = wb.createFont(); 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗  
			font.setFontName("黑体"); 
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeightInPoints((short) 16);// 设置字体大小
			cellStyleG.setFont(font);
			
			//cellStyleG.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); //设置背景颜色
			//cellStyleG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //设置使用纯色填充
			if(locked)
				cellStyleG.setLocked(false);
			else
				cellStyleG.setLocked(false);
			return cellStyleG;
		}
		if(flag ==6){//二级标题
			HSSFCellStyle cellStyleG = wb.createCellStyle();		// 创建单元格样式 
			cellStyleG.setAlignment(HSSFCellStyle.ALIGN_RIGHT);	// 指定单元格居中对齐 
			cellStyleG.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyleG.setFillBackgroundColor(HSSFColor.WHITE.index);
			cellStyleG.setWrapText(true);// 指定当单元格内容显示不下时自动换行
			cellStyleG.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//			cellStyleG.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//			cellStyleG.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyleG.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
			HSSFFont font = wb.createFont(); 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗  
			font.setFontName("黑体"); 
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeightInPoints((short) 11);// 设置字体大小
			cellStyleG.setFont(font);
			
			//cellStyleG.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); //设置背景颜色
			//cellStyleG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //设置使用纯色填充
			if(locked)
				cellStyleG.setLocked(false);
			else
				cellStyleG.setLocked(false);
			return cellStyleG;
		}
		if(flag ==7){//三级标题
			HSSFCellStyle cellStyleG = wb.createCellStyle();		// 创建单元格样式 
			cellStyleG.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 指定单元格居中对齐 
			cellStyleG.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyleG.setWrapText(true);// 指定当单元格内容显示不下时自动换行
			cellStyleG.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellStyleG.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyleG.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyleG.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
			HSSFFont font = wb.createFont(); 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗  
			font.setFontName("黑体"); 
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeightInPoints((short) 11);// 设置字体大小
			cellStyleG.setFont(font);
			
			//cellStyleG.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); //设置背景颜色
			//cellStyleG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //设置使用纯色填充
			if(locked)
				cellStyleG.setLocked(false);
			else
				cellStyleG.setLocked(false);
			return cellStyleG;
		}
		if(flag ==8){//中文内容
			HSSFCellStyle cellStyleG = wb.createCellStyle();		// 创建单元格样式 
			cellStyleG.setAlignment(HSSFCellStyle.ALIGN_LEFT);	// 指定单元格居左
			cellStyleG.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyleG.setWrapText(true);// 指定当单元格内容显示不下时自动换行
			cellStyleG.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellStyleG.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyleG.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyleG.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
			HSSFFont font = wb.createFont(); 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗  
			font.setFontName("宋体"); 
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeightInPoints((short) 9);// 设置字体大小
			cellStyleG.setFont(font);
			
			//cellStyleG.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); //设置背景颜色
			//cellStyleG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //设置使用纯色填充
			if(locked)
				cellStyleG.setLocked(false);
			else
				cellStyleG.setLocked(false);
			return cellStyleG;
		}
		
		if(flag ==9){//金额内容
			HSSFCellStyle cellStyleG = wb.createCellStyle();		// 创建单元格样式 
			cellStyleG.setAlignment(HSSFCellStyle.ALIGN_RIGHT);	// 指定单元格居右
			cellStyleG.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyleG.setWrapText(true);// 指定当单元格内容显示不下时自动换行
			cellStyleG.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellStyleG.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyleG.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyleG.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
			HSSFFont font = wb.createFont(); 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗  
//			font.setFontName("黑体"); 
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeightInPoints((short) 9);// 设置字体大小
			cellStyleG.setFont(font);
			
			//cellStyleG.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); //设置背景颜色
			//cellStyleG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //设置使用纯色填充
			if(locked)
				cellStyleG.setLocked(false);
			else
				cellStyleG.setLocked(false);
			return cellStyleG;
		}
		if(flag ==10){//中文内容 加粗
			HSSFCellStyle cellStyleG = wb.createCellStyle();		// 创建单元格样式 
			cellStyleG.setAlignment(HSSFCellStyle.ALIGN_LEFT);	// 指定单元格居左
			cellStyleG.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐 
			cellStyleG.setWrapText(true);// 指定当单元格内容显示不下时自动换行
//			cellStyleG.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//			cellStyleG.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyleG.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyleG.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
			HSSFFont font = wb.createFont(); 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗  
			font.setFontName("宋体"); 
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setFontHeightInPoints((short) 9);// 设置字体大小
			cellStyleG.setFont(font);
			
			//cellStyleG.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); //设置背景颜色
			//cellStyleG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //设置使用纯色填充
			if(locked)
				cellStyleG.setLocked(false);
			else
				cellStyleG.setLocked(false);
			return cellStyleG;
		}
		return null;
	}
	
	/**
	* 创建内容单元格 
	* 
	* @param wb HSSFWorkbook
	* @param row HSSFRow
	* @param col short型的列索引
	* @param style 单元格样式
	* @param val 列值
	*/
	public void cteateCell(HSSFWorkbook wb, HSSFRow row, int col,HSSFCellStyle style, String val) {
		HSSFCell cell = row.createCell(col); 
		cell.setCellStyle(style);
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		if(val.matches("-*[0-9]+")){//要设置值为数字类型
			cell.setCellValue(Integer.valueOf(val));
		}else if(val.matches("-*[0-9]+(.[0-9]{1,})+") && val.indexOf("-") < 1){
			cell.setCellValue(Double.valueOf(val));//要设置值为N位小数类型
		}else {
			//字符转类型
			cell.setCellValue(new HSSFRichTextString(val));
		}
		
	}

	/**
	 * 合并单元格
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 */
	public void addMergedRegion(int firstRow, int lastRow, int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}
	
	/**
	 * 获取列名ABCD
	 * @param cell
	 * @return
	 */
	public String getCellName(HSSFCell cell) {
		return CellReference.convertNumToColString(cell.getColumnIndex());
	}
	
	/**
	 * 设置单元格公式
	 * @param cell
	 * @param cellStyle
	 * @param formula
	 */
	public void setCellFormula(HSSFCell cell,HSSFCellStyle cellStyle, String formula) {
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
		cell.setCellFormula(formula);
	}
	
	/**
	 * 设置单元格公式
	 * @param wb
	 * @param cellStyle
	 * @param row
	 * @param col
	 * @param formula
	 */
	public void setCellFormula(HSSFWorkbook wb,HSSFCellStyle cellStyle, int row, int col,String formula){
		HSSFRow hssFRow= sheet.getRow(row);
		if(hssFRow != null){
			HSSFCell cell = hssFRow.createCell(col); 
			if(cell != null){
				cell.setCellStyle(cellStyle);
				cell.setCellFormula(formula);
			}
		}
	}
	
	/**
	 * 保留6位小数并去掉多余的0
	 * @param value
	 * @return
	 */
	public static String toDecimalFormat(String value){
		DecimalFormat format = new DecimalFormat("0.######");
		if (!StringTools.notEmpty(value)) {
			return "0";
		}else {
			return format.format(Double.valueOf(value));
		}
	}
}
