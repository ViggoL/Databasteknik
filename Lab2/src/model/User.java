package src.model;

/**
 * Represents the user entity.
 * @author Viggo & Johan
 *
 */
public class User extends Person{
    private String pwHash;
    private String email;
	private String userStringId;

    public User(int userId, String userName, String pwHash, String email) 
    {
        super(userId,userName);
        this.pwHash = pwHash;
        this.email = email;
        this.userStringId = "";
    }
    
    public User(String userId, String userName, String pwHash, String email) 
    {
        super(0,userName);
        this.pwHash = pwHash;
        this.email = email;
        this.userStringId = userId;
    }
    
    public User(String userName){
    	super(userName);
    	this.pwHash = org.apache.commons.codec.digest.DigestUtils.sha1Hex("Incorrect");;
    }

    public int getUserId() {
        return getId();
    }

    public void setUserId(int userId) {
        setId(userId);
    }
    
    public void setUserId(String userId) {
        this.userStringId = userId;
    }

    public String getUserName() {
        return getName();
    }

    public void setUserName(String userName) {
        setName(userName);
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
