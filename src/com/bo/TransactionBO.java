package com.bo;

import java.util.ArrayList;

import com.exception.BusinessException;
import com.to.Transaction;

public interface TransactionBO {
	public int makeTransaction( Transaction transaction ) throws BusinessException;
	public ArrayList < Transaction > getTransactions() throws BusinessException;
	public ArrayList < Transaction > getTransactionsByAccountNumber( int accountNumber ) throws BusinessException;
}
