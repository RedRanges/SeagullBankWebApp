package com.dao;

import java.util.ArrayList;

import com.exception.BusinessException;
import com.to.Transaction;

public interface TransactionDAO {
	public int makeTransaction( Transaction transaction ) throws BusinessException;
	public ArrayList < Transaction > getTransactions() throws BusinessException;
	public ArrayList < Transaction > getTransactionsByAccountNumber( int accountNumber) throws BusinessException;
}
