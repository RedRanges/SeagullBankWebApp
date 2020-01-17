package com.bo;

import com.exception.BusinessException;

import java.util.ArrayList;

import com.to.Account;

public interface AccountBO {
	public ArrayList < Account > getAccountsByUsername( String username ) throws BusinessException;
	public int addAccount( Account account) throws BusinessException;
	public int setBalance ( Account account, String type, double amount ) throws BusinessException;
	public Account getBalance( Account account ) throws BusinessException;
	public Account getAccountByAccountNumber( int accountNumber ) throws BusinessException;
	public ArrayList <Account> getPendingAccounts() throws BusinessException;
	public int updateAccount( Account account ) throws BusinessException;
}
