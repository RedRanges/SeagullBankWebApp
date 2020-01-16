package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dao.TransferDAO;
import com.dbutil.OracleConnection;
import com.exception.BusinessException;
import com.to.Account;
import com.to.Transfer;

public class TransferDAOImpl implements TransferDAO{

	@Override
	public int addTransfer( Transfer transfer ) throws BusinessException {
		int i = 0;
		
		try( Connection connection=OracleConnection.getConnection() ) {
			String sql = "insert into transfers ( accountfrom, accountto, amount, status, datetime ) values(?,?,?,?,?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement( sql );
			preparedStatement.setInt( 1, transfer.getAccountFrom() );
			preparedStatement.setInt( 2, transfer.getAccountTo() );
			preparedStatement.setDouble( 3, transfer.getAmount() );
			preparedStatement.setString( 4, transfer.getStatus() );
			preparedStatement.setString( 5, transfer.getDateTime() );
			
			i =  preparedStatement.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		}
		
		return i;
	}

	@Override
	public int updateTransfer( Transfer transfer ) throws BusinessException {
		int i = 0;
		try( Connection connection=OracleConnection.getConnection() ) {
			String sql = "update transfers set status=? where id=?";
			
			PreparedStatement preparedStatement = connection.prepareStatement( sql );

			preparedStatement.setString( 1, transfer.getStatus() );
			preparedStatement.setInt( 2, transfer.getId() );
			
			i =  preparedStatement.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		

		}
		return i;

	}

	@Override
	public ArrayList<Transfer> getAllTransfersByAccountNumber(Account account) throws BusinessException {
		ArrayList < Transfer > transferList = new ArrayList();
		try( Connection connection=OracleConnection.getConnection() ) {
			String sql = "select id, status, amount, datetime, accountfrom, accountto from transfers where accountfrom=? or accountto=?";
			
			PreparedStatement preparedStatement = connection.prepareStatement( sql );


			preparedStatement.setInt( 1, account.getAccountNumber() );
			preparedStatement.setInt( 2, account.getAccountNumber() );
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while ( resultSet.next() ) {
				Transfer transfer = new Transfer();
				transfer.setId( resultSet.getInt( "id" ) );
				transfer.setStatus( resultSet.getString( "status" ) );
				transfer.setAmount( resultSet.getDouble( "amount" ) );
				transfer.setDateTime( resultSet.getString( "datetime" ) );
				transfer.setAccountFrom( resultSet.getInt( "accountfrom" ) );
				transfer.setAccountTo( resultSet.getInt( "accountTo" ) );
				
				transferList.add( transfer );
			}

		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		

		}
		
		return transferList;
	}
}
