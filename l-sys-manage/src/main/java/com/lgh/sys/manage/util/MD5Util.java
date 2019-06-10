package com.lgh.sys.manage.util;

import org.springframework.util.DigestUtils;

public class MD5Util {

	private static final String SALT = "!@#$%^&YTRFDXCVGT%$E";
	
	/**
	 * 
	 * @Title: verify
	 * @Description: 验证
	 * @Author lizhiting
	 * @DateTime May 14, 2019 4:42:33 PM
	 * @param text 明文
	 * @param md5 秘文
	 * @return 
	 * @throws Exception
	 */
	public static boolean verify(String text, String md5) throws Exception {
		return verify(text, SALT, md5);
	}
	
	/**
	 * 
	 * @Title: md5
	 * @Description: MD5 加密
	 * @Author lizhiting
	 * @DateTime May 14, 2019 4:41:18 PM
	 * @param text 明文
	 * @return
	 * @throws Exception
	 */
	public static String md5(String text) throws Exception {
		return md5(text, SALT);
	}
	
    /**
     * MD5方法
     * 
     * @param text 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr=DigestUtils.md5DigestAsHex((text + key).getBytes());
        return encodeStr;
    }

    /**
     * MD5验证方法
     * 
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(md5)){
            return true;
        }
        return false;
    }
}
