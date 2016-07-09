package com.bfd.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author lljqiu
 *
 */
public class ReadPropertiesUtil {

    private static Properties p = null;

    /** 
    * Description：
    *   获取properties 中的值
    *   <pre>
    *       proFile 是properties文件路径 (不需要后缀)
    *           例如：config/cms
    *       key 是properties  所对应的键
    *       返回，根据key获取的值
    *   </pre>
    * @author name：lljqiu
     **/
    public static Object get(String proFile,String key) {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(proFile+".properties");
            p = new Properties();
            p.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println( (String) ReadPropertiesUtil.get("config/cms", "wxtoken"));
    }

}
