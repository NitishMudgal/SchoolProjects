/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
package edu.cmu.cs.webapp.hw4.model;

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import edu.cmu.cs.webapp.hw4.databean.UserBean;

public class UserDAO extends GenericDAO<UserBean> {
	public UserDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(UserBean.class, tableName, cp);
	}
	
	public UserBean read(String emailAddress) throws RollbackException{
		try{
			UserBean[] user = match(MatchArg.equals("emailAddress",emailAddress));
		     if (user == null || user.length == 0) {
		         return null;
		     }
		     return user[0];
		}finally {
	         if (Transaction.isActive())
	             Transaction.rollback();
		}
	}
	
	public void create(UserBean user) throws RollbackException{
		try{
			UserBean[] duplicateUser = match(MatchArg.equals("emailAddress",user.getEmailAddress()));
		     if (duplicateUser != null && duplicateUser.length >0) {
		    	 throw new RollbackException("User with the email address exists.");
		     }else{
		    	 super.create(user);
		     }
		}finally {
	         if (Transaction.isActive())
	             Transaction.rollback();
		}
	}
	
	public UserBean setPassword(String emailAddress, String oldPassword, String newPassword) throws RollbackException, Exception {
        try {
            Transaction.begin();
            UserBean dbUser = read(emailAddress);
            
            if (dbUser == null) {
                throw new RollbackException("User with " + emailAddress + " no longer exists");
            }            
            if(!dbUser.getPassword().equals(oldPassword)){
            	throw new Exception("Old password does not match the password provided by you.");
            }            

            dbUser.setPassword(newPassword);

            update(dbUser);
            Transaction.commit();
            
            return dbUser;
        } catch (Exception e){
        	System.out.println(e.getMessage());
        	return null;
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
	
	public UserBean[] getUsers() throws RollbackException {
        UserBean[] users = match();
        Arrays.sort(users); // We want them sorted by last and first names (as per User.compareTo());
        return users;
    }
}
