package model;

import java.sql.Date;
import java.util.List;

public class Album {
	private int id;
	private String name;
	private Date releaseDate;
	private List<Artist> artists;
	
	
	public Album(int id, String name, Date releaseDate, List<Artist> artists)
	{
		this.id = id;
		this.name = name;
		this.releaseDate = releaseDate;
		this.setArtists(artists);
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
}
