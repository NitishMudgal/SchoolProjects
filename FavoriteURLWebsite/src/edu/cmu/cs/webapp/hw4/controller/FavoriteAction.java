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
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.hw4.databean.FavoriteBean;
import edu.cmu.cs.webapp.hw4.databean.UserBean;
import edu.cmu.cs.webapp.hw4.formbean.FavoriteForm;
import edu.cmu.cs.webapp.hw4.formbean.URLForm;
import edu.cmu.cs.webapp.hw4.model.FavoriteDAO;
import edu.cmu.cs.webapp.hw4.model.Model;
import edu.cmu.cs.webapp.hw4.model.UserDAO;

public class FavoriteAction extends Action {
	private FavoriteDAO favDAO;
	private UserDAO userDAO;
	private FormBeanFactory<FavoriteForm> favoriteFormBeanFactory = FormBeanFactory.getInstance(FavoriteForm.class);
	private FormBeanFactory<URLForm> urlFormBeanFactory = FormBeanFactory.getInstance(URLForm.class);

	public FavoriteAction(Model model) {
		favDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() { return "favorite.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        int userId;
        try {
        	 // Set up user list for nav bar
			request.setAttribute("userList",userDAO.getUsers());
			FavoriteForm favForm = favoriteFormBeanFactory.create(request);
			HttpSession session = request.getSession();
        	UserBean user = (UserBean)session.getAttribute("user");
        	if(!favForm.isPresent()){
        		URLForm urlForm = urlFormBeanFactory.create(request);
        		if(urlForm.isPresent()){
        			try{
        				if(user == null){
        					favDAO.updateCount(urlForm.getUserId(), urlForm.getFavoriteId());
        					request.setAttribute("favoriteList", favDAO.getUserFavorites(urlForm.getUserId()));
        					return ("index.jsp");
        				}else {
        					favDAO.updateCount(user.getUserId(), urlForm.getFavoriteId());
        					request.setAttribute("favoriteList", favDAO.getUserFavorites(user.getUserId()));
        	        		return ("favorite.jsp");
        				}    				
        			}catch (RollbackException e) {
        				errors.add(e.getMessage());
    		        	return "error.jsp";
        			}
        		} else{
        			   	request.setAttribute("favoriteList", favDAO.getUserFavorites(user.getUserId()));
    	        		return ("favorite.jsp");
        	        }        		
        	}else if(favForm.getAction().equals("AddFavorite")){
    	        // Any validation errors?
    	        errors.addAll(favForm.getValidationErrors());
    	        if (errors.size() != 0) {
    	            return "favorite.jsp";
    	        }

				FavoriteBean favBean = new FavoriteBean();
				favBean.setUrl(favForm.getUrl());
				favBean.setComment(favForm.getComment());
				favBean.setUserId(user.getUserId());
				try {
					favDAO.create(favBean);
					request.setAttribute("successMessage", "Url: " + favForm.getUrl() + " has been added.");
					request.setAttribute("favoriteList", favDAO.getUserFavorites(user.getUserId()));
	        		return ("favorite.jsp");					
				} catch (RollbackException e) {
					errors.add(e.getMessage());
		        	return "error.jsp";
				}				
			}
		} catch (FormBeanException e1) {
			errors.add(e1.getMessage());
        	return "error.jsp";
		} catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
        return "error.jsp";
        
    }
}
