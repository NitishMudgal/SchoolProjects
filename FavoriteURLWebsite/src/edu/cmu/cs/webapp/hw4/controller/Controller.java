/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
package edu.cmu.cs.webapp.hw4.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import edu.cmu.cs.webapp.hw4.databean.FavoriteBean;
import edu.cmu.cs.webapp.hw4.databean.UserBean;
import edu.cmu.cs.webapp.hw4.model.FavoriteDAO;
import edu.cmu.cs.webapp.hw4.model.Model;
import edu.cmu.cs.webapp.hw4.model.UserDAO;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        Model model = new Model(getServletConfig());

        Action.add(new ManageAction(model));
        Action.add(new ListAction(model));
        Action.add(new ChangePwdAction(model));
        Action.add(new DeleteFavoriteAction(model));
        Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new FavoriteAction(model));
        
        initializeDB(model);
    }
    
    private void initializeDB(Model model){
    	 //Creating users in database
        UserBean user1 = new UserBean();
        user1.setEmailAddress("nmudgal@andrew.cmu.edu");
        user1.setFirstName("Nitish");
        user1.setLastName("Mudgal");
        user1.setPassword("1234");
        
      //Creating users in database
        UserBean user2 = new UserBean();
        user2.setEmailAddress("batman@andrew.cmu.edu");
        user2.setFirstName("Bruce");
        user2.setLastName("Wayne");
        user2.setPassword("1234");
        
      //Creating users in database
        UserBean user3 = new UserBean();
        user3.setEmailAddress("superman@andrew.cmu.edu");
        user3.setFirstName("Clark");
        user3.setLastName("Kent");
        user3.setPassword("1234");
        
        UserDAO userDAO = model.getUserDAO();
        try {
			userDAO.create(user1);
			userDAO.create(user2);
			userDAO.create(user3);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        FavoriteBean fav1 = new FavoriteBean();
        fav1.setClickCount(0);
        fav1.setComment("It's a comment");
        fav1.setUrl("https://www.google.com/");
        fav1.setUserId(1);
        
        FavoriteBean fav2 = new FavoriteBean();
        fav2.setClickCount(0);
        fav2.setComment("It's a comment");
        fav2.setUrl("https://www.facebook.com/");
        fav2.setUserId(1);
        
        FavoriteBean fav3 = new FavoriteBean();
        fav3.setClickCount(0);
        fav3.setComment("It's a comment");
        fav3.setUrl("https://www.youtube.com/");
        fav3.setUserId(1);
        
        FavoriteBean fav4 = new FavoriteBean();
        fav4.setClickCount(0);
        fav4.setComment("It's a comment");
        fav4.setUrl("https://www.cmu.com/");
        fav4.setUserId(1); 
        
        FavoriteBean fav5 = new FavoriteBean();
        fav5.setClickCount(0);
        fav5.setComment("It's a comment");
        fav5.setUrl("https://www.google.com/");
        fav5.setUserId(2);
        
        FavoriteBean fav6 = new FavoriteBean();
        fav6.setClickCount(0);
        fav6.setComment("It's a comment");
        fav6.setUrl("https://www.facebook.com/");
        fav6.setUserId(2);
        
        FavoriteBean fav7 = new FavoriteBean();
        fav7.setClickCount(0);
        fav7.setComment("It's a comment");
        fav7.setUrl("https://www.youtube.com/");
        fav7.setUserId(2);
        
        FavoriteBean fav8 = new FavoriteBean();
        fav8.setClickCount(0);
        fav8.setComment("It's a comment");
        fav8.setUrl("https://www.cmu.com/");
        fav8.setUserId(2);
        
        FavoriteBean fav9 = new FavoriteBean();
        fav9.setClickCount(0);
        fav9.setComment("It's a comment");
        fav9.setUrl("https://www.google.com/");
        fav9.setUserId(3);
        
        FavoriteBean fav10 = new FavoriteBean();
        fav10.setClickCount(0);
        fav10.setComment("It's a comment");
        fav10.setUrl("https://www.facebook.com/");
        fav10.setUserId(3);
        
        FavoriteBean fav11 = new FavoriteBean();
        fav10.setClickCount(0);
        fav10.setComment("It's a comment");
        fav10.setUrl("https://www.youtube.com/");
        fav10.setUserId(3);
        
        FavoriteBean fav12 = new FavoriteBean();
        fav11.setClickCount(0);
        fav11.setComment("It's a comment");
        fav11.setUrl("https://www.cmu.com/");
        fav11.setUserId(3);
        
        
        FavoriteDAO favDAO = model.getFavoriteDAO();
        try{
        	favDAO.create(fav2);
        	favDAO.create(fav3);
        	favDAO.create(fav4);
        	favDAO.create(fav5);
        	favDAO.create(fav6);
        	favDAO.create(fav7);
        	favDAO.create(fav8);
        	favDAO.create(fav9);
        	favDAO.create(fav10);
        	favDAO.create(fav11);
        	favDAO.create(fav12);
        	favDAO.create(fav1);
        	
        } catch (RollbackException ef){
        	// TODO Auto-generated catch block
        	ef.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage, request, response);
    }

    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * 
     * @param request
     * 
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        UserBean user = (UserBean) session.getAttribute("user");
        String action = getActionName(servletPath);
        
        /*if (user == null) {
            // If the user hasn't logged in, so login is the only option
            return Action.perform("manage.do", request);
        }*/

        // Let the logged in user run his chosen action
        return Action.perform(action, request);
    }

    /*
     * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
     * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
     * page (the view) This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                    + nextPage);
            d.forward(request, response);
            return;
        }

        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}
