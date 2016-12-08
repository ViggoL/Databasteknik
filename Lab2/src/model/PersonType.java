package src.model;

public enum PersonType {
	ARTIST("Artist"),DIRECTOR("Director"),USER("User");
	
	private final String name;
	
	PersonType(String ptype){
		this.name = ptype;
	}
	
	public String toString(){
		return this.name;
	}

}
