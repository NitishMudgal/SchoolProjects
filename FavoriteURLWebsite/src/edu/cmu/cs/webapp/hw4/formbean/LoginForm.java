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
public class LoginForm extends FormBean{
    private String emailAddress;
    private String password;
    private String action;
    
    public String getEmailAddress() { return emailAddress; }
    public String getPassword() { return password; }
    public String getAction() { return action; }
    
	public void setEmailAddress(String s) { this.emailAddress = s.trim(); }
	public void setPassword(String s) { this.password = s.trim(); }
	public void setAction(String s) { this.action = s.trim(); }


	public List<String> getValidationErrors() {
    	
        List<String> errors = new ArrayList<String>();

        if (emailAddress == null || emailAddress.length() == 0)
            errors.add("Email Address is required");
        if (password == null || password.length() == 0)
            errors.add("Password is required");
        if (action == null)
            errors.add("Button is required");
        if (errors.size() > 0)
            return errors;
        if (!action.equals("Login") && !action.equals("Register"))
            errors.add("Invalid button");
        if (!emailAddress.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
            errors.add("Please provide valid email id.");

        return errors;
    }
}
