package com.bo.impl;

import com.bo.UserBO;
import com.dao.impl.UserDAOImpl;
import com.exception.BusinessException;
import com.to.User;

public class UserBOImpl implements UserBO{

	@Override
	public boolean isValidUser(User user) throws BusinessException {
		boolean b=false;
		if( user != null && ( user.getUsername()+"").matches( "^(?=.*[a-zA-z])[a-zA-Z0-9]{5,20}$" ) && user.getPassword().matches( "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,15}$" ) ){
			//call DAO
			b=new UserDAOImpl().isValidUser(user);
		}else {
			throw new BusinessException("Invalid Login Credentials");
		}
		return b;
	}

	@Override
	public int addUser(User user) throws BusinessException {
		int i = 0;
		if( user != null && ( user.getUsername()+"").matches( "^(?=.*[a-zA-z])[a-zA-Z0-9]{5,20}$" ) && user.getPassword().matches( "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,15}$" ) ){
			//call DAO
			i=new UserDAOImpl().addUser( user );
		}else {
			throw new BusinessException("Invalid Login Credentials");
		}
		return i;
	}

	@Override
	public User getUser(User user) throws BusinessException {
		// business logic
		user = new UserDAOImpl().getUser( user );
		
		return user;
	}
}

