package com.to;

public class Transfer {
	private int id;
	private int accountFrom;
	private int accountTo;
	private double amount;
	private String dateTime;
	private String status;
	private String responseDateTime;
	
	@Override
	public String toString() {
		return "Transfer [id=" + id + ", accountFrom=" + accountFrom + ", accountTo=" + accountTo + ", amount=" + amount
				+ ", dateTime=" + dateTime + ", status=" + status + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}
	public int getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponseDateTime() {
		return responseDateTime;
	}
	public void setResponseDateTime(String responseDateTime) {
		this.responseDateTime = responseDateTime;
	}


}
