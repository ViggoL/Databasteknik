package src.model;

public abstract class MediaPerson extends Person {

	private String bio;

	public MediaPerson(int id,String name,String bio) {
		super(id,name);
		this.setBio(bio);
	}
	
	public MediaPerson(String name, String bio){
		this(name);
		this.setBio(bio);
	}
	
	public MediaPerson(String name){
		super(name);
	}
	

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

}