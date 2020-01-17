package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bo.UserBO;
import com.bo.impl.UserBOImpl;
import com.to.User;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class EmployeeAccountController
 */

public class EmployeeAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeAccountController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Logger log = Logger.getLogger( EmployeeAccountController.class );
		
		try {
			response.setContentType( "application/json" );
			PrintWriter out=response.getWriter();
			UserBO userBO = new UserBOImpl();
			 HttpSession session=request.getSession( false ); 
			 User user = new User();
			 if ( session != null ) {
				 String username = ( String )session.getAttribute( "username" );
//				 log.info( "Employee login attempted by user " + username );
				 user.setUsername( username );
				 if ( userBO.getUser( user ).getType().equals( "EMPLOYEE" ) ) {
					 response.setStatus( 200 );
				 } else {
					 response.setStatus( 403 );
				 }
			 } else {
				 response.setStatus( 403 );
			 }
			 
			 
		} catch ( Exception e ) {
			System.out.println( e );
		}
	}

}
