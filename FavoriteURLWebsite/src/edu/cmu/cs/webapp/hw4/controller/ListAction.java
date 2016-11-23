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
public class ListAction extends Action {
	private FormBeanFactory<UserForm> formBeanFactory = FormBeanFactory
			.getInstance(UserForm.class);

	private FavoriteDAO favDAO;
	private UserDAO userDAO;

	public ListAction(Model model) {
		favDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "list.do";
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

			UserForm form = formBeanFactory.create(request);

			String emailAddress = form.getEmailAddress();
			if (emailAddress == null || emailAddress.length() == 0) {
				errors.add("User must be specified");
				return "error.jsp";
			}

			// Set up photo list
			UserBean user = userDAO.read(emailAddress);
			if (user == null) {
				errors.add("Invalid User: " + emailAddress);
				return "error.jsp";
			}

			FavoriteBean[] favoriteList = favDAO.getUserFavorites(user.getUserId());
			request.setAttribute("userName", user.getFirstName()+ " " + user.getLastName());
			request.setAttribute("favoriteList", favoriteList);
			return "index.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
