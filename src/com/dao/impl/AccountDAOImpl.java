package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dao.AccountDAO;
import com.dbutil.OracleConnection;
import com.exception.BusinessException;
import com.to.Account;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public ArrayList< Account > getAccountsByUsername(String username) throws BusinessException {
		ArrayList < Account > accountList = new ArrayList();	
		try(Connection connection=OracleConnection.getConnection()){
			
			String sql = "select accountnumber, balance, type, status from accounts where username=? and status=?";

			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString( 1, username );
			preparedStatement.setString( 2, "APPROVED" );
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				Account account = new Account();
				account.setType( resultSet.getString( "type" ) );
				account.setBalance( resultSet.getDouble(  "balance" ) );
				account.setAccountNumber( resultSet.getInt( "accountnumber" ) );
				account.setStatus( resultSet.getString( "status" ) );
				accountList.add( account );
			}
			
		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		}
		return accountList;
		}

	@Override
	public int addAccount( Account account ) throws BusinessException {
		int i = 0;
		try( Connection connection=OracleConnection.getConnection() ) {
			String sql = "insert into accounts (username, balance, type) values(?,?,?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement( sql );
			preparedStatement.setString( 1, account.getUsername() );
			preparedStatement.setDouble( 2,  account.getBalance() );
			preparedStatement.setString( 3, account.getType() );
			
			i =  preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support..." + e );
		}
		
		return i;
	}

	@Override
	public int setBalance( Account account ) throws BusinessException {
		int i = 0;
		try( Connection connection=OracleConnection.getConnection() ) {
			String sql = "update accounts set balance=? where accountnumber=?";
			
			
			PreparedStatement preparedStatement = connection.prepareStatement( sql );
			preparedStatement.setDouble( 1, account.getBalance() );
			preparedStatement.setInt( 2,  account.getAccountNumber() );
			
			i =  preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support...5" + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support...6" + e );
		}
		
		return i;
	}

	@Override
	public Account getBalance( Account account ) throws BusinessException {
		Account accountBalance = new Account();
		try(Connection connection=OracleConnection.getConnection()){
			String sql = "select balance from accounts where accountnumber=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt( 1,  account.getAccountNumber() );
			ResultSet resultSet = preparedStatement.executeQuery();
			if ( resultSet.next() ) {
				accountBalance.setBalance( resultSet.getDouble(  "balance" ) );				
			}
			
		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support...1" + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support...2" + e );
		}
		
		
		return accountBalance;
	}

	@Override
	public Account getAccountByAccountNumber( int accountNumber ) throws BusinessException {
		Account account = null;
		try(Connection connection=OracleConnection.getConnection()){
			
			String sql = "select accountNumber, balance, type from accounts where accountnumber=?";

			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt( 1, accountNumber );
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if ( resultSet.next() ) {
				account = new Account();
				account.setType( resultSet.getString( "type" ) );
				account.setBalance( resultSet.getDouble(  "balance" ) );
				account.setAccountNumber( resultSet.getInt( "accountnumber" ) );
			}
			
		} catch (ClassNotFoundException e) {
			throw new BusinessException( "Internal error occured... please contact support...3" + e );
		} catch (SQLException e) {
			throw new BusinessException( "Internal error occured... please contact support...4" + e );
		}
		return account;
		
	}
	



}
