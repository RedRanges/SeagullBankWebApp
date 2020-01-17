package com.dao;

import com.exception.BusinessException;
import com.to.User;


public interface UserDAO {
	public User getUserByUsernamePassword( String username, String password ) throws BusinessException;
	public boolean isValidUser(User user) throws BusinessException;
	public int addUser( User user ) throws BusinessException;
	public User getUser( User user ) throws BusinessException;

}
