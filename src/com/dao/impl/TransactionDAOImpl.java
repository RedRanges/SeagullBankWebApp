package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dao.TransactionDAO;
import com.dbutil.OracleConnection;
import com.exception.BusinessException;
import com.to.Account;
import com.to.Transaction;

public class TransactionDAOImpl implements TransactionDAO{
	public int makeTransaction( Transaction transaction ) throws BusinessException {
		int i = 0;
		
		try( Connection connection=OracleConnection.getConnection() ) {
			String sql = "insert into transactions ( accountnumber, type, amount, dt, username, balance ) values(?,?,?,?,?,?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement( sql );
			preparedStatement.setInt( 1, transaction.getAccountNumber() );
			preparedStatement.setString( 2, transaction.getType() );
			preparedStatement.setDouble( 3, transaction.getAmount() );
			preparedStatement.setString( 4, transaction.getDt() );
			preparedStatement.setString( 5, transaction.getUsername() );
			preparedStatement.setDouble( 6,  transaction.getBalance() );
			
			i =  preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		}
		
		return i;
	}

	@Override
	public ArrayList<Transaction> getTransactions() throws BusinessException {
		ArrayList < Transaction > transactionList= new ArrayList();
		try( Connection connection=OracleConnection.getConnection() ) {
			String sql = "select accountnumber, type, amount, dt, username, balance from transactions";
			PreparedStatement preparedStatement = connection.prepareStatement( sql );
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				Transaction transaction = new Transaction();
				transaction.setType( resultSet.getString( "type" ) );
				transaction.setBalance( resultSet.getDouble(  "balance" ) );
				transaction.setAccountNumber( resultSet.getInt( "accountnumber" ) );
				transaction.setAmount( resultSet.getDouble( "amount" ) );
				transaction.setDt( resultSet.getString( "dt" ) );
				transaction.setUsername( resultSet.getString( "username" ) );
				
				transactionList.add( transaction );
			}
			
			
		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		}
		
		return transactionList;
	}
}
