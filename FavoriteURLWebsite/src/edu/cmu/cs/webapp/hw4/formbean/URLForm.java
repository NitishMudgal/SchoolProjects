package edu.cmu.cs.webapp.hw4.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;
/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
public class URLForm extends FormBean {
	private int favoriteId;
	private int userId;
    private String action;

    public int getFavoriteId() { return favoriteId; }
    public int getUserId() { return userId; }
    public String getAction() { return action; }
    
	public void setFavoriteId(String s) { this.favoriteId = Integer.parseInt(s.trim()); }
	public void setUserId(String s) { this.userId = Integer.parseInt(s.trim()); }
	public void setAction(String s) { this.action = s.trim(); }
	
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (favoriteId == 0)
            errors.add("URLId is required");
        if (userId == 0)
            errors.add("UserId is required");
        if (action == null)
            errors.add("Button is required");

        if (errors.size() > 0)
            return errors;

        if (!action.equals("AddFavorite"))
            errors.add("Invalid button");
        //if (!url.matches("^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$"))
           // errors.add("Invalid URL. It should start from 'http://' or 'https://'");

        return errors;
    }

}
