package src.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import src.model.Album;
import src.model.AlbumGenre;
import src.model.AlbumReview;
import src.model.Artist;
import src.model.Director;
import src.model.JvdbInterface;
import src.model.Movie;
import src.model.MovieAttributes;
import src.model.MovieGenre;
import src.model.MovieReview;
import src.model.Operations;
import src.model.User;

public class JVDB implements JvdbInterface {
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private String db = "jvdb", usr = "client", pwd = "lab", port = "3306", host = "viggolunden.com";
	private int userId;

	public JVDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + db + "?useSSL=false",
				usr, pwd);
		stmt = conn.prepareStatement("");
		stmt.close();
	}
	
	@Override
	public int getUserId()
	{
		return this.userId;
	}

	@Override
	public boolean albumReviewExists(int userId, int albumId) throws SQLException
	{
		String sql = "SELECT * FROM album_reviews WHERE userId=? AND albumId=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, albumId);
			ResultSet rs = stmt.executeQuery();
			return (rs.isBeforeFirst());
		} finally {
			stmt.close();
		}
	}
	
	@Override
	public boolean movieReviewExists(int userId, int movieId) throws SQLException
	{
		String sql = "SELECT * FROM movie_reviews WHERE userId=? AND movieId=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, movieId);
			ResultSet rs = stmt.executeQuery();
			return (rs.isBeforeFirst());
		} finally {
			stmt.close();
		}
	}
	
	@Override
	public void addArtist(Artist artist) throws SQLException{
		String sql = "INSERT INTO artists (artistName, artistBio) VALUES (?,?);";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, artist.getName());
			stmt.setString(2, artist.getBio());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}

	@Override
	public void addDirector(Director director) throws SQLException{
		String sql = "INSERT INTO directors (directorName, directorBio) VALUES (?,?);";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, director.getName());
			stmt.setString(2, director.getBio());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	@Override
	public void addMovieReview(MovieReview review) throws SQLException
	{
		String sql = "INSERT INTO movie_reviews (userId, reviewText, rating, movieId) VALUES (?,?,?,?);";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, this.userId);
			stmt.setString(2, review.getText());
			stmt.setInt(3, review.getRating());
			stmt.setInt(4, review.getMovieId());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	@Override
	public void addAlbumReview(AlbumReview review) throws SQLException
	{
		String sql = "INSERT INTO album_reviews (userId, reviewText, rating, albumId) VALUES (?,?,?,?);";
		try {
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, this.userId);
		stmt.setString(2, review.getText());
		stmt.setInt(3, review.getRating());
		stmt.setInt(4, review.getAlbumId());
		stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	/**
	 * Closes the connection to the database.
	 */
	@Override
	public void close() throws SQLException {
		stmt.close();
		conn.close();
		
	}

	/**
	 * Returns wether the connection is open or not.
	 */
	@Override
	public boolean isOpen() throws SQLException {
		return !conn.isClosed();
	}

	/**
	 * Returns all users.
	 */
	@Override
	public List<User> getUsers() throws SQLException {
		String sql = "SELECT * FROM users";
		List<User> users;
		try {
			ResultSet resultSet = stmt.executeQuery(sql);
			users = new ArrayList<>();
			while (resultSet.next()) {
				User user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4));
				users.add(user);
			} 
		} finally {
			stmt.close();
		}
		return users;
	}



	/**
	 * Adds a user. The password parameter is hashed before inserted.
	 */
	@Override
	public boolean addUser(String userName, String password, String email) throws SQLException {
		String sql = "INSERT INTO users (userName, pwHash, email) VALUES (?, ?, ?);";

		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userName);
		stmt.setString(2, DigestUtils.sha1Hex(password));
		stmt.setString(3, email);
		try {
			return stmt.executeUpdate() != 0;
		} finally {
			stmt.close();
		}
	}

	/**
	 * Returns all movies with the specified filter
	 */
	@Override
	public List<Movie> getMovies(MovieAttributes attribute, String value) throws SQLException {
		// Create query
		String sql = new String();
		switch (attribute) {
		case TITLE:
			sql = "SELECT * FROM movies WHERE movieTitle LIKE ?;";
			break;
		case DIRECTOR:
			sql = "SELECT movies.* " + "FROM movies,directors,tr_movies_directors " + "WHERE directors.directorName "
					+ "LIKE ? " + "AND directors.directorId=tr_movies_directors.directorId "
					+ "AND movies.movieId=tr_movies_directors.movieId;";
			break;
		case RATING:
			sql = "SELECT movies.* " + "FROM movies,movie_reviews " + "WHERE rating = ? "
					+ "AND movies.movieId=movie_reviews.movieId;";
			break;
		case RELEASE_DATE:
			sql = "SELECT * FROM movies WHERE movieReleaseDate LIKE ?;";
			break;
		case GENRE:
			sql = "SELECT movies.* " + "FROM movies,movie_genres,tr_movies_genres " + "WHERE movies_genres.genreName "
					+ "LIKE ? " + "AND movies_genres.genreId=tr_movies_genres.genreId "
					+ "AND movies.movieId=tr_movies_genres.movieId;";
			break;
		case ALL:
			sql = "SELECT * FROM movies;";
			break;
		}
		List<Movie> movies;
		try {
			// Get movies
			ResultSet resultSet;
			if (attribute == MovieAttributes.ALL) {
				stmt = conn.prepareStatement(sql);
				resultSet = stmt.executeQuery();
			} else {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%" + value + "%");
				resultSet = stmt.executeQuery();
			}
			movies = new ArrayList<>();
			while (resultSet.next()) {
				Movie movie = new Movie();
				movie.setId(resultSet.getInt(1));
				movie.setTitle(resultSet.getString(2));
				movie.setReleaseDate(resultSet.getDate(3));
				movie.setRating(resultSet.getInt(4));

				movies.add(movie);
			}
			// Get who added each movie
			for (Movie m : movies) {
				sql = "SELECT * FROM users WHERE userId= (SELECT addedBy FROM movies WHERE movieId=? LIMIT 1); ";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, m.getId());
				resultSet = stmt.executeQuery();
				resultSet.next();
				User user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4));
				m.setAddedBy(user);
			}
			// Get the directors for each movie
			for (Movie m : movies) {
				sql = "SELECT directors.* FROM directors, tr_movies_directors WHERE directors.directorId=tr_movies_directors.directorId AND tr_movies_directors.movieId=?;";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, m.getId());
				resultSet = stmt.executeQuery();
				List<Director> directors = new ArrayList<>();
				while (resultSet.next()) {
					Director d = new Director(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
					directors.add(d);
				}
				m.setDirectors(directors);

				sql = "SELECT movie_genres.* FROM movie_genres, tr_movies_genres WHERE movie_genres.genreId=tr_movies_genres.genreId AND tr_movies_genres.movieId=?;";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, m.getId());
				resultSet = stmt.executeQuery();
				List<MovieGenre> genres = new ArrayList<>();
				while (resultSet.next()) {
					MovieGenre mg = new MovieGenre(resultSet.getInt(1), resultSet.getString(2));
					genres.add(mg);
				}
				m.setGenres(genres);
			} 
		} finally {
			stmt.close();
		}
		return movies;

	}

	@Override
	public void AddMovie(Movie movie) throws SQLException {
		Savepoint save1 = null;

		conn.setAutoCommit(false);

		// Jämför regissörerna i databasen mot de angivna
		String sql = "SELECT * FROM directors;";
		try {
			save1 = conn.setSavepoint();
			stmt = conn.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery(sql);
			ArrayList<Director> directors = new ArrayList<>();

			while (resultSet.next()) {
				Director d = new Director(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
				directors.add(d);
			}

			ArrayList<Director> newDirs = (ArrayList<Director>) ((ArrayList<Director>) movie.getDirectors()).clone();
			ArrayList<Director> dirs = new ArrayList<>();
			boolean directorExists = false;
			for (Director d : newDirs) {
				for (Director dbDirector : directors) {
					if (dbDirector.getName().toString().equals(d.getName().toString())) {
						d.setId(dbDirector.getId());
						dirs.add(d);
						directorExists = true;
						break;
					}
				}
				if (!directorExists) {
					// Om regissören inte finns i databasen bör denne läggas
					// till:
					sql = "INSERT INTO directors (directorName,directorBio) VALUES (?,'');";
					stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					stmt.setString(1, d.getName());
					int addedDirector = stmt.executeUpdate();

					// När tillfälle ges kan detta bli ett fönster som visar
					// användaren tillägget
					// System.out.print("\nDirector added: " + d.getName());

					ResultSet keys = stmt.getGeneratedKeys();
					keys.next();
					int directorId = keys.getInt(1);
					Director a = new Director(directorId, d.getName(), "");
					dirs.add(a);
				}
			}
			// }

			// lägg till filmen och fånga upp IDt
			sql = "INSERT INTO movies (movieTitle,movieReleaseDate,addedBy) VALUES (?, ?);";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, movie.getTitle());
			stmt.setDate(2, movie.getReleaseDate());
			stmt.setInt(3, this.userId);
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next();
			int movieId = keys.getInt(1);

			for (Director d : dirs) {
				sql = "INSERT INTO tr_movies_directors (directorId, movieId) VALUES (?,?);";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, d.getId());
				stmt.setInt(2, movieId);
				int addedDirectors = stmt.executeUpdate();

				System.out.println("Director relations added: " + addedDirectors);
			}
			
			for (MovieGenre mg: movie.getGenres()){
				sql = "INSERT INTO tr_movies_genres (movieId, genreId) VALUES (?,?);";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, movieId);
				stmt.setInt(2, mg.getId());
				int addedGenre = stmt.executeUpdate();
				
				System.out.println("Genre relations added: " + addedGenre);
			}
			

			conn.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (save1 != null)
				conn.rollback(save1);
		} finally {
			conn.setAutoCommit(true);
			stmt.close();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see src.model.JvdbInterface#getArtists()
	 */
	@Override
	public List<Artist> getArtists() throws SQLException {
		String sql = "SELECT * FROM artists";
		List<Artist> artists;
		try {
			ResultSet rs = stmt.executeQuery(sql);
			artists = new ArrayList<>();
			while (rs.next()) {
				Artist a = new Artist(rs.getInt(1), rs.getString(2), rs.getString(3));
				artists.add(a);
			} 
		} finally {
			stmt.close();
		}
		return artists;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see src.model.JvdbInterface#getGenres()
	 */
	@Override
	public List<AlbumGenre> getAlbumGenres() throws SQLException {
		String sql = "SELECT * FROM album_genres;";
		List<AlbumGenre> genres;
		try {
			ResultSet rs = stmt.executeQuery(sql);
			genres = new ArrayList<>();
			while (rs.next()) {
				AlbumGenre g = new AlbumGenre(rs.getInt(1), rs.getString(2));
				genres.add(g);
			} 
		} finally {
			stmt.close();
		}
		return genres;
	}

	@Override
	public List<MovieGenre> getMovieGenres() throws SQLException {
		String sql = "SELECT * FROM movie_genres";
		List<MovieGenre> genres;
		try {
			ResultSet rs = stmt.executeQuery(sql);
			genres = new ArrayList<>();
			while (rs.next()) {
				MovieGenre g = new MovieGenre(rs.getInt(1), rs.getString(2));
				genres.add(g);
			} 
		} finally {
			stmt.close();
		}
		return genres;
	}
	
	
	@Override
	public List<Album> getAlbums(Operations operation, String value) throws SQLException {
		try {
			String sql = "SELECT * FROM albums";
			ResultSet rs;
			List<Album> albums = new ArrayList<>();
			switch (operation) {
			case ALL:
				sql = "SELECT * FROM albums";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next()) {
					Album album = new Album();
					album.setId(rs.getInt(1));
					album.setName(rs.getString(2));
					album.setReleaseDate(rs.getDate(3));
					album.setRating(rs.getInt(4));
					albums.add(album);
				}
				break;
			case ARTIST:
				String sqlId = "SELECT albumId FROM tr_albums_artists WHERE artistId = (SELECT artistId FROM artists WHERE artistName LIKE ?)";
				stmt = conn.prepareStatement(sqlId);
				List<Integer> albumIds = new ArrayList<>();
				stmt.setString(1, value);
				rs = stmt.executeQuery();
				while (rs.next()) {
					albumIds.add(new Integer(rs.getInt(1)));
				}
				sql = "SELECT * FROM albums WHERE albumId = ?;";
				for (int i = 0; i < albumIds.size(); i++) {
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, albumIds.get(i).intValue());
					rs = stmt.executeQuery();
					rs.next();
					Album album = new Album();
					album.setId(rs.getInt(1));
					album.setName(rs.getString(2));
					album.setReleaseDate(rs.getDate(3));
					album.setRating(rs.getInt(4));
					albums.add(album);
				}

				break;
			case GENRE:
				String sqlId2 = "SELECT albumId FROM tr_albums_genres WHERE genreId = (SELECT genreId FROM album_genres WHERE genreName=?);";
				stmt = conn.prepareStatement(sqlId2);
				stmt.setString(1, value);
				rs = stmt.executeQuery();
				List<Integer> albumids = new ArrayList<>();
				while (rs.next())
					albumids.add(new Integer(rs.getInt(1)));
				sql = "SELECT * FROM albums WHERE albumId = ?;";

				for (int i = 0; i < albumids.size(); i++) {
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, albumids.get(i).intValue());
					rs = stmt.executeQuery();
					rs.next();
					Album album = new Album();
					album.setId(rs.getInt(1));
					album.setName(rs.getString(2));
					album.setReleaseDate(rs.getDate(3));
					album.setRating(rs.getInt(4));
					albums.add(album);
				}

				while (rs.next()) {
					Album album = new Album();
					album.setId(rs.getInt(1));
					album.setName(rs.getString(2));
					album.setReleaseDate(rs.getDate(3));
					album.setRating(rs.getInt(4));
					albums.add(album);
				}
				break;
			case NAME:
				sql = "SELECT * FROM albums WHERE albumName LIKE ?;";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%" + value + "%");
				rs = stmt.executeQuery();
				while (rs.next()) {
					Album album = new Album();
					album.setId(rs.getInt(1));
					album.setName(rs.getString(2));
					album.setReleaseDate(rs.getDate(3));
					album.setRating(rs.getInt(4));
					albums.add(album);
				}
				break;
			case RATING:
				sql = "SELECT * FROM albums WHERE albumRating=?;";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, value);
				rs = stmt.executeQuery();
				while (rs.next()) {
					Album album = new Album();
					album.setId(rs.getInt(1));
					album.setName(rs.getString(2));
					album.setReleaseDate(rs.getDate(3));
					album.setRating(rs.getInt(4));
					albums.add(album);
				}
				break;
			case RELEASEDATE:
				sql = "SELECT * FROM albums WHERE albumReleaseDate=?;";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, value);
				rs = stmt.executeQuery();
				while (rs.next()) {
					Album album = new Album();
					album.setId(rs.getInt(1));
					album.setName(rs.getString(2));
					album.setReleaseDate(rs.getDate(3));
					album.setRating(rs.getInt(4));
					albums.add(album);
				}
				break;
			default:
				stmt.close();
				return null;

			}
			if (albums.size() < 1)
				return null;
			// Get the genres and artists for each album
			for (Album album : albums) {
				sql = "SELECT artists.* FROM artists, tr_albums_artists WHERE artists.artistId=tr_albums_artists.artistId AND tr_albums_artists.albumId=?;";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, album.getId());
				rs = stmt.executeQuery();
				List<Artist> artists = new ArrayList<>();
				while (rs.next()) {
					Artist a = new Artist(rs.getInt(1), rs.getString(2), rs.getString(3));
					artists.add(a);
				}
				album.setArtists(artists);
				sql = "SELECT * FROM album_genres, tr_albums_genres WHERE album_genres.genreId=tr_albums_genres.genreId AND tr_albums_genres.albumId=?;";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, album.getId());
				rs = stmt.executeQuery();
				List<AlbumGenre> genres = new ArrayList<>();
				while (rs.next()) {
					AlbumGenre g = new AlbumGenre(rs.getInt(1), rs.getString(2));
					genres.add(g);
				}
				album.setGenres(genres);

				sql = "SELECT * FROM users WHERE userId= (SELECT addedBy FROM albums WHERE albumId=? LIMIT 1); ";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, album.getId());
				rs = stmt.executeQuery();
				rs.next();
				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				album.setAddedBy(user);
			}
			return albums;
		} finally {
			stmt.close();
		}
	}

	@Override
	public void addAlbum(Album album) throws SQLException, NullPointerException {
		try {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO albums (albumName, albumReleaseDate, albumRating, addedBy) VALUES (?,?,?,?);";
			System.out.println(sql + "\n" + album.toString());
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, album.getName());
			stmt.setDate(2, album.getReleaseDate());
			stmt.setInt(3, album.getRating());
			stmt.setInt(4, this.userId);
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next();
			int albumId = keys.getInt(1);
			for (Artist a : album.getArtists()) {
				sql = "INSERT INTO tr_albums_artists (artistId, albumId) VALUES (?,?);";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, a.getId());
				stmt.setInt(2, albumId);
				stmt.executeUpdate();
			}
			for (AlbumGenre g : album.getGenres()) {
				sql = "INSERT INTO tr_albums_genres (genreId, albumId) VALUES (?,?);";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, g.getId());
				stmt.setInt(2, albumId);
				stmt.executeUpdate();
			}

			conn.commit();
		} catch (SQLException sqlex) {
			conn.rollback();
			throw sqlex;
		}

		finally {
			conn.setAutoCommit(true);
			stmt.close();
		}

	}

	@Override
	public int logIn(String userName, String passWord) throws SQLException {
		String hashedPw = DigestUtils.sha1Hex(passWord);
		String sql = "SELECT userId FROM users WHERE userName=? AND pwHash=?;";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userName);
		stmt.setString(2, hashedPw);
		try {
			ResultSet rs = stmt.executeQuery();
			if (!rs.isBeforeFirst()) return -1;
			rs.next();
			int id = rs.getInt(1);
			if (id < 1) return -1;
			else {
				this.userId = id;
				return id;
			}
		} finally {
			stmt.close();
		}
	}

}
