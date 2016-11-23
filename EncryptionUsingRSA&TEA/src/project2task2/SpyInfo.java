package project2task2;
/**
 * @author Nitish
 * SpyInfo is the bean which stores the information of spies and the spy commander
 * It stores userName, hash of the password, salt used to create hash,
 * location of the spy and title of the spy
 */
public class SpyInfo {
	private String userName;
	private String hash;
	private String salt;
	private String location;
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * SpyInfo constructor
	 * @param userName
	 * @param hash
	 * @param salt
	 * @param location
	 * @param title
	 */
	SpyInfo (String userName, String hash, String salt, String location, String title){
		this.userName = userName;
		this.hash = hash;
		this.salt = salt;
		this.location = location;
		this.title = title;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
