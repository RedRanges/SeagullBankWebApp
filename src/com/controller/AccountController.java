package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * Servlet implementation class AccountController
 */
@WebServlet("/AccountController")
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			response.setContentType( "application/json" );
			PrintWriter out=response.getWriter();
			
			 HttpSession session=request.getSession( false ); 
			 if ( session != null ) {
			 String username = ( String )session.getAttribute( "username" );
			 out.print( "hello " + username );
			
			 response.setStatus( 200 );
			 } else {
				 response.setStatus( 403 );
			 }
			 
			 
		} catch ( Exception e ) {
			System.out.println( e );
		}
		
	}

}
