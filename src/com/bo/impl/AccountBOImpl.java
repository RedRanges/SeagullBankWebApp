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
	public int addAccount(Account account) throws BusinessException {
		int i = 0;
		// business logic
		i = new AccountDAOImpl().addAccount( account );
		
		return i;
	}
	
	
	

}
