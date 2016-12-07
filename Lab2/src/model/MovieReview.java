package src.model;
/**
 * Represents a movie review entity.
 * @author Viggo & Johan
 *
 */
public class MovieReview extends MediaReview{
	
	public MovieReview(int id, String text, int rating, User user, int movieId)
	{
		super(id,text,rating,user, movieId);

	}

	public int getMovieId() {
		return getMediaId();
	}

	public void setMovieId(int movieId) {
		setMediaId(movieId);
	}

	
}
