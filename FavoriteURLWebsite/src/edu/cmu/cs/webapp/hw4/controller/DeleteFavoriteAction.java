
/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
package edu.cmu.cs.webapp.hw4.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import edu.cmu.cs.webapp.hw4.formbean.*;
import edu.cmu.cs.webapp.hw4.databean.*;
import edu.cmu.cs.webapp.hw4.model.*;

/*
 * Looks up the photos for a given "user".
 * 
 * If successful:
 *   (1) Sets the "userList" request attribute in order to display
 *       the list of users on the navbar.
 *   (2) Sets the "photoList" request attribute in order to display
 *       the list of given user's photos for selection.
 *   (3) Forwards to list.jsp.
 */
public class DeleteFavoriteAction extends Action {
	private FormBeanFactory<DeleteFavoriteForm> formBeanFactory = FormBeanFactory.getInstance(DeleteFavoriteForm.class);

	private FavoriteDAO favDAO;
	private UserDAO userDAO;

	public DeleteFavoriteAction(Model model) {
		favDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "deletefavorite.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up the request attributes (the errors list and the form bean so
		// we can just return to the jsp with the form if the request isn't
		// correct)
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// Set up user list for nav bar
			request.setAttribute("userList", userDAO.getUsers());
			
			DeleteFavoriteForm form = formBeanFactory.create(request);
			
			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "delete-fav.jsp";
			}
			
			UserBean user = (UserBean)request.getSession().getAttribute("user");
			if(user == null){
				errors.add("User must be specified. Session has timedout");
				return "error.jsp";
			}

			String emailAddress = user.getEmailAddress();
			if (emailAddress == null || emailAddress.length() == 0) {
				errors.add("User must be specified. Session has timedout");
				return "error.jsp";
			}
			FavoriteBean[] favoriteList = favDAO.getUserFavorites(user.getUserId());
			request.setAttribute("favoriteList", favoriteList);			
			favDAO.delete(user.getUserId(), form.getFavoriteUrl());
			request.setAttribute("successMessage", "URL was successfully deleted.");
			return "favorite.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "delete-fav.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "delete-fav.jsp";
		}
	}
}
