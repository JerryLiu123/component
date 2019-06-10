package com.lgh.sys.manage.server;

/**
 * 
 * @ClassName: SaveTonek
 * @Description: token操作接口
 * @Author lizhiting
 * @DateTime May 16, 2019 2:56:08 PM
 */
public interface TokenOperation {
	/**
	 * 
	 * @Title: putToken
	 * @Description: 保存Tonen
	 * @Author lizhiting
	 * @DateTime May 16, 2019 2:56:19 PM
	 * @param id
	 * @param token
	 */
	public void putToken(String id, String token);
	
	/**
	 * 
	 * @Title: deleteToken
	 * @Description: 删除Token
	 * @Author lizhiting
	 * @DateTime May 16, 2019 2:56:47 PM
	 * @param id
	 */
	public void deleteToken(String id);
	
	/**
	 * 
	 * @Title: containToken
	 * @Description: 验证Tonken时候存在
	 * @Author lizhiting
	 * @DateTime May 16, 2019 2:57:27 PM
	 * @param id
	 * @param token
	 * @return
	 */
	public boolean containToken(String id, String token);
	
	/**
	 * 
	 * @Title: getToken
	 * @Description:  获得Token
	 * @Author lizhiting
	 * @DateTime May 16, 2019 4:32:35 PM
	 * @param id
	 * @return
	 */
	public String getToken(String id);
	
	/**
	 * 
	 * @Title: getTokenSize
	 * @Description: 获得所有的Token个数
	 * @Author lizhiting
	 * @DateTime May 30, 2019 9:38:35 AM
	 * @return
	 */
	public int getTokenSize();
}
