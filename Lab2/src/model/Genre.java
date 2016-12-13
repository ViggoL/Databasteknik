package src.model;

public class Genre {

	protected int id;
	protected String name;

	public Genre(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String toString(){
		return this.name;
	}
	
	public Genre(String s) {
		this(-1,s);
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

}