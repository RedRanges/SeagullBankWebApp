package com.dao;

import java.util.ArrayList;

import com.exception.BusinessException;
import com.to.Account;

public interface AccountDAO {
	public ArrayList < Account > getAccountsByUsername( String username ) throws BusinessException;
	public int addAccount( Account account ) throws BusinessException;
	public int setBalance ( Account account ) throws BusinessException;
	public Account getBalance ( Account account ) throws BusinessException;
	public Account getAccountByAccountNumber( int accountNumber ) throws BusinessException;
	public ArrayList <Account> getPendingAccounts () throws BusinessException;
	public int updateAccount( Account account ) throws BusinessException;
}
