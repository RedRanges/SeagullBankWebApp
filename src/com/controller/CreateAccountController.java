package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bo.AccountBO;
import com.bo.impl.AccountBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
import com.to.Account;
import com.to.Transfer;

/**
 * Servlet implementation class CreateAccountController
 */

public class CreateAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountController() {
        super();
        // TODO Auto-generated constructor stub
    }

    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( "application/json" );
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession( false );
		Gson gson=new Gson();
		
		Account account = gson.fromJson( request.getReader(), Account.class );
		AccountBO accountBO = new AccountBOImpl();
		
		if ( session != null ) {
			account.setUsername( (String) session.getAttribute( "username" ) );
			try {
				
			accountBO.addAccount( account );
			response.setStatus( 200 );
			} catch ( BusinessException e ) {
				System.out.println( e.getMessage() );
				response.setStatus( 404 );
			}
		}
	}

}
