package src.model;

public abstract class MediaReview {

	private int id;
	private String text;
	private int rating;
	private User user;
	protected int mediaId;

	public MediaReview(int id, String text, int rating, User user, int mediaId) {
		super();
		this.setText(text);
		this.setRating(rating);
		this.setUser(user);
		this.setMediaId(mediaId);
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

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

}