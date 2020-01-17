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
		try {
			response.setContentType( "application/json" );
			PrintWriter out=response.getWriter();
			UserBO userBO = new UserBOImpl();
			 HttpSession session=request.getSession( false ); 
			 User user = new User();
			 if ( session != null ) {
				 String username = ( String )session.getAttribute( "username" );
				 if ( userBO.getUser( ) )
				 response.setStatus( 200 );
			 } else {
				 response.setStatus( 403 );
			 }
			 
			 
		} catch ( Exception e ) {
			System.out.println( e );
		}
	}

}
