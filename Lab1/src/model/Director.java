package src.model;

public class Director {
	private int id;
	private String name;
	private String bio;
	
	public Director(String name)
	{
		this.name = name;
		this.bio = "";
	}
	
	public Director (int id, String name, String bio)
	{
		this(name);
		this.id = id;
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
	public String toString()
	{
		return this.name;
	}
}
