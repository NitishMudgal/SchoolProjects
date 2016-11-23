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

import org.genericdao.RollbackException;

import edu.cmu.cs.webapp.hw4.model.Model;
import edu.cmu.cs.webapp.hw4.model.UserDAO;

/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class LogoutAction extends Action {
	private UserDAO userDAO;
    public LogoutAction(Model model) {
    	userDAO = model.getUserDAO();
    }

    public String getName() {
        return "logout.do";
    }

    public String perform(HttpServletRequest request) { 
    	List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try {
        	HttpSession session = request.getSession(false);
			request.setAttribute("userList",userDAO.getUsers());
			session.setAttribute("user", null);
			return "login.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
        	return "error.jsp";
		}
    }
}
