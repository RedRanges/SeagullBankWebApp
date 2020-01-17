package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;

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
 * Servlet implementation class EmployeePendingController
 */

public class EmployeePendingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeePendingController() {
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
			AccountBO accountBO = new AccountBOImpl();
			
			
			try {
				ArrayList < Account > accountList = accountBO.getPendingAccounts();
				Gson gson = new Gson();
				out.print( gson.toJson( accountList ) );
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
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( "application/json" );
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		
		HttpSession session = request.getSession( false ); 
		
		if ( session != null ) {
			AccountBO accountBO = new AccountBOImpl();
			Gson gson = new Gson();
			Account account = gson.fromJson( request.getReader(), Account.class );
			System.out.println( account );
			int i = 0;
			try {
				i = accountBO.updateAccount( account );
				if ( i == 1 ) {
					response.setStatus( 200 );
				} else {
					response.setStatus( 404 );
				}
				
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
