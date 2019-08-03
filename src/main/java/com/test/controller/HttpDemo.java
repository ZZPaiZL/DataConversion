package com.test.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.test.bean.MapBean;

/**
 * 服务端
 * 
 * @author 华为
 *
 */
@RestController
@RequestMapping("/httpService")
public class HttpDemo {

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 模拟
	 * 
	 * @param request
	 * @param respose
	 * @return
	 */
	@RequestMapping(value = "/sendPostDataByMap", method = RequestMethod.POST)
	public String sendPostDataByMap(HttpServletRequest request, HttpServletResponse respose, @RequestBody Object obj) {
		// String param = request.getParameter("param");
//		JSONObject jsonObject = JSONObject.parseObject(requestBody);
		MapBean bean = new MapBean();
		// String result = "调用成功：数据是 " + "name:" + json.getString("name") + " city:" +
		// json.getString("city");
		return null;

	}

	@RequestMapping(value = "/sendPostDataByJson", method = RequestMethod.POST)
	public Map<String,Object> sendPostDataByJson(HttpServletRequest request, HttpServletResponse respose,
			@RequestBody String requestBody) {
		// 以下是将object对象转换成json，并转换成bean对象

		// 以下是字符串转换成json，requestBody是String类型，使用这个得
		JSONObject jsonObject = JSONObject.parseObject(requestBody);
		Map<String,Object> mapList = new HashMap();
		MapBean bean = null;
		String json = "";
		try {
			
			if (jsonObject.containsKey("dataType")) {
				// 单数据情况通过一个工具类ObjectMapper，将json数据转换成javaBean对象
				bean = objectMapper.readValue(jsonObject.toString(), MapBean.class);
				System.out.println("-----------------------------");
				System.out.println("bean类型:" + bean);
				System.out.println("-----------------------------");
				// 将实体类转换成json
				json = objectMapper.writeValueAsString(bean);
				System.out.println("-----------------------------");
				System.out.println("json" + json);
				System.out.println("-----------------------------");

				// 将json数据装换成map对象
				Map<String, Object> map = objectMapper.readValue(jsonObject.toString(), Map.class);
				System.out.println("-----------------------------");
				System.out.println("map" + map.get("name"));
				System.out.println("-----------------------------");
				
				mapList.put("bean",bean);
				mapList.put("json", json);
				mapList.put("map", map);
				
				
			} else {

				// 多数据情况先把每一个数据取出来
				JSONObject param = (JSONObject) jsonObject.get("param");
				JSONObject bpm = (JSONObject) jsonObject.get("bpm");
				System.out.println("这个参数是param:" + param.toString());
				System.out.println("这个参数是bpm:" + bpm.toString());
				// 将param转换成bean
				bean = objectMapper.readValue(param.toString(), MapBean.class);
				System.out.println("-----------------------------");
				System.out.println("bean类型:" + bean);
				System.out.println("-----------------------------");

				// 将bpm转换成map
				Map<String, Object> map = objectMapper.readValue(bpm.toString(), Map.class);
				System.out.println("-----------------------------");
				System.out.println("map类型:" + map.get("zzp") + ":" + map.get("cc"));
				System.out.println("-----------------------------");
				mapList.put("bean",bean);
				mapList.put("map", map);
			}

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapList;
	}

	@RequestMapping("/testJson")
	public String testJson(@RequestBody String params) {
		// 转换成json
		JSONObject json = JSONObject.parseObject(params);
		JSONObject param = null;
		String url = "";
		//判断单数据或者多数据
		if (json.containsKey("url")) {
			url = json.getString("url");
			//增加数据类型得key,单数据添加，多数据不添加
			//json.setDefaultTypeKey("dataType");
			json.put("dataType", "single");
			
			param = json;

		} else {
			param = (JSONObject) json.get("param");
			url = param.getString("url");
			param = json;
		}
		// 调用httpClient中的post方式传输json数据,返回字符串对象
		String result = HttpDemo2.sendPostDataByJson(url, param.toString());
		// 转换成json对象
		JSONObject JO = JSONObject.parseObject(result);
		// 可以通过get获取其中的值
		//System.out.println(JO.get("name"));

		return result;

	}

	@RequestMapping("/testMap")
	public String testMap(@RequestBody String param) {
		// 转换成json
		JSONObject json = JSONObject.parseObject(param);
		String url = json.getString("url");
		Map<String, Object> map = new HashMap();
		map.put("param", param);
		String result = HttpDemo2.sendPostDataByMap(url, map);
		System.out.println("在这里:" + result + "--------");
		return result;

	}

	@RequestMapping("test")
	public String httpCilents() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet http = new HttpGet("http://www.baidu.com");
			System.out.println("请求：" + http.getURI());
			// 执行get请求
			CloseableHttpResponse httpResponse = httpClient.execute(http);
			// 获取响应体
			HttpEntity entity = httpResponse.getEntity();
			System.out.println("-------------------------------------");
			// 打印响应体
			System.out.println(httpResponse.getStatusLine());
			if (entity != null) {
				// 打印响应内容长度
				System.out.println("长度：" + entity.getContentLength());
				// 打印响应内容
				System.out.println("内容:" + EntityUtils.toString(entity));
			}
			System.out.println("---------------------------------");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "成功";

	}

}
