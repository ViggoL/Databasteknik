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
		this.setProfession(PersonType.ARTIST);
	}
	public Artist(String name, String bio)
	{
		super(name,bio);
		this.setProfession(PersonType.ARTIST);
	}
	public Artist(String name)
	{
		super(name);
		this.setProfession(PersonType.ARTIST);
	}
	public Artist(PersonType artist, String name, String bio) {
		super(artist,name,bio);
		this.setProfession(PersonType.ARTIST);
	}
}
