package com.bo.impl;

import java.util.ArrayList;

import com.bo.TransferBO;
import com.dao.impl.TransferDAOImpl;
import com.exception.BusinessException;
import com.to.Account;
import com.to.Transfer;

public class TransferBOImpl implements TransferBO {

	@Override
	public int addTransfer(Transfer transfer) throws BusinessException {
		// TODO add business logic
		// account is not pending
		int i = 0;
		i = new TransferDAOImpl().addTransfer( transfer );
		return i;
	}

	@Override
	public int updateTransfer( Transfer transfer ) throws BusinessException {
		int i = 0;
		// business logic status is accepted or rejected
		i = new TransferDAOImpl().updateTransfer( transfer );
		return i;
	}

	@Override
	public ArrayList<Transfer> getAllTransfersByAccountNumber(Account account) throws BusinessException {
		ArrayList < Transfer > transferList = new ArrayList();
		// business logic
		
		transferList = new TransferDAOImpl().getAllTransfersByAccountNumber(account);
		
		return transferList;
	}

	@Override
	public Transfer getTransferById(int id) throws BusinessException {
		Transfer transfer = null;
		// TODO business logic
		transfer = new TransferDAOImpl().getTransferById( id );
		
		return transfer;
	}

	@Override
	public ArrayList<Transfer> getAllTransfers() throws BusinessException {
		ArrayList < Transfer > transferList = null;
		transferList = new TransferDAOImpl().getAllTransfers();
		return transferList;
	}
	
}
