package com.tc.website.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 虚拟目录工具
 * @author Jack Yu
 *
 */
public class VDirUtil {

	/**
	 * 创建一个虚拟目录
	 * @param docBase
	 * @param path
	 * @return
	 */
	public static boolean addVirtualDir(String docBase, String path) {
		if(docBase == null || path == null) {
			return false;
		}
		OutputStream out = null;
		try {
			// String tomcatHome = System.getenv("CATALINA_HOME");
			String tomcatHome = System.getenv("CATALINA_BASE");
			if(tomcatHome == null || tomcatHome.equals("") ) {
				return false;
			}
			String fileName = path.replaceFirst("/", "").replace("/", "#");
			File file = new File(tomcatHome + File.separator + "conf" + File.separator + "Catalina" + 
					File.separator +"localhost" + File.separator  + fileName + ".xml");
			if(!file.exists() || !file.isFile()) {
				file.createNewFile();
			}
			String temp = "<Context docBase=\"" + docBase + "\" path=\"" + path + "\" reloadable=\"true\"></Context>";
			out = new FileOutputStream(file);
			out.write(temp.getBytes("utf-8"));
			out.close();
			return true;
		}catch(Exception e) {
			 System.out.println(e.getMessage());
		}finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return false;
	}
	
}
