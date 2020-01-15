package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bo.AccountBO;
import com.bo.TransactionBO;
import com.bo.impl.AccountBOImpl;
import com.bo.impl.TransactionBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
import com.to.Account;
import com.to.Transaction;


/**
 * Servlet implementation class TransactionServlet
 */
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionController() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		// WHAT IS MY UPDATE PATTERN?
		// * STEP 1 : create transaction object
		// * STEP 2 : create account for transaction
		// * STEP 3 : call update on account ( set balance )
		// * STEP 4 : store transaction in transaction DB 
		// TODO
		// need to add transaction to transaction table
		// businessexception after get account balance, what is how to handle?
		// what status when things go wrong?
		
		response.setContentType( "application/json" );
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession( false );
		
		Gson gson=new Gson();
		
		Transaction transaction = gson.fromJson(request.getReader(), Transaction.class);
		
		TransactionBO transactionBO = new TransactionBOImpl();
		AccountBO accountBO = new AccountBOImpl();
				
		if ( session != null ) {
			transaction.setUsername( ( String ) session.getAttribute( "username" ));
			transaction.setAccountNumber( ( Integer ) session.getAttribute( "selectedAccount" ) );
			Account transactionAccount = new Account();
			try {
				transactionAccount.setAccountNumber( transaction.getAccountNumber() );
				transactionAccount.setBalance( accountBO.getBalance( transactionAccount ).getBalance() );

				
				try {
					// update the account in the database, but we probably need to fetch that account now to update the view
					accountBO.setBalance( transactionAccount, transaction.getType(), transaction.getAmount() );
					System.out.println( accountBO.getAccountByAccountNumber( transactionAccount.getAccountNumber() ) );
					
					transaction.setBalance( accountBO.getBalance( transactionAccount ).getBalance() );
					out.print( transaction );
					out.print( accountBO.getAccountByAccountNumber( transactionAccount.getAccountNumber() ) );
					// change method name to add transaction?
					transactionBO.makeTransaction( transaction );
				} catch ( BusinessException e ) {
					out.print( e.getMessage() );
				}
			} catch ( BusinessException e ) {
				// what is this businessException
			}				
			response.setStatus( 200 );
		} else {
			// what status do I want to send back when things go wrong
			response.setStatus( 403 );
		}
	}

}
