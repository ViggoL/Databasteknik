/**
 * 
 */
package src.controller;

import java.awt.Component;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import src.model.Director;
import src.model.JvdbInterface;
import src.model.Movie;

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

	public class AddMovie implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> movie = new ArrayList<>();

			if (e.getSource() instanceof JButton) {
				b = (JButton) e.getSource();
				Component[] c = b.getParent().getComponents();
				for (Component s : c) {
					java.sql.Date sqlDate;
					if (s instanceof JTextField) {
						String string = new String(((JTextComponent) s).getText());
						System.out.println(string);
						movie.add(string);
					}
					if(s instanceof JFormattedTextField){
						sqlDate = new java.sql.Date(((java.util.Date) ((JFormattedTextField) s).getValue()).getTime());
						System.out.println("Date: " + sqlDate.toString());
					}
				}
				Movie m = new Movie();
				m.setTitle(movie.remove(0));
				m.setReleaseDate(Date.valueOf(movie.remove(0)));
				List<Director> directors = new ArrayList<>();
				for (String s : movie) {
					Director d = new Director(s);
					directors.add(d);
				}
				m.setDirectors(directors);

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						try {
							jvdb.AddMovie(m);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});

			}
		}
	};
}
