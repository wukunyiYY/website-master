package com.tc.website.common.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具
 * @author Unicorn
 */
public class AesUtil {

    public final static String PASSWORD = "itistcsfpassword";

	public static byte[] encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(password.getBytes("utf-8"), "AES"));
            byte[] bytes = cipher.doFinal(content.getBytes("utf-8"));
            return bytes;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
	}  
	
	public static byte[] decrypt(byte[] content, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(password.getBytes("utf-8"), "AES"));
            return cipher.doFinal(content);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
	}  
	
	public static void main(String[] args) {
		String content = "test";  
		String password = "1234567812345678";
		//加密  
		System.out.println("加密前：" + content);  
		byte[] encryptResult = encrypt(content, password);  
		//解密  
		byte[] decryptResult = decrypt(encryptResult,password);  
		System.out.println("解密后：" + new String(decryptResult)); 
	}
}
