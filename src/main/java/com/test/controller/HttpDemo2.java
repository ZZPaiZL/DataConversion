package com.test.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
/**
 * http客户端
 * @author 华为
 *
 */
public class HttpDemo2 {
	/**
	 * post传输map数据
	 * @param url
	 * @param map
	 * @return
	 */
	public static String sendPostDataByMap
	(String url,Map<String,Object>map) {
		
		String result = "";
		//创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);
		
		//装填参数
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(map != null) {
			//循环添加参数
			for(Entry<String,Object> entry : map.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), (String)entry.getValue()));
			}
		}
		//输出信息
		System.out.println("---------------"+nameValuePairs.toString());
		try {
			//设置参数到请求对象中
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
			// 设置header信息
	        // 指定报文头Content-type、User-Agent
	        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
	        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	        //执行请求操作，并拿到结果(同步阻塞)
	        CloseableHttpResponse respose = httpClient.execute(httpPost);
	        //获取结果实体
	        //判断网络连接状态码是否正常（0--200）
	        if(respose.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	        	result = EntityUtils.toString(respose.getEntity(),"utf-8");
	        }
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				//释放连接
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	/**
	 * post方式传输json数据
	 * @param url
	 * @param json
	 * @return
	 */
	public static String sendPostDataByJson(String url,String json) {
		String result = "";
		//创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建post方式请求对象
		HttpPost httppost = new HttpPost(url);
		
		
		try {
			//设置参数到请求对象中
			StringEntity stringEntity = new StringEntity(json,ContentType.APPLICATION_JSON);
			stringEntity.setContentEncoding("utf-8");
			httppost.setEntity(stringEntity);
			
			//执行操作
			 CloseableHttpResponse respose = httpClient.execute(httppost);
		        //获取结果实体
		        //判断网络连接状态码是否正常（0--200）
		        if(respose.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		        	result = EntityUtils.toString(respose.getEntity(),"utf-8");
		        }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	//测试代码，可删除
//	@Test
//	public void testSendPostDataByJson() {
//		String url="http://localhost:8080/httpService/sendPostDataByJson";
//		Map<String,String> map=new HashMap();
//		map.put("name","曾智平");
//		map.put("city","贵州");
//		
//		String body = sendPostDataByJson(url,JSON.toJSONString(map));
//		System.out.println("相应结果:"+body);
//	}
//	@Test
//	public void testSendPostDataByMap() {
//		String url="http://localhost:8080/httpService/sendPostDataByMap";
//		Map<String,Object> map=new HashMap();
//		map.put("name","曾智平");
//		map.put("city","贵州");
//		String body = sendPostDataByMap(url, map);
//		System.out.println("相应结果:"+body);
//	}
	
	
}
