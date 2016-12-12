package src.model;

public class MediaPerson extends Person {

	private String bio;
	private MediaPersonType profession;

	public MediaPerson(int id,String name,String bio) {
		super(id,name);
		this.setBio(bio);
	}
	
	public MediaPerson(String name, String bio){
		this(name);
		this.setBio(bio);
	}
	
	public MediaPerson(String name, MediaPersonType profession, String bio) {
		this(name,bio);
		this.profession = profession;
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
	
	public void setProfession(MediaPersonType profession){
		this.profession = profession;
	}
	
	public MediaPersonType getProfession() throws NullPointerException {
		return this.profession;
	}
	@Override
	public String toString(){
		return "[" + String.valueOf(getId()) + "," + name + "," + profession + "," + bio + "]";
	}

}