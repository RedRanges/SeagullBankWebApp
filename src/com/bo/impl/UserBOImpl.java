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
}

