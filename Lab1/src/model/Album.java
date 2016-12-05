package src.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Album {
	private int id;
	private String name;
	private Date releaseDate;
	private List<Artist> artists = new ArrayList<Artist>();
	private List<AlbumGenre> genres = new ArrayList<AlbumGenre>();
	private int rating;
	
	
	public Album(int id, String name, Date releaseDate, List<Artist> artists, int rating)
	{
		this.id = id;
		this.name = name;
		this.releaseDate = releaseDate;
		this.setArtists(artists);
		this.rating = rating;
	}
	
	public Album() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<AlbumGenre> getGenres() {
		return genres;
	}

	public void setGenres(List<AlbumGenre> genres) {
		this.genres = genres;
	}
	
	public Object[] getArray()
	{
		Object[] arr = new Object[5];
		arr[0] = this.name;
		arr[1] = this.releaseDate;
		arr[2] = this.artists.toString().replace("]", "").replace("[", "");;
		arr[3] = this.genres.toString().replace("]", "").replace("[", "");;
		arr[4] = this.rating;
		return arr;
	}
	
	public boolean compareArrays(Object[] other)
	{
		Object[] thisArr = this.getArray();
		if (thisArr[0].equals(other[0]) &&
			thisArr[1].equals(other[1]) &&
			thisArr[2].equals(other[2]) &&
			thisArr[3].equals(other[3]) &&
			thisArr[4].equals(other[4])) return true;
		return false;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
