package src.model;

public enum PersonType {
	ARTIST("Artist"),DIRECTOR("Director"),USER("User");
	
	private final String name;
	
	private PersonType(String s){
		this.name = s;
	}
	
	public String toString(){
		return this.name;
	}

}
