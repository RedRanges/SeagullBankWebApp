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

import com.bo.TransferBO;
import com.bo.impl.TransferBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
import com.to.Account;
import com.to.Transfer;

/**
 * Servlet implementation class AllTransfersController
 */
@WebServlet("/AllTransfersController")
public class AllAccountTransfersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllAccountTransfersController() {
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
			TransferBO transferBO = new TransferBOImpl();
			Gson gson = new Gson();
			Account account = gson.fromJson( request.getReader(), Account.class );
			System.out.println( account );
			try {
				ArrayList < Transfer > transferList = transferBO.getAllTransfersByAccountNumber( account );
				for ( Transfer t : transferList ) {
					System.out.println( t );
				}
				out.print( gson.toJson( transferList ) );
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
