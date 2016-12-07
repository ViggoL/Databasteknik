package src.model;
/**
 * Represents an album genre entity.
 * @author Viggo
 *
 */
public class AlbumGenre extends Genre {
	public AlbumGenre()
	{
		
	}
	
	public AlbumGenre(int id, String name) {
		this.id = id;
		this.name = name;
	}
}
