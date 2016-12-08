package src.model;

/**
 * Represents an album review entity.
 * @author Viggo
 *
 */
public class AlbumReview extends MediaReview {
	
	public AlbumReview(int id, String text, int rating, User user, int albumId)
	{
		super(id,text,rating,user, albumId);

	}
	public AlbumReview(String text, int rating, User user, int albumId)
	{
		super(text,rating,user, albumId);

	}
	public int getAlbumId() {
		return getMediaId();
	}

	public void setMovieId(int movieId) {
		setMediaId(movieId);
	}
	

	
}
