package com.bfd.guesspicture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;

public class Utils
{
  public static String getSuffix(String name){
    if(name==null || name.trim().length()==0 ){
      return "";
    }
    int index = name.indexOf(".");
    if(index<0){
      return "jpg";
    }
    String rs = name.substring(index+1).trim();
    return rs;
  }
  
  public static String Bitmap2StrByBase64(Bitmap bit){  
    ByteArrayOutputStream bos=new ByteArrayOutputStream();  
    bit.compress(CompressFormat.JPEG, 40, bos);//参数100表示不压缩  
    byte[] bytes=bos.toByteArray();  
    return Base64.encodeToString(bytes, Base64.DEFAULT);  
 }  
  
  /** 
   * 通过BASE64Decoder解码，并生成图片 
   * @param imgStr 解码后的string 
    
  public boolean string2Image(String imgStr, String imgFilePath) {  
      // 对字节数组字符串进行Base64解码并生成图片  
      if (imgStr == null)  
          return false;  
      try {  
          // Base64解码  
          byte[] b = new BASE64Decoder().decodeBuffer(imgStr);  
          for (int i = 0; i < b.length; ++i) {  
              if (b[i] < 0) {  
                  // 调整异常数据  
                  b[i] += 256;  
              }  
          }  
          // 生成Jpeg图片  
          OutputStream out = new FileOutputStream(imgFilePath);  
          out.write(b);  
          out.flush();  
          out.close();  
          return true;  
      } catch (Exception e) {  
          return false;  
      }  
  }     
  
  */ 
  
  public static void main(String[] args)
  {
    String name = "dfa.jpg";
    System.out.println(getSuffix(name));
  }
}
