package src.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class JVDB implements JvdbInterface {
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private String db = "jvdb", usr = "client", pwd = "lab", port = "3306", host = "viggolunden.com";

	public JVDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + db + "?useSSL=false",
				usr, pwd);
		stmt = conn.prepareStatement("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see src.model.JvdbInterface#close()
	 */
	@Override
	public void close() throws SQLException {
		conn.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see src.model.JvdbInterface#isOpen()
	 */
	@Override
	public boolean isOpen() throws SQLException {
		return !conn.isClosed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see src.model.JvdbInterface#getUsers()
	 */
	@Override
	public List<User> getUsers() throws SQLException {
		String sql = "SELECT * FROM users";
		ResultSet resultSet = stmt.executeQuery(sql);
		List<User> users = new ArrayList<>();
		while (resultSet.next()) {
			User user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
					resultSet.getString(4));
			users.add(user);
		}
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see src.model.JvdbInterface#addUser(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean addUser(String userName, String password, String email) throws SQLException {
		String sql = "INSERT INTO users (userName, pwHash, email) VALUES (?, ?, ?);";

		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userName);
		stmt.setString(2, DigestUtils.sha1Hex(password));
		stmt.setString(3, email);
		return stmt.executeUpdate() != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see src.model.JvdbInterface#getMovies()
	 * 
	 * @return list of movies with movie
	 */
	@Override
	public List<Movie> getMovies(MovieAttributes attribute, String value) throws SQLException {
		// Create query
		String sql = new String();
		switch (attribute) {
		case TITLE:
			sql = "SELECT * FROM movies WHERE movieTitle LIKE '%?%';";
			break;
		case DIRECTOR:
			sql = "SELECT movies.* " + "FROM movies,directors,tr_movies_directors " + "WHERE directors.directorName "
					+ "LIKE '%?%' " + "AND directors.directorId=tr_movies_directors.directorId "
					+ "AND movies.movieId=tr_movies_directors.movieId;";
			break;
		case RATING:
			sql = "SELECT movies.* " + "FROM movies,movie_reviews " + "WHERE rating = '?' "
					+ "AND movies.movieId=movie_reviews.movieId;";
			break;
		case RELEASE_DATE:
			sql = "SELECT * FROM movies WHERE movieReleaseDate LIKE '%?%';";
			break;
		default:
			break;
		}
		// Get movies
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, value);
		ResultSet resultSet = stmt.executeQuery(sql);
		List<Movie> movies = new ArrayList<>();
		while (resultSet.next()) {
			Movie movie = new Movie();
			movie.setId(resultSet.getInt(1));
			movie.setTitle(resultSet.getString(2));
			movie.setReleaseDate(resultSet.getDate(3));
			movies.add(movie);
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
			boolean directorExists=false;
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
					
					// När tillfälle ges kan detta bli ett fönster som visar användaren tillägget
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
			sql = "INSERT INTO movies (movieName,movieReleaseDate) VALUES (?, ?);";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, movie.getTitle());
			stmt.setDate(2, movie.getReleaseDate());
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

			conn.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (save1 != null)
				conn.rollback(save1);
		} finally {
			conn.setAutoCommit(true);
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
		ResultSet rs = stmt.executeQuery(sql);
		List<Artist> artists = new ArrayList<>();
		while (rs.next()) {
			Artist a = new Artist(rs.getInt(1), rs.getString(2), rs.getString(3));
			artists.add(a);
		}
		return artists;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see src.model.JvdbInterface#getGenres()
	 */
	@Override
	public List<Genre> getGenres() throws SQLException {
		String sql = "SELECT * FROM genres";
		ResultSet rs = stmt.executeQuery(sql);
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre g = new Genre(rs.getInt(1), rs.getString(2));
			genres.add(g);
		}
		return genres;
	}

	@Override
	public List<Movie> getMoviesByName(String name) throws SQLException {
		String sql = "SELECT * FROM movies WHERE movieTitle=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		List<Movie> movies = new ArrayList<>();
		while (rs.next()) {
			Movie movie = new Movie();
			movie.setId(rs.getInt(1));
			movie.setTitle(rs.getString(2));
			movie.setReleaseDate(rs.getDate(3));
		}
		for (Movie m : movies) {
			sql = "SELECT directors.* FROM directors, tr_movies_directors WHERE directors.directorId=tr_movies_directors.directorId AND tr_movies_directors.movieId=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, m.getId());
			rs = stmt.executeQuery();
			List<Director> directors = new ArrayList<>();
			while (rs.next()) {
				Director d = new Director(rs.getInt(1), rs.getString(2), rs.getString(3));
				directors.add(d);
			}
			m.setDirectors(directors);
		}
		return movies;
	}

	public List<Album> getAlbumsByDate(Date date) throws SQLException {
		// Get all Albums
		String sql = "SELECT * FROM albums WHERE albumReleaseDate=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, date.toString());
		ResultSet resultSet = stmt.executeQuery();
		List<Album> albums = new ArrayList<>();

		while (resultSet.next()) {
			Album album = new Album();
			album.setId(resultSet.getInt(1));
			album.setName(resultSet.getString(2));
			album.setReleaseDate(resultSet.getDate(3));
			albums.add(album);

		}
		for (Album alb : albums) {
			sql = "SELECT artists.* FROM artists, tr_albums_artists WHERE artists.artistId=tr_albums_artists.artistId AND tr_albums_artists.albumId=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, alb.getId());
			resultSet = stmt.executeQuery();
			List<Artist> artists = new ArrayList<>();
			while (resultSet.next()) {
				Artist a = new Artist(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
				artists.add(a);
			}
			alb.setArtists(artists);
			sql = "SELECT genres.* FROM genres, tr_albums_genres WHERE genres.genreId=tr_albums_genres.genreId AND tr_albums_genres.albumId=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, alb.getId());
			resultSet = stmt.executeQuery();
			List<Genre> genres = new ArrayList<>();
			while (resultSet.next()) {
				Genre g = new Genre(resultSet.getInt(1), resultSet.getString(2));
				genres.add(g);
			}
			alb.setGenres(genres);
		}

		return albums;
	}

	@Override
	public List<Album> getAlbums(Operations operation, String value) throws SQLException {
		String sql = "SELECT * FROM albums";
		ResultSet rs;
		List<Album> albums = new ArrayList<>();
		switch (operation) {
		case ALL:
			sql = "SELECT * FROM albums";
			rs = stmt.executeQuery(sql);
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
			String sqlId = "SELECT albumId FROM tr_albums_artists WHERE artistId = (SELECT artistId FROM artists WHERE artistName=? LIMIT 1)";
			stmt = conn.prepareStatement(sqlId);
			stmt.setString(1, value);
			rs = stmt.executeQuery();
			rs.next();
			int albumId = rs.getInt(1);
			sql = "SELECT * FROM albums WHERE albumId=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, albumId);
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
		case GENRE:
			String sqlId2 = "SELECT albumId FROM tr_albums_genres WHERE genreId = (SELECT genreId FROM genres WHERE genreName=? LIMIT 1);";
			stmt = conn.prepareStatement(sqlId2);
			stmt.setString(1, value);
			rs = stmt.executeQuery();
			rs.next();
			int albumId2 = rs.getInt(1);
			sql = "SELECT * FROM albums WHERE albumId=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, albumId2);
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
		case NAME:
			sql = "SELECT * FROM albums WHERE albumName=?;";
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
			sql = "SELECT genres.* FROM genres, tr_albums_genres WHERE genres.genreId=tr_albums_genres.genreId AND tr_albums_genres.albumId=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, album.getId());
			rs = stmt.executeQuery();
			List<Genre> genres = new ArrayList<>();
			while (rs.next()) {
				Genre g = new Genre(rs.getInt(1), rs.getString(2));
				genres.add(g);
			}
			album.setGenres(genres);
		}
		return albums;
	}

	@Override
	public void addAlbum(Album album) throws SQLException, NullPointerException {
		try {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO albums (albumName, albumReleaseDate, albumRating) VALUES (?,?,?);";
			System.out.println(sql + "\n" + album.toString());
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, album.getName());
			stmt.setDate(2, album.getReleaseDate());
			stmt.setInt(3, album.getRating());
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
			for (Genre g : album.getGenres()) {
				sql = "INSERT INTO tr_albums_genres (genreId, albumId) VALUES (?,?);";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, g.getId());
				stmt.setInt(2, albumId);
				stmt.executeUpdate();
			}

			conn.commit();
		} catch (SQLException sqlex) {
			conn.rollback();
		}

		finally {
			conn.setAutoCommit(true);
		}

	}

}
