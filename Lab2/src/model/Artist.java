package src.model;
/**
 * Represents an artist entity.
 * @author Viggo
 *
 */
public class Artist extends MediaPerson {
	public Artist(int id, String name, String bio)
	{
		super(id,name,bio);
	}
	public Artist(String name, String bio)
	{
		super(name,bio);
	}
	public Artist(String name)
	{
		super(name);
	}
}
