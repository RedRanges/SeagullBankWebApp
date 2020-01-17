package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bo.UserBO;
import com.bo.impl.UserBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
import com.to.User;




public class RegisterUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUserController() {
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
		Gson gson=new Gson();
		
		User user = gson.fromJson( request.getReader(), User.class );
		System.out.println( user );
		UserBO userBO = new UserBOImpl();
		try {
			userBO.addUser( user );
			response.setStatus( 200 );
		} catch (BusinessException e) {
			response.setStatus( 404 );
			e.printStackTrace();
		}
		
		
		

	}

}
