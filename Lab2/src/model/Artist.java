package src.model;
/**
 * Represents an artist entity.
 * @author Viggo
 *
 */
public class Artist extends Person {
	private String bio;
	
	public Artist(int id, String name, String bio)
	{
		this.setId(id);
		this.setName(name);
		this.setBio(bio);
	}

	public Artist() {
		// TODO Auto-generated constructor stub
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
}
