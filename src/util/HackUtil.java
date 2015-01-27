package util;

import it.sauronsoftware.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import Decoder.BASE64Decoder;

public class HackUtil {

	private static String resource = "MyBatis-Configuration.xml"; 
	
	private static String  STORE_DIR = "D:\\pictures\\" ; 
	
			
	public static SqlSession getSqlSession() throws IOException {
		String resource = "MyBatis-Configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(reader);
        
        SqlSession session = factory.openSession();
        
        return session;
	}
	
	public static String bytesStr2Image(String imgDataStr) {
		String path = null;
        FileOutputStream fout = null;
        
        if (imgDataStr == null || imgDataStr.length() == 0) {
        	return null;
        }
        
		try {
			// Generate file name
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
			String formatDate = sdf.format(new Date());
			String fileName = formatDate + ".jpg";
			
			// Convert bytes string into image and store it		
	        byte[] data = new BASE64Decoder().decodeBuffer(imgDataStr);   //对android传过来的图片字符串进行解码   
	        File destDir = new File(STORE_DIR);
	        if(!destDir.exists()) 
	        	destDir.mkdir();  
	        fout = new FileOutputStream(new File(destDir,fileName));   //保存图片
	        fout.write(data);  
	        fout.flush();  
	        fout.close();  
	        path = STORE_DIR+fileName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
    }
	
	public static String image2BytesStr(String filePath) {
		String bytesStr = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			byte[] buffer = new byte[1024];  
	        int count = 0;  
	        while((count = fis.read(buffer)) >= 0) {  
	            baos.write(buffer, 0, count);  
	        }  
	        bytesStr = new String(Base64.encode(baos.toByteArray()));  //进行Base64编码  
	        //hs.processPicture(uploadBuffer, new Receipt());
	        fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bytesStr;
	}
}
