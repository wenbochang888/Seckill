package com.entity;

import java.util.Date;

public class SuccessKilledRecord {

	private long seckillId;
	private String name;
	private int number;
	private Date startTime;
	private Date endTime;
	private Date createTimeGoods;

	private long userPhone;
	private short state;
	private Date createTimeSeckillGoods;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTimeGoods() {
		return createTimeGoods;
	}

	public void setCreateTimeGoods(Date createTimeGoods) {
		this.createTimeGoods = createTimeGoods;
	}

	public Date getCreateTimeSeckillGoods() {
		return createTimeSeckillGoods;
	}

	public void setCreateTimeSeckillGoods(Date createTimeSeckillGoods) {
		this.createTimeSeckillGoods = createTimeSeckillGoods;
	}
	
	@Override
	public String toString() {
		return "SuccessKilledRecord [seckillId=" + seckillId + ", name=" + name + ", number=" + number + ", startTime="
				+ startTime + ", endTime=" + endTime + ", createTimeGoods=" + createTimeGoods + ", userPhone="
				+ userPhone + ", state=" + state + ", createTimeSeckillGoods=" + createTimeSeckillGoods + "]";
	}
}
