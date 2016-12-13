package src.model;

public class MediaPerson extends Person {

	private String bio;
	private MediaPersonType profession;

	
	public MediaPerson()
	{
		super();
	}
	
	public MediaPerson(String id, String name, MediaPersonType profession, String bio)
	{
		super(id, name);
		this.profession = profession;
		this.bio = bio;
	}
	
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public void setProfession(MediaPersonType profession){
		this.profession = profession;
	}
	
	public MediaPersonType getProfession() throws NullPointerException {
		return this.profession;
	}
	@Override
	public String toString(){
		return "[" + this.name + "," + this.profession + "]";
	}
	
	public String toMongoString(){
		return "[" + getId() + "," + name + "," + profession + "," + bio + "]";
	}
}