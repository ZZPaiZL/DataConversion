package com.test.bean;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MapBean {
	private long xmbh;
	private int zh;
	private String jgbm;
	private String userid;
	private String username;
	private String url;
	private String name;
	private String city;
	public long getXmbh() {
		return xmbh;
	}
	public void setXmbh(long xmbh) {
		this.xmbh = xmbh;
	}
	public int getZh() {
		return zh;
	}
	public void setZh(int zh) {
		this.zh = zh;
	}
	public String getJgbm() {
		return jgbm;
	}
	public void setJgbm(String jgbm) {
		this.jgbm = jgbm;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "MapBean [xmbh=" + xmbh + ", zh=" + zh + ", jgbm=" + jgbm + ", userid=" + userid + ", username="
				+ username + ", url=" + url + ", name=" + name + ", city=" + city + "]";
	}
	
	
	
}
