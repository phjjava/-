package com.jp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.jp.common.ConstantUtils;

public class UploadUtil {
	
	public static String fileUpload(List<File> files, List<String> fileNames) throws FileNotFoundException{
		FilePart filePart=null;
		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);
			String filename = fileNames.get(i);
			filePart = new CustomFilePart(filename, file);
			filePart.setCharSet("utf-8");
			//parts[i] = part;
		}
		Part[] parts = {filePart};
		String result = null;
		//-- 指定URL "http://localhost:8888/fileupload/FileUploadServlet";
		//String targetURL = ConfigLoader.config.getProperty("uploadURL");
		String targetURL = "";
//		File targetFile = new File("G:\\photo\\1.jpg"); //-- 指定上传文件
//	    File targetFile1 = new File("G:\\photo\\2.jpg");
	    PostMethod filePost = new PostMethod(targetURL);
	    
	    try {
	    // 通过以下方法可以模拟页面参数提交
	    // filePost.setParameter("name", "中文");
	    // filePost.setParameter("pass", "1234");
	    //Part[] parts = { new FilePart(targetFile.getName(), targetFile),new FilePart(targetFile1.getName(), targetFile1) };
	    filePost.setRequestEntity(new MultipartRequestEntity(parts,
			   filePost.getParams()));
	    
	    HttpClient client = new HttpClient();
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(
			   5000);
	    
	    int status = client.executeMethod(filePost);
	    if (status == HttpStatus.SC_OK) {
		   System.out.println("上传成功");
	    
		   result = new String(filePost.getResponseBodyAsString().getBytes("utf-8")); 
		   System.out.println(result);
		   // 上传成功
	    } else {
		   System.out.println("上传失败");
		   result = new String(filePost.getResponseBodyAsString().getBytes("utf-8")); 
		   // 上传失败
	    }
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    	result ="{\"result\": 1,"
				 +"\"title\": \"文件上传\","
				 +"\"msg\": \"上传失败\","
				 +"\"data\": {"
				 +"}}";
	    } finally {
	    	filePost.releaseConnection();
	    }
	    
		return result;
	}
	
     public static String webFileUpload(List<File> files, List<String> fileNames, String url ,String companyid,String createid,String createname) throws FileNotFoundException, UnsupportedEncodingException{
		//Part[] parts = new Part[files.size()];
		String filename="";
		//文件
		FilePart filePart=null;
		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);
			filename = fileNames.get(i);
			filePart = new CustomFilePart(filename, file);
			filePart.setCharSet("utf-8");
			//parts[i] = part;
		}
		//带中文的参数
		StringPart urlPart = new StringPart("url",url,"utf-8");
		//带中文的参数
		StringPart urlPart2 = new StringPart("companyid",companyid,"utf-8");
		//带中文的参数
		StringPart urlPart3 = new StringPart("filename",filename,"utf-8");
		//带中文的参数
		StringPart urlPart4 = new StringPart("createid",createid,"utf-8");
		//带中文的参数
		StringPart urlPart5 = new StringPart("createname",createname,"utf-8");
		
		Part[] parts = {urlPart, urlPart2, urlPart3, urlPart4, urlPart5, filePart};
		String result = null;
		//-- 指定URL 
		//String targetURL = ConfigLoader.config.getProperty("webUploadURL");
		String targetURL = "http://192/..168.0.66:8080/fileupload/FileUploadServlet";
	    PostMethod filePost = new PostMethod(targetURL);
	    try {
	    // 通过以下方法可以模拟页面参数提交
	    filePost.setRequestEntity(new MultipartRequestEntity(parts,
			   filePost.getParams()));
	    HttpClient client = new HttpClient();
	    client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
	    client.getParams().setContentCharset("utf-8");
//	    client.getHttpConnectionManager().getParams().setConnectionTimeout(
//			   5000);
	    
	    int status = client.executeMethod(filePost);
	    if (status == HttpStatus.SC_OK) {
		   System.out.println("上传成功");
	    
		   result = new String(filePost.getResponseBodyAsString().getBytes("utf-8")); 
		   System.out.println(result);
		   // 上传成功
	    } else {
		   System.out.println("上传失败");
		   result ="{\"result\": 1,"
					 +"\"title\": \"文件上传\","
					 +"\"msg\": \"上传失败\","
					 +"\"data\": {"
					 +"}}";
		   // 上传失败
	    }
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    	result ="{\"result\": 1,"
				 +"\"title\": \"文件上传\","
				 +"\"msg\": \"上传失败\","
				 +"\"data\": {"
				 +"}}";
	    } finally {
	    	filePost.releaseConnection();
	    }
	    
		return result;
	}
	/**
	 * 群组上传文件
	 * @author CZY
	 * @param files
	 * @param fileNames
	 * @param loginId
	 * @param groupid
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static String groupFileUpload(List<File> files, List<String> fileNames, String loginId ,String groupid) throws FileNotFoundException, UnsupportedEncodingException{
		//Part[] parts = new Part[files.size()];
		String filename="";
		//文件
		FilePart filePart=null;
		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);
			filename = fileNames.get(i);
			filePart = new CustomFilePart(filename, file);
			filePart.setCharSet("utf-8");
			//parts[i] = part;
		}
		//带中文的参数
		StringPart urlPart = new StringPart("loginId",loginId,"utf-8");
		//带中文的参数
		StringPart urlPart2 = new StringPart("groupid",groupid,"utf-8");
		//带中文的参数
		StringPart urlPart3 = new StringPart("filename",filename,"utf-8");
		
		Part[] parts = {urlPart, urlPart2, urlPart3, filePart};
		String result = null;
		//-- 指定URL 
		//String targetURL = ConfigLoader.config.getProperty("taskUploadURL");
		String targetURL = "";
	    PostMethod filePost = new PostMethod(targetURL);
	    try {
	    // 通过以下方法可以模拟页面参数提交
	    filePost.setRequestEntity(new MultipartRequestEntity(parts,
			   filePost.getParams()));
	    HttpClient client = new HttpClient();
	    client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
	    client.getParams().setContentCharset("utf-8");
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(
			   5000);
	    
	    int status = client.executeMethod(filePost);
	    if (status == HttpStatus.SC_OK) {
		   System.out.println("上传成功");
	    
		   result = new String(filePost.getResponseBodyAsString().getBytes("utf-8")); 
		   System.out.println(result);
		   // 上传成功
	    } else {
		   System.out.println("上传失败");
		   result = new String(filePost.getResponseBodyAsString().getBytes("utf-8")); 
		   // 上传失败
	    }
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    	result ="{\"result\": 1,"
				 +"\"title\": \"文件上传\","
				 +"\"msg\": \"上传失败\","
				 +"\"data\": {"
				 +"}}";
	    } finally {
	    	filePost.releaseConnection();
	    }
	    
		return result;
	}
	/**
	 * 任务上传
	 * @author CZY
	 * @param files
	 * @param fileNames
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static String taskFileUpload(List<File> files, List<String> fileNames) throws FileNotFoundException, UnsupportedEncodingException{
		//Part[] parts = new Part[files.size()];
		String filename="";
		//文件
		FilePart filePart=null;
		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);
			filename = fileNames.get(i);
			filePart = new CustomFilePart(filename, file);
			filePart.setCharSet("utf-8");
			//parts[i] = part;
		}
		//带中文的参数
		StringPart urlPart2 = new StringPart("filename",filename,"utf-8");
		Part[] parts = {urlPart2, filePart};
		String result = null;
		//-- 指定URL 
		//String targetURL = ConfigLoader.config.getProperty("taskUploadURL");
		String targetURL = ConstantUtils.JIAPU_UPLOAD_URL;
	    PostMethod filePost = new PostMethod(targetURL);
	    try {
		    // 通过以下方法可以模拟页面参数提交
		    filePost.setRequestEntity(new MultipartRequestEntity(parts,
				   filePost.getParams()));
		    HttpClient client = new HttpClient();
		    client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		    client.getParams().setContentCharset("utf-8");
		    client.getHttpConnectionManager().getParams().setConnectionTimeout(
				   5000);
		    
		    int status = client.executeMethod(filePost);
		    if (status == HttpStatus.SC_OK) {
			   System.out.println("上传成功");
		    
			   result = new String(filePost.getResponseBodyAsString().getBytes("utf-8")); 
	//		   String result1= new String(result.getBytes("ISO8859_1"),"utf-8");
	//		   System.out.println(result1);
			   System.out.println(result);
			   // 上传成功
		    } else {
		       result = new String(filePost.getResponseBodyAsString().getBytes("utf-8")); 
			   System.out.println("上传失败");
			   // 上传失败
		    }
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    	result ="{\"result\": 1,"
				 +"\"title\": \"文件上传\","
				 +"\"msg\": \"上传失败\","
				 +"\"data\": {"
				 +"}}";
	    } finally {
	    	filePost.releaseConnection();
	    }
	    
		return result;
	}
	public static void main(String args[]) throws FileNotFoundException {
		List<File> files = new ArrayList<File>();
		List<String> fileNames = new ArrayList<String>();
		
		files.add(new File("G:\\photo\\1.jpg"));
		files.add(new File("G:\\photo\\2.jpg"));
		
		fileNames.add("1.jpg");
		fileNames.add("2.jpg");
		
		String fileUpload = fileUpload(files, fileNames);
		
		System.out.println(fileUpload);
	}
}