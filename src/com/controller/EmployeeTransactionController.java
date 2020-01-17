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
import com.bo.TransferBO;
import com.bo.impl.TransactionBOImpl;
import com.bo.impl.TransferBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
import com.to.Transaction;
import com.to.Transfer;

/**
 * Servlet implementation class EmployeeTransactionController
 */

public class EmployeeTransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeTransactionController() {
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
		
		if ( session != null ) {
			TransactionBO transactionBO = new TransactionBOImpl();
			
			
			try {
				ArrayList < Transaction > transactionList = transactionBO.getTransactions();
				Gson gson = new Gson();
				out.print( gson.toJson( transactionList ) );
				response.setStatus( 200 );
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				response.setStatus( 403 );
				e.printStackTrace();
			}
		} else {
			response.setStatus( 404 );
		}
	}

}
