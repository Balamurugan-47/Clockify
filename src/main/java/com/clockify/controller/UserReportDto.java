package com.clockify.controller;

public class UserReportDto {
	private String userName;
	private int duration;
	private double amount;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public UserReportDto(String userName, String currency, int duration, double amount) {
		super();
		this.userName = userName;
		this.duration = duration;
		this.amount = amount;
	}

	public UserReportDto() {

	}

}
