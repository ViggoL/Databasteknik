package src.model;

public class MovieReview {
	
	private int id;
	private String text;
	private int rating;
	private User user;
	private int movieId;
	
	public MovieReview(int id, String text, int rating, User user, int movieId)
	{
		this.setId(id);
		this.setText(text);
		this.setRating(rating);
		this.setUser(user);
		this.setMovieId(movieId);
	}

	public MovieReview() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) throws IndexOutOfBoundsException {
		if (rating < 0 || rating > 10) 
			throw new IndexOutOfBoundsException("Rating must be between zero and ten");
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	
}
