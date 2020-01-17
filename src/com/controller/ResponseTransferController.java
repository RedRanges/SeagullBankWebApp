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
 * Servlet implementation class ResponseTransferController
 */

public class ResponseTransferController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResponseTransferController() {
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
		TransactionBO transactionBO = new TransactionBOImpl();
		AccountBO accountBO = new AccountBOImpl();
		
		Gson gson=new Gson();
		
		Transfer transfer = gson.fromJson( request.getReader(), Transfer.class );
		TransferBO transferBO = new TransferBOImpl();
//		System.out.println( transfer );
		if ( session != null ) {
			try {
			int i = transferBO.updateTransfer( transfer );
			Transfer transferToUpdate = transferBO.getTransferById( transfer.getId() );
		

			if ( i == 1 ) {
				if ( transfer.getStatus().equals( "accepted") ) {
					Account account = accountBO.getAccountByAccountNumber( (Integer) session.getAttribute( "selectedAccount" ) );
	
					
					Transaction transaction = new Transaction();
					transaction.setType( "deposit" );
					transaction.setAmount( transferToUpdate.getAmount() );
					transaction.setDateTime( transfer.getResponseDateTime() );
					transaction.setUsername( (String) session.getAttribute( "username" ) );
					transaction.setAccountNumber( transferToUpdate.getAccountTo() );
					// update the account in the database, but we probably need to fetch that account now to update the view
					accountBO.setBalance( account, transaction.getType(), transaction.getAmount() );
					transaction.setBalance( accountBO.getBalance( account ).getBalance() );
					System.out.println( transaction);
					transactionBO.makeTransaction( transaction );
					
					response.setStatus( 200 );
				}
				
			} else {
				response.setStatus( 404 );
			}
			} catch ( BusinessException e ) {
				// what to do here?
				response.setStatus( 404 );
			}
		}
	}

}
