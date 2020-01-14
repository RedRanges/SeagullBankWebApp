package com.to;

//import java.util.Date;

public class Transaction {
	private int accountNumber;
	private String type;
	private double amount;
	private String dt;
	private String username;
	private double balance;
	
	@Override
	public String toString() {
		return "Transaction [accountNumber=" + accountNumber + ", type=" + type + ", amount=" + amount + ", dt=" + dt
				+ ", username=" + username + "]";
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	
	
}
