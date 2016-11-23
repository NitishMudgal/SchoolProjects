/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
package edu.cmu.cs.webapp.hw4.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.hw4.databean.UserBean;
import edu.cmu.cs.webapp.hw4.formbean.LoginForm;
import edu.cmu.cs.webapp.hw4.formbean.RegisterForm;
import edu.cmu.cs.webapp.hw4.model.Model;
import edu.cmu.cs.webapp.hw4.model.UserDAO;

public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> loginFormBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	private FormBeanFactory<RegisterForm> registerFormBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);
	
	private UserDAO userDAO;

	public LoginAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() { return "login.do"; }
    
    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
            	
    	// If user is already logged in, redirect to todolist.do
        if (session.getAttribute("user") != null) {
        	return "favorite.do";
        }
        
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
        	 // Set up user list for nav bar
			request.setAttribute("userList",userDAO.getUsers());
			
	    	LoginForm loginForm = loginFormBeanFactory.create(request);
	    	request.setAttribute("loginForm",loginForm);       

	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!loginForm.isPresent()) {
	            return "login.jsp";
	        }

	        // Any validation errors?
	        errors.addAll(loginForm.getValidationErrors());
	        if (errors.size() != 0) {
	            return "login.jsp";
	        }

       		if (loginForm.getAction().equals("Register")) {
       			RegisterForm registerForm = registerFormBeanFactory.create(request);
	       		errors.addAll(registerForm.getValidationErrors());
	 	        if (errors.size() != 0) {
	 	            return "login.jsp";
	 	        }
    	        request.setAttribute("registerForm",registerForm);
       			UserBean newUser = new UserBean();
       			newUser.setEmailAddress(registerForm.getEmailAddress());
       			newUser.setFirstName(registerForm.getFirstName());
       			newUser.setLastName(registerForm.getLastName());
       			newUser.setPassword(registerForm.getPassword());
       			try {
	       			userDAO.create(newUser);	       			
	       			session.setAttribute("user", newUser);
	       			return("favorite.do");
       	        } catch (DuplicateKeyException e) {
       	        	errors.add("A user with this email address already exists");
       	        	return "login.jsp";
       			} catch (RollbackException e) {
       	        	errors.add("A user with this email address already exists");
       	        	return "login.jsp";
       			} 
       		} 

	        // Look up the user
	        UserBean user = userDAO.read(loginForm.getEmailAddress());
	        
	        if (user == null) {
	            errors.add("User Name not found");
	            return "login.jsp";
	        }

	        // Check the password
	        if (!user.getPassword().equals(loginForm.getPassword())) {
	            errors.add("Incorrect password");
	            return "login.jsp";
	        }
	
	        // Attach (this copy of) the user bean to the session
	        session.setAttribute("user",user);
	        
	        // If redirectTo is null, redirect to the "todolist" action
			return "favorite.do";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
