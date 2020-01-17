package com.bo.impl;

import java.util.ArrayList;

import com.bo.TransactionBO;
import com.dao.impl.TransactionDAOImpl;
import com.exception.BusinessException;
import com.to.Transaction;

public class TransactionBOImpl implements TransactionBO {

	@Override
	public int makeTransaction(Transaction transaction) throws BusinessException {
		int i = 0;
		//  business logic
		i = new TransactionDAOImpl().makeTransaction( transaction );
		return i;
	}

	@Override
	public ArrayList<Transaction> getTransactions() throws BusinessException {
		ArrayList < Transaction > transactionList = new ArrayList();
		// business logic
		transactionList = new TransactionDAOImpl().getTransactions();
		return transactionList;
	}

	@Override
	public ArrayList<Transaction> getTransactionsByAccountNumber(int accountNumber) throws BusinessException {
		ArrayList < Transaction > transactionList = new ArrayList();
		// business logic
		transactionList = new TransactionDAOImpl().getTransactionsByAccountNumber( accountNumber );
		return transactionList;
	}

	@Override
	public ArrayList<Transaction> getAllTransfers() throws BusinessException {
		ArrayList < Transaction > transactionList = new ArrayList();
		// business logic
		transactionList = new TransactionDAOImpl().getTransactions();
		return transactionList;
	}

}
