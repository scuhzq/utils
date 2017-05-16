package com.hzq.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by hzq on 2017/5/16.
 */
public class GetMsgBean extends Bean2Map {

	@XStreamAlias("account")
	private String name;

	@XStreamAlias("password")
	private String pwd;

	private Long age;

	private Long money;

	public GetMsgBean() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Long getAge() {
		return this.age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public long getMoney() {
		return this.money.longValue();
	}

	public void setMoney(long money) {
		this.money = Long.valueOf(money);
	}

}
