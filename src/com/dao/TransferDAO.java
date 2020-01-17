package com.dao;

import java.util.ArrayList;

import com.exception.BusinessException;
import com.to.Account;
import com.to.Transfer;

public interface TransferDAO {
	public int addTransfer( Transfer transfer ) throws BusinessException;
	public int updateTransfer( Transfer transfer ) throws BusinessException;
	public ArrayList < Transfer > getAllTransfersByAccountNumber( Account account ) throws BusinessException; 
	public Transfer getTransferById( int id ) throws BusinessException;
	public ArrayList < Transfer > getAllTransfers() throws BusinessException;
}
