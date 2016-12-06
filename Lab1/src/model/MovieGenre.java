package src.model;

/**
 * Represents a movie genre entity.
 * @author Viggo
 *
 */
public class MovieGenre {
	private int id;
	private String name;
	
	public MovieGenre()
	{
		
	}
	
	public MovieGenre(int id, String name) {
		this.id = id;
		this.name = name;
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
	
	@Override
	public String toString()
	{
		return this.name;
	}
}
