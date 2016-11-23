package edu.cmu.cs.webapp.hw4.databean;

import org.genericdao.PrimaryKey;

/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
@PrimaryKey("userId")
public class UserBean implements Comparable<UserBean>{
	private int userId;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String password;
	
	public int compareTo(UserBean other) {
		// Order first by lastName and then by firstName
		int c = lastName.compareTo(other.lastName);
		if (c != 0) return c;
		c = firstName.compareTo(other.firstName);
		if (c != 0) return c;
		return emailAddress.compareTo(other.emailAddress);
	}

	public boolean equals(Object obj) {
		if (obj instanceof UserBean) {
			UserBean other = (UserBean) obj;
			return emailAddress.equals(other.emailAddress);
		}
		return false;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	
	
	
	
	
}
