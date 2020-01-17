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
import com.bo.TransferBO;
import com.bo.impl.AccountBOImpl;
import com.bo.impl.TransactionBOImpl;
import com.bo.impl.TransferBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
import com.to.Account;
import com.to.Transaction;
import com.to.Transfer;

/**
 * Servlet implementation class MakeTransferController
 */

public class MakeTransferController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeTransferController() {
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
		
		Transfer transfer = gson.fromJson( request.getReader(), Transfer.class );
		transfer.setStatus( "pending" );
		TransferBO transferBO = new TransferBOImpl();
		AccountBO accountBO = new AccountBOImpl();
		TransactionBO transactionBO = new TransactionBOImpl();
		
		// update the account in the database, but we probably need to fetch that account now to update the view
		
		
		if ( session != null ) {
			try {
			System.out.println( transfer );
			Account account = accountBO.getAccountByAccountNumber( (Integer) session.getAttribute( "selectedAccount" ) );
			System.out.println( account );
			int i = transferBO.addTransfer( transfer );
			if ( i == 1 ) {
				Transaction transaction = new Transaction();
				transaction.setAccountNumber( account.getAccountNumber() );
				transaction.setAmount( transfer.getAmount() );
				transaction.setDateTime( transfer.getDateTime() );
				transaction.setUsername( (String) session.getAttribute( "username" ));
				transaction.setType( "withdraw" );
				
				
				accountBO.setBalance( account, transaction.getType(), transaction.getAmount() );
				
				transaction.setBalance( accountBO.getBalance( account ).getBalance() );
				System.out.println( transaction );
				System.out.println( account );
				transactionBO.makeTransaction( transaction );
			}
			
			response.setStatus( 200 );
			} catch ( BusinessException e ) {
				// what to do here?
				System.out.println( e.getMessage() );
				response.setStatus( 404 );
			}
		}
		
	}

}
