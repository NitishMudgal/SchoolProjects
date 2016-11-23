/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
package edu.cmu.cs.webapp.hw4.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DeleteFavoriteForm extends FormBean {
	private String favoriteUrl;
	private String action;
	
	public String getFavoriteUrl()	   { return favoriteUrl;	}
	public String getAction()     { return action;     }
	
	public void setFavoriteUrl(String s) 	{ favoriteUrl 		= s.trim();}
	public void setAction(String s)     { action     = s.trim(); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (favoriteUrl == null || favoriteUrl.length() == 0) {
			errors.add("Please provide a url to remove.");
		}
		
		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}
}
