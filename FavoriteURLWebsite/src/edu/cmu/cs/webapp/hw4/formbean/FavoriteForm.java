/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
package edu.cmu.cs.webapp.hw4.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class FavoriteForm extends FormBean {
	private String url;
    private String comment;
    private String action;

    public String getUrl() { return url; }
    public String getComment() { return comment; }
    public String getAction() { return action; }
    
	public void setUrl(String s) { this.url = s.trim(); }
	public void setComment(String s) { this.comment = s.trim(); }
	public void setAction(String s) { this.action = s.trim(); }
	
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (url == null || url.length() == 0)
            errors.add("URL is required");
        if (action == null)
            errors.add("Button is required");
        if (!action.equals("AddFavorite"))
            errors.add("Invalid button");
        if (errors.size() > 0)
            return errors;

        return errors;
    }

}
