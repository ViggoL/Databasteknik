package src.model;

public enum MediaPersonType {
	ARTIST("Artist"),DIRECTOR("Director"),USER("User");
	
	private final String name;
	
	private MediaPersonType(String s){
		this.name = s;
	}
	
	public String toString(){
		return this.name;
	}

}
