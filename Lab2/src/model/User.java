package src.model;

/**
 * Represents the user entity.
 * @author Viggo & Johan
 *
 */
public class User extends Person{
    private String pwHash;
    private String email;

    public User(String userId, String userName, String pwHash, String email) 
    {
        super(userId,userName);
        this.pwHash = pwHash;
        this.email = email;
    }
    
    public User(String userName){
    	super(userName);
    	this.pwHash = org.apache.commons.codec.digest.DigestUtils.sha1Hex("Incorrect");;
    }

    public String getUserId() {
        return getId();
    }

    public void setUserId(String userId) {
        setId(userId);
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
