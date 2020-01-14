package com.to;

public class Account {
	private int accountNumber;
	private String type;
	private double balance;
	private String username;
	private String status;
	
	
	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", type=" + type + ", balance=" + balance + ", username="
				+ username + ", status=" + status + "]";
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
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
