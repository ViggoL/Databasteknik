package src.model;

/**
 * Represents the user entity.
 * @author Viggo
 *
 */
public class User {
    private int id;
    private String name;
    private String pwHash;
    private String email;

    public User(int userId, String userName, String pwHash, String email)
    {
        this.id = userId;
        this.name = userName;
        this.pwHash = pwHash;
        this.email = email;
    }

    public int getUserId() {
        return id;
    }

    public void setUserId(int userId) {
        this.id = userId;
    }

    public String getUserName() {
        return name;
    }

    public void setUserName(String userName) {
        this.name = userName;
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
    
    public String toString()
    {
    	return this.name;
    }
}
