package com.bfd.guesspicture;

/* import相关class */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GuessPicture extends Activity
{
  /* 变量声明
   * uploadFile：要上传的文件路径
   * actionUrl：吱服器勺对应的程序路径 */
  private String uploadFile="";
  private String actionUrl="http://172.24.5.47:12306/validator/androidApi";

  private TextView mText3;
  private Button getPictureButton;
  private EditText editText01;
  
  private Button uploadButton;
  private ImageView myImageView01;
  
  /* activity初始化 */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mText3 = (TextView) findViewById(R.id.myText4);
    mText3.setText("图片类型：");
    editText01 = (EditText) this.findViewById(R.id.EditText01);
    /* 设定mButton的onClick事件处理 */    
    getPictureButton = (Button) findViewById(R.id.myButton);
    getPictureButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        uploadFile();
      }
    });
    
    myImageView01 = (ImageView) findViewById(R.id.myImageView01);
    uploadButton = (Button) findViewById(R.id.myButton01);
    uploadButton.setOnClickListener(new Button.OnClickListener()
    { 
      @Override 
      public void onClick(View arg0) 
      { 
        myImageView01.setImageBitmap(null);
        Intent intent = new Intent(); 
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*"); 
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT); 
        /* 取得相片后返回本画面 */ 
        startActivityForResult(intent, 1);
        }
      }); 
    
  }
  /* 执行post请求 */
  public static HttpResponse post(Map<String,String> params, String url) {

    HttpClient client = new DefaultHttpClient();
    HttpPost httpPost = new HttpPost(url);
    httpPost.addHeader("charset", HTTP.UTF_8);
    httpPost.setHeader("Content-Type",
        "application/x-www-form-urlencoded; charset=utf-8");
    HttpResponse response = null;
    if (params != null && params.size() > 0) {
      List nameValuepairs = new ArrayList();
      for (String key : params.keySet()) {
        nameValuepairs.add(new BasicNameValuePair(key, (String) params
            .get(key)));
      }
      try {
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuepairs,
            HTTP.UTF_8));
        response = client.execute(httpPost);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } catch (ClientProtocolException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    } else {
      try {
        response = client.execute(httpPost);
      } catch (ClientProtocolException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return response;

  }
  
  /* 上传文件吹Server的method */
  private void uploadFile()
  {
    Bitmap rawBitmap1 = BitmapFactory.decodeFile(uploadFile, null);
    String base64 =  Utils.Bitmap2StrByBase64(rawBitmap1);
    Map<String, String> params = new HashMap<String, String>();
    params.put("data", base64);
    params.put("suffix", Utils.getSuffix(uploadFile));
    String type = editText01.getText().toString();
    params.put("type", type);
    HttpResponse response = post(params, actionUrl);
    String content = "";
    if (response != null) {
      try {
        content = EntityUtils.toString(response.getEntity());
      } catch (ParseException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    String message = "";
    try
    {
      JSONObject jsonObject = new JSONObject(content);
      String code = jsonObject.getString("code");
      if("1".equals(code)){
        message = jsonObject.getString("result");
      }else{
        message = "识别有点小问题";
      }
    } catch (JSONException e)
    {
      e.printStackTrace();
    }  
    showDialog(message);
 
  }
  
  /* 从选择图片界面返回主界面 */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    if (resultCode == RESULT_OK)
    { 
      Uri uri = data.getData();
      uploadFile = uri.toString().substring(uri.toString().indexOf("///") + 2);
      ContentResolver cr = this.getContentResolver(); 
      try 
      { 
        Bitmap bitmap = BitmapFactory.decodeStream(cr .openInputStream(uri));
        /* 将Bitmap设定到ImageView */
        myImageView01.setImageBitmap(bitmap);
        } 
      catch (FileNotFoundException e) 
      {
        e.printStackTrace();
        } 
      }
    super.onActivityResult(requestCode, resultCode, data);
    }
  
  /* 显示Dialog的method */
  private void showDialog(String mess)
  {
    new AlertDialog.Builder(GuessPicture.this).setTitle("Message")
     .setMessage(mess)
     .setNegativeButton("确定",new DialogInterface.OnClickListener()
     {
       public void onClick(DialogInterface dialog, int which)
       {          
       }
     })
     .show();
  }
}

