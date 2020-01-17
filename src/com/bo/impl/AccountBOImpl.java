package com.bo.impl;

import java.util.ArrayList;

import com.bo.AccountBO;
import com.exception.BusinessException;
import com.to.Account;
import com.dao.impl.AccountDAOImpl;

public class AccountBOImpl implements AccountBO{

	@Override
	public ArrayList< Account > getAccountsByUsername(String username) throws BusinessException {
		ArrayList < Account > accountList = null;
		// business logic
		
		accountList = new AccountDAOImpl().getAccountsByUsername( username );
		
		return accountList;
	}

	@Override
	public int addAccount( Account account ) throws BusinessException {
		int i = 0;
		// business logic
		i = new AccountDAOImpl().addAccount( account );
		
		return i;
	}

	@Override
	public int setBalance( Account account, String type, double amount ) throws BusinessException {
		int i = 0;
		Account updatedAccount = new Account();
		
		if ( type.equals( "withdraw" ) && account.getBalance() - amount >= 0 ) {
			updatedAccount.setBalance( account.getBalance() - amount );
			updatedAccount.setAccountNumber( account.getAccountNumber() );
			i = new AccountDAOImpl().setBalance( updatedAccount );
		} else if ( type.equals( "deposit" ) ) { 
			updatedAccount.setBalance( account.getBalance() + amount );
			updatedAccount.setAccountNumber( account.getAccountNumber() );
			i = new AccountDAOImpl().setBalance( updatedAccount );
		} else {
			throw new BusinessException( "Invalid withdraw transaction : not enough money" );
		}
		
		return i;
	}

	@Override
	public Account getBalance( Account account ) throws BusinessException {
		Account currentBalance = new Account();
		currentBalance = new AccountDAOImpl().getBalance( account );
		return currentBalance;
	}

	@Override
	public Account getAccountByAccountNumber(int accountNumber) throws BusinessException {
		Account account = new Account();
		account = new AccountDAOImpl().getAccountByAccountNumber( accountNumber );
		return account;
	}

	@Override
	public ArrayList<Account> getPendingAccounts() throws BusinessException {
		ArrayList <Account> accountList = new ArrayList();
		accountList = new AccountDAOImpl().getPendingAccounts();
		return accountList;
	}

	@Override
	public int updateAccount( Account account ) throws BusinessException {
		// TODO Auto-generated method stub
		int i = 0;
		i = new AccountDAOImpl().updateAccount( account );
		return i;
	}


}
