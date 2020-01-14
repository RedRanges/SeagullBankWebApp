package com.bo;

import com.exception.BusinessException;

import java.util.ArrayList;

import com.to.Account;

public interface AccountBO {
	public ArrayList < Account > getAccountsByUsername( String username ) throws BusinessException;
	public int addAccount( Account account) throws BusinessException;
}
