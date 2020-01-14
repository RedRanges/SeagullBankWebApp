package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

/**
 * Servlet implementation class AccountNumberController
 */
@WebServlet("/AccountNumberController")
public class AccountNumberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountNumberController() {
        super();
        // TODO Auto-generated constructor stub
    }

    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( "application/json" );
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		
		HttpSession session = request.getSession( false ); 
		System.out.println( session.getAttribute( "username" ) );
		AccountBO bo = new AccountBOImpl();
		String username = (String) session.getAttribute( "username" );
			
		ArrayList <Account> accountList = new ArrayList();
		try {
			accountList = bo.getAccountsByUsername( username );
		} catch (BusinessException e) {
			System.out.println( e.getMessage() );
		}
		System.out.println( "test" );
		System.out.println( accountList );
		for ( Account a : accountList ) {
			System.out.println( a );
		}
		Gson gson = new Gson();
		
		
		
	
		out.print( gson.toJson( accountList ) );
		
		
		
	}

}
