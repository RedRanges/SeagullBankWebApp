package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.bo.AccountBO;
import com.bo.UserBO;
import com.bo.impl.AccountBOImpl;
import com.bo.impl.UserBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
import com.to.Account;
import com.to.User;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( "application/json" );
		response.setCharacterEncoding( "UTF-8" );
		Gson gson=new Gson();
		User user=gson.fromJson(request.getReader(), User.class);
		UserBO bo=new UserBOImpl();
		PrintWriter out=response.getWriter();
	
		try {
			if( bo.isValidUser( user ) ) {
				bo.getUser( user );
				user.setPassword( null );
				out.print(gson.toJson( user ) );
				if ( user.getType().equals( "EMPLOYEE" ) ) {
					response.setStatus( 200 );				
				}

		        HttpSession session=request.getSession();  
		        session.setMaxInactiveInterval( 1200 );
		        session.setAttribute( "username", user.getUsername() );
		        AccountBO accountBO = new AccountBOImpl();
		        
		        ArrayList < Account > accountList = accountBO.getAccountsByUsername( user.getUsername() );
		        
		        if ( accountList.size() == 0 && ! user.getType().equals( "EMPLOYEE" ) ) {
		        
		        	response.setStatus( 204 );
		        } else {
		        	
		        	response.setStatus( 200 );
		        }
			} else {

				response.setStatus( 403 );
			}
			
		} catch( Exception e ) {
			System.out.println( e.getMessage() );
			response.setStatus( 404 );
		}
	}
    

}
