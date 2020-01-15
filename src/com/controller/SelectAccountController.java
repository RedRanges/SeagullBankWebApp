package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bo.TransactionBO;
import com.bo.impl.TransactionBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
import com.to.Account;
import com.to.Transaction;


/**
 * Servlet implementation class SelectAccountController
 */

public class SelectAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectAccountController() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// update session so that it's selectedAccount attribute matches the account that was just selected
		// this servlet will go in the side bar menu links
		// add transaction history to return?
		// what happens if error for getTransactionsByAccountNumber
		// what if someone edits the javascript / html?
		
		response.setContentType( "application/json" );
		response.setCharacterEncoding( "UTF-8" );

		PrintWriter out=response.getWriter();
		TransactionBO transactionBO = new TransactionBOImpl();
		HttpSession session = request.getSession( false );
		ArrayList < Transaction > transactionList = new ArrayList();
		Gson gson = new Gson();
		if ( session != null ) {
			Account account =gson.fromJson(request.getReader(), Account.class);
			try {
			transactionList = transactionBO.getTransactionsByAccountNumber( account.getAccountNumber() );
			gson = new Gson();
			out.print( gson.toJson( transactionList ) );
	        
			
			
//			response.setStatus( 200 );
			
			} catch ( BusinessException e ) {
				// what happens if this happens?
			}
			
			
		} else {
			// find out what status to send back
			response.setStatus( 403 );
		}
	

	}

}
