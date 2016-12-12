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
		this.setProfession(MediaPersonType.ARTIST);
	}
	public Artist(String name, String bio)
	{
		super(name,bio);
		this.setProfession(MediaPersonType.ARTIST);
	}
	public Artist(String name)
	{
		super(name);
		this.setProfession(MediaPersonType.ARTIST);
	}
	public Artist(MediaPersonType artist, String name, String bio) {
		super(artist,name,bio);
		this.setProfession(MediaPersonType.ARTIST);
	}
}
