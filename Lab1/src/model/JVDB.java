package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class JVDB {
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private String db = "jvdb", usr = "client", pwd = "lab", port = "3306", host = "viggolunden.com";

    public JVDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + db + "?useSSL=false", usr, pwd);
        stmt = conn.prepareStatement("");
    }

    public void close() throws SQLException {
        conn.close();
    }

    public boolean isOpen() throws SQLException {
        return !conn.isClosed();
    }

    public List<User> getUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        ResultSet resultSet = stmt.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (resultSet.next())
        {
            User user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            users.add(user);
        }
        return users;
    }
    public boolean addUser(String userName, String password, String email) throws SQLException {
        String sql = "INSERT INTO users (userName, pwHash, email) VALUES (?, ?, ?);";

        stmt = conn.prepareStatement(sql);
        stmt.setString(1, userName);
        stmt.setString(2, DigestUtils.sha1Hex(password));
        stmt.setString(3, email);
        return stmt.executeUpdate() != 0;
    }
    public List<Movie> getMovies() throws SQLException
    {
    	// Get all movies
    	String sql = "SELECT * FROM movies";
    	ResultSet resultSet = stmt.executeQuery(sql);
    	List<Movie> movies = new ArrayList<>();
    	while (resultSet.next())
    	{
    		Movie movie = new Movie();
    		movie.setId(resultSet.getInt(1));
    		movie.setName(resultSet.getString(2));
    		movie.setReleaseDate(resultSet.getDate(3));
    		movies.add(movie);
    	}
    	// Get the directors for each movie
    	for (Movie m : movies)
    	{
        	sql = "SELECT directors.* FROM directors, tr_movies_directors WHERE directors.directorId=tr_movies_directors.directorId AND tr_movies_directors.movieId=?;";
        	stmt = conn.prepareStatement(sql);
        	stmt.setInt(1, m.getId());
        	resultSet = stmt.executeQuery();
        	List<Director> directors = new ArrayList<>();
        	while (resultSet.next())
        	{
        		Director d = new Director(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        		directors.add(d);
        	}
        	m.setDirectors(directors);
    	}
    	return movies;
    	
    }

	public List<Artist> getArtists() throws SQLException {
		String sql = "SELECT * FROM artists";
		ResultSet rs = stmt.executeQuery(sql);
		List<Artist> artists = new ArrayList<>();
		while (rs.next()) 
		{
			Artist a = new Artist(rs.getInt(1), rs.getString(2), rs.getString(3));
			artists.add(a);
		}
		return artists;
	}
	
	public List<Genre> getGenres() throws SQLException {
		String sql = "SELECT * FROM genres";
		ResultSet rs = stmt.executeQuery(sql);
		List<Genre> genres = new ArrayList<>();
		while (rs.next())
		{
			Genre g = new Genre(rs.getInt(1), rs.getString(2));
			genres.add(g);
		}
		return genres;
	}

}
