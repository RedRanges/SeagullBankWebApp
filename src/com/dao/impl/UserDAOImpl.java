package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dao.UserDAO;
import com.dbutil.OracleConnection;
import com.exception.BusinessException;
import com.to.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public User getUserByUsernamePassword(String username, String password) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValidUser(User user) throws BusinessException {
		boolean b=false;
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select username from users where username=? and password=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			System.out.println( user );
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				b=true;
			}else {
				throw new BusinessException("Invalid Login Credentials");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Server encountered internal error.. Please contact support....");
		}
		return b;
	}
	
}
