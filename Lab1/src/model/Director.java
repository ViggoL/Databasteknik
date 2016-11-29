package src.model;

public class Director {
	private int id;
	private String name;
	private String bio;
	
	public Director()
	{
		
	}
	
	public Director (int id, String name, String bio)
	{
		this.id = id;
		this.name = name;
		this.bio = bio;
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
}
