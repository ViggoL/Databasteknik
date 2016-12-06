package src.model;
/**
 * Represents an artist entity.
 * @author Viggo
 *
 */
public class Artist {
	private int id;
	private String name;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}
