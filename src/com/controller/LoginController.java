package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bo.UserBO;
import com.bo.impl.UserBOImpl;
import com.exception.BusinessException;
import com.google.gson.Gson;
import com.to.User;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson=new Gson();
		User user=gson.fromJson(request.getReader(), User.class);
		UserBO bo=new UserBOImpl();
		PrintWriter out=response.getWriter();
		try {
			if( bo.isValidUser( user ) ) {
				out.print(gson.toJson( user ) );
				Cookie cookie = new Cookie( "username", user.getUsername() );
				response.addCookie( cookie );
				response.setStatus( 200 );
			} else {
				response.setStatus( 404 );
			}
			
		} catch( Exception e ) {
			response.setStatus( 404 );
		}

		
		
	}
    

}
