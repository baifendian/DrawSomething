package com.bfd.util;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/** 
 * <p>文件名称: HttpUtils.java</p>
 * 
 * <p>文件功能: 发送http请求</p>
 *
 * <p>编程者: 拜力文</p>
 * 
 * <p>初作时间: 2014-11-20 下午2:21:28</p>
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
public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024), Executors.defaultThreadFactory(), new DiscardOldestPolicy());

	public static JSONObject sendTokenValidateRequest(String url, String jsonData) throws Exception {
		url += "api/tokenValidate";
		return JSONObject.parseObject(sendDispatchPostRequest(url, jsonData));
	}

	public static String sendDispatchPostRequest(String url, String jsonData) throws Exception {
		String[] split = url.split(":");
		if (split[0].equals("https")) {
			return sendHttpsPostRequest(url, jsonData);
		} else {
			return sendHttpPostRequest(url, jsonData);

		}
	}

	/**
	 * Description：发送http post请求
	 * 
	 * @param url
	 * @param jsonData
	 * @return void
	 * @throws Exception 
	 **/
	public static String sendHttpPostRequest(String url, String jsonData) throws Exception {
		String result = null;
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpclient = builder.build();
		HttpPost httpPost = new HttpPost();
		URI uri = null;
		StringEntity entity = new StringEntity(jsonData);
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		uri = new URI(url);
		httpPost.setURI(uri);
		httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.setEntity(entity);

		HttpResponse response = httpclient.execute(httpPost);// 发起请求
		int resultCode = response.getStatusLine().getStatusCode();
		logger.debug("result code" + resultCode);
		HttpEntity responseEntity = response.getEntity();
		result = EntityUtils.toString(responseEntity);
		logger.debug("idmp reponse" + result);
		httpclient.close();
		return result;
	}

	/**
	 * Description：发送http post请求
	 * 
	 * @param url
	 * @param jsonData
	 * @return void
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 **/
	public static String sendHttpsPostRequest(String url, String jsonData) throws ClientProtocolException, Exception {
		String result = null;
		CloseableHttpClient httpclient = createSSLClientDefault();
		HttpPost httpPost = new HttpPost();
		URI uri = null;
		StringEntity entity = new StringEntity(jsonData);
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		uri = new URI(url);
		httpPost.setURI(uri);
		httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.setEntity(entity);

		HttpResponse response = httpclient.execute(httpPost);// 发起请求
		int resultCode = response.getStatusLine().getStatusCode();
		logger.debug("result code" + resultCode);
		HttpEntity responseEntity = response.getEntity();
		//		FileOutputStream os  = new FileOutputStream("d:/a.png");
		//		responseEntity.writeTo(os );
		//		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
		//		outputStreamWriter.flush();
		result = EntityUtils.toString(responseEntity);
		logger.debug("idmp reponse" + result);
		httpclient.close();
		return result;

	}

	/**
	 * Description：发送http请求，无响应
	 * 
	 * @param url
	 * @param jsonData
	 * @return void
	 **/
	public static void sendHttpRequest(String url, String jsonData) {
		HttpClientBuilder builder = HttpClientBuilder.create();
		final CloseableHttpClient httpclient = builder.build();
		final HttpPost httpPost = new HttpPost();
		URI uri = null;
		try {
			uri = new URI(url);
			httpPost.setURI(uri);
			httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.setHeader("msg", jsonData);
			executor.submit(new Runnable() {

				public void run() {
					try {
						logger.debug("开始发送http请求");
						HttpResponse response = httpclient.execute(httpPost);// 发起请求
						HttpEntity responseEntity = response.getEntity();
						EntityUtils.consume(responseEntity);
						httpclient.close();
						logger.debug("完成发送http请求");
					} catch (Exception e) {
						logger.error("发生错误", e);
					}

				}
			});

		} catch (Exception e) {
			logger.error("发生错误", e);
		}

	}

	/**
	 * Description：发送http get 请求
	 * 
	 * @param url
	 * @param msg
	 * @return
	 * @return JSONObject
	 * @author 拜力文
	 **/
	public static JSONObject sendHttpGetRequest(String url, JSONObject msg) {

		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpclient = builder.build();
		Iterator<String> iterator = msg.keySet().iterator();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(url).append("?");
		for (; iterator.hasNext();) {
			String next = iterator.next();
			String value = msg.getString(next);
			stringBuffer.append(next).append("=").append(value).append("&");
		}
		String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
		String conResult = null;
		HttpGet httpGet = new HttpGet(substring);
		try {
			HttpResponse response = httpclient.execute(httpGet);// 发起请求
			conResult = EntityUtils.toString(response.getEntity());// 获得响应结果
			httpclient.close();// 关闭
		} catch (Exception e) {
			logger.error("发生错误", e);
		}
		return JSON.parseObject(conResult);
	}

	/**
	 * Description：发送http get 请求
	 * 
	 * @param url
	 * @param msg
	 * @return
	 * @return JSONObject
	 * @author 拜力文
	 **/
	public static JSONObject sendHttpsGetRequest(String url, JSONObject msg) {

		CloseableHttpClient httpclient = createSSLClientDefault();
		Iterator<String> iterator = msg.keySet().iterator();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(url).append("?");
		for (; iterator.hasNext();) {
			String next = iterator.next();
			String value = msg.getString(next);
			stringBuffer.append(next).append("=").append(value).append("&");
		}
		String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
		String conResult = null;
		HttpGet httpGet = new HttpGet(substring);
		try {

			HttpResponse response = httpclient.execute(httpGet);// 发起请求
			conResult = EntityUtils.toString(response.getEntity());// 获得响应结果
			httpclient.close();// 关闭
		} catch (Exception e) {
			logger.error("发生错误", e);
			e.printStackTrace();
		}
		return JSON.parseObject(conResult);
	}

	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	public static String getRequestMsg(JSONObject msg) {
		Iterator<String> iterator = msg.keySet().iterator();
		StringBuffer stringBuffer = new StringBuffer();
		for (; iterator.hasNext();) {
			String next = iterator.next();
			String value = msg.getString(next);
			stringBuffer.append(next).append("=").append(value).append("&");
		}
		String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
		return substring;
	}

	public static void main(String[] args) throws Exception {
		String url = "https://kyfw.12306.cn/otn/login/init";
		HttpUtils.sendDispatchPostRequest(url, "");

	}
}
