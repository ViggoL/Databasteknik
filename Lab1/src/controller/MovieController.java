/**
 * 
 */
package src.controller;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

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
import src.view.AddAlbumReview;
import src.view.AddMovieReview;
import src.view.ShowAlbums;
import src.view.ShowMovies;

/**
 * @author jlipecki
 *
 */
public class MovieController {

	private JvdbInterface jvdb;
	private JButton b;

	public MovieController(final JvdbInterface jvdb) {
		this.jvdb = jvdb;
	}

	public class HideWindow implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JButton) {
				b = (JButton) e.getSource();
				b.getRootPane().getParent().setVisible(false);
			}
		}
	}

	
	
	public class AddDirector implements ActionListener {

		private src.view.AddDirector view;
		
		public AddDirector(src.view.AddDirector view)
		{
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(){
				public void run()
				{
					Director director = new Director();
					director.setName(view.txtName.getText());
					director.setBio(view.txtBio.getText());
					try {
						jvdb.addDirector(director);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					view.hide();
				}
				
			}.start();
		}
		
		
	}
	
	public class ShowAddMovieReview implements ActionListener {

		private ShowMovies view;
		
		public ShowAddMovieReview(ShowMovies view)
		{
			this.view = view; 
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Movie movie = null;
			Object[] arr = new Object[6];
			int row = view.tblMovies.getSelectedRow();
			int column = view.tblMovies.getColumnCount();
			arr[0] = view.tblMovies.getValueAt(row, 0);
			arr[1] = view.tblMovies.getValueAt(row, 1);
			arr[2] = view.tblMovies.getValueAt(row, 2);
			arr[3] = view.tblMovies.getValueAt(row, 3);
			arr[4] = view.tblMovies.getValueAt(row, 4);
			arr[5] = view.tblMovies.getValueAt(row, 5);
			for (Movie m : view.allMovies)
			{
				if (m.compareArrays(arr)){
					movie = m;
					break;
				}
					
			}
			try {
				if (jvdb.movieReviewExists(jvdb.getUserId(), movie.getId()))
				{
					JOptionPane.showMessageDialog(null, "You have already reviewed this movie.");
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			AddMovieReview amr = new AddMovieReview(jvdb, movie);
			amr.show();
		}
		
	}
	
	public class SearchMovie implements ActionListener {

		private ShowMovies view;
		
		public SearchMovie(ShowMovies view)
		{
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(){
				public void run()
				{
					try {
						final List<Movie> movies;
						if (view.textField.getText().equals("")){
							movies = jvdb.getMovies(MovieAttributes.ALL, "");
						}
						else {
							movies = jvdb.getMovies(view.operations, view.textField.getText());
						}
						SwingUtilities.invokeLater(new Runnable(){
							public void run() {
								view.Refresh(movies);
							}
						});
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		}
		
	}
	
	public class AddRating implements ActionListener {

		private AddMovieReview view;

		public AddRating(AddMovieReview view)
		{
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(){
				public void run()
				{
					MovieReview review = new MovieReview();
					review.setText(view.txtComment.getText());
					review.setRating(view.slider.getValue());
					review.setMovieId(view.movie.getId());
					try {
						jvdb.addMovieReview(review);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					view.setVisible(false);
				}
			}.start();

		}

	}
	
	public class AddMovie implements ActionListener {
		
		private src.view.AddMovie view;

		public AddMovie(src.view.AddMovie view) {
			this.view = view;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> movie = new ArrayList<>();
			ArrayList<MovieGenre> gList = null;

			if (e.getSource() instanceof JButton) {
				final Movie m = new Movie();
				b = (JButton) e.getSource();
				Component[] c = b.getParent().getComponents();
				for (Component s : c) {
					java.sql.Date sqlDate;
					if (s instanceof JTextField) {
						String string = new String(((JTextComponent) s).getText());
						if (!string.matches(".*\\d+.*") && string.length() > 0){
							movie.add(string);
						}
						else if (s instanceof JFormattedTextField) {
								sqlDate = new java.sql.Date(
										((java.util.Date) ((JFormattedTextField) s).getValue()).getTime());
								m.setReleaseDate(sqlDate);
						}
					}else if (s instanceof JScrollPane) {
						Component[] comps = ((JScrollPane) s).getComponents();
						for (Component c1 : comps) {
							if (c1 instanceof JViewport) {
								JViewport port = (JViewport) c1;
								for (Component c2 : port.getComponents()) {
									if (c2 != null) {
										if (c2 instanceof JList) {
											JList<Object> tmpList = (JList<Object>) c2;
											if (tmpList.getSelectedValuesList().size() > 0) {
												if (tmpList.getSelectedValuesList().get(0) instanceof MovieGenre) {
													gList = (ArrayList<MovieGenre>) ((JList<MovieGenre>) c2)
															.getSelectedValuesList(); 
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
				m.setGenres(gList);
				m.setTitle(movie.remove(0));
				List<Director> directors = new ArrayList<>();
				for (String s : movie) {
					Director d = new Director(s);
					directors.add(d);
				}
				m.setDirectors(directors);
				System.out.println(m.getTitle() + m.getReleaseDate().toString() + m.getDirectors());
				new Thread(){
					@Override
					public void run() {
						try {
							jvdb.AddMovie(m);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}.start();

			}
		}
	};
}
