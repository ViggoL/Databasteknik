package src.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an album entity.
 * @author Viggo
 *
 */
public class Album extends Media {
	List<Artist> artists = new ArrayList<Artist>();
	public Album(int id, String name, Date releaseDate, List<Artist> artists, int rating, User addedBy)
	{
		this.id = id;
		this.name = name;
		this.releaseDate = releaseDate;
		this.setArtists(artists);
		this.rating = rating;
		this.setAddedBy(addedBy);
	}
	
	public Album() {
		// TODO Auto-generated constructor stub
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}
}
