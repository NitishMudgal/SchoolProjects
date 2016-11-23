
/*Name: Nitish Mudgal
* AndrewId : nmudgal
* Date: 10 Oct 2016
* Course No : 08672
*/
package edu.cmu.cs.webapp.hw4.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import edu.cmu.cs.webapp.hw4.databean.FavoriteBean;
import edu.cmu.cs.webapp.hw4.databean.UserBean;

public class FavoriteDAO extends GenericDAO<FavoriteBean>{
	public FavoriteDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(FavoriteBean.class, tableName, cp);
	}
	
	public FavoriteBean[] getUserFavorites(int userId) throws RollbackException{
		try{
			FavoriteBean[] favs = match(MatchArg.equals("userId",userId));
		     if (favs == null || favs.length == 0) {
		         return null;
		     }
		     return favs;
		}finally {
	         if (Transaction.isActive())
	             Transaction.rollback();
		}
	}
	
	public void updateCount(int userId, int favoriteId) throws RollbackException{
		try{
			Transaction.begin();
			FavoriteBean[] favs = match(MatchArg.equals("userId",userId));
			if (favs == null || favs.length == 0) {
				return;
			}
			
			for(FavoriteBean fav : favs){
				if(fav.getFavoriteId() == favoriteId) {
					fav.setClickCount(fav.getClickCount()+1);
					update(fav);
				}
			}			
			
			Transaction.commit();
		}finally {
			if(Transaction.isActive())
				Transaction.rollback();
		}
	}
	
	public void delete(int userId, String url) throws RollbackException {
        try {
        	boolean isDeleted = false;
        	Transaction.begin();
            FavoriteBean[] favoriteList = getUserFavorites(userId);

            if (favoriteList == null || favoriteList.length == 0) {
                throw new RollbackException("No favorite urls stored for the user.");
            }
            
            for(FavoriteBean fav : favoriteList){
            	if(fav.getUrl().equalsIgnoreCase(url)) {
            		delete(fav.getFavoriteId());
            		isDeleted = true;
            	}            		
            }
            
            if(!isDeleted){
            	throw new RollbackException("Url: " + url+ " not found.");
            }
            Transaction.commit();
        } /*catch (Exception e){
        	System.out.println(e.getMessage());
        } */finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
}
