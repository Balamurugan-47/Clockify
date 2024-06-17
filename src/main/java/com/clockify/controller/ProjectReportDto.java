package com.clockify.controller;

public class ProjectReportDto {
	private String projectName;
	private String currency;
	private int duration;
	private double amount;

	public ProjectReportDto() {
	}

	public ProjectReportDto(String projectName, String currency, int duration, double amount) {
		super();
		this.projectName = projectName;
		this.currency = currency;
		this.duration = duration;
		this.amount = amount;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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
}
