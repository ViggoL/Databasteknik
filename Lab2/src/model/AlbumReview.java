package src.model;

/**
 * Represents an album review entity.
 * @author Viggo
 *
 */
public class AlbumReview extends MediaReview {
	
	public AlbumReview(int id, String text, int rating, User user, int albumId)
	{
		this.setId(id);
		this.setText(text);
		this.setRating(rating);
		this.setUser(user);
		this.mediaId = albumId;
	}

	public AlbumReview() {
		
	}

	
}
