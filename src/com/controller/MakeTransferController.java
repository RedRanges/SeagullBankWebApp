package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bo.TransferBO;
import com.bo.impl.TransferBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
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
		
		if ( session != null ) {
			try {
			transferBO.addTransfer( transfer );
			response.setStatus( 200 );
			} catch ( BusinessException e ) {
				// what to do here?
				response.setStatus( 404 );
			}
		}
		
	}

}
