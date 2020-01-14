package com.dao;

import java.util.ArrayList;

import com.exception.BusinessException;
import com.to.Account;

public interface AccountDAO {
	public ArrayList < Account > getAccountsByUsername( String username ) throws BusinessException;
	public int addAccount( Account account ) throws BusinessException;
}
