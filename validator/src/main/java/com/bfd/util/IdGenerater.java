package com.bfd.util;

import java.util.UUID;
/** 
 * <p>文件名称: IdGenerater.java</p>
 * 
 * <p>文件功能: </p>
 *
 * <p>编程者: 拜力文</p>
 * 
 * <p>初作时间: 2016年7月9日 下午12:31:22</p>
 * 
 * <p>版本: version 1.0 </p>
 *
 * <p>输入说明: </p>
 *
 * <p>输出说明: </p>
 *
 * <p>程序流程: </p>
 * 
 * <p>============================================</p>
 * <p>修改序号:</p>
 * <p>时间:	 </p>
 * <p>修改者:  </p>
 * <p>修改内容:  </p>
 * <p>============================================</p>
 */
public class IdGenerater {
	private IdGenerater(){
		
	}
	/**
	 * 使用uuid生成
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
