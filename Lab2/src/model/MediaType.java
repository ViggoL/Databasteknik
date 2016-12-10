package src.model;

public enum MediaType {
	ALBUM("Album"),MOVIE("Movie");
	
	private final String type;
	
	private MediaType(String type){
		this.type = type;
	}
	
	public String toString(){
		return this.type;
	}
}
