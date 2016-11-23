/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
package edu.cmu.cs.webapp.hw4.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class UserForm extends FormBean {
	private String emailAddress = "";
	
	public String getEmailAddress()  { return emailAddress; }
	
	public void setEmailAddress(String s)  { emailAddress = s; }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (emailAddress == null || emailAddress.length() == 0) {
			errors.add("Email Address is required");
		}		
		return errors;
	}
}
