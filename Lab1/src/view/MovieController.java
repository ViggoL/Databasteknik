/**
 * 
 */
package src.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
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

	public MovieController(final JvdbInterface jvdb) {
		this.jvdb = jvdb;
	}

	public class CloseWindow implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			final Action exit = new AbstractAction("exit") {

				@Override
				public void actionPerformed(ActionEvent e) {
					JFrame f = new JFrame();
					if (e.getSource() instanceof JFrame) {
						f = (JFrame) e.getSource();
						f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
					}
				}
			};
		}
	}

	public class AddMovie implements ActionListener {
		JButton b;

		@Override
		public void actionPerformed(ActionEvent e) {
			//Runnable addTheMovie = new Runnable() {
			//	public void run() {
					List<String> movie = new ArrayList<>();

					if (e.getSource() instanceof JButton) {
						b = (JButton) e.getSource();
						Component[] c = b.getParent().getComponents();
						for (Component s : c) {
							if (s instanceof JTextField) {
								String string = new String(((JTextComponent) s).getText());
								System.out.println(string);
								movie.add(string);
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
						
						SwingUtilities.invokeLater(new Runnable(){
							
							@Override
							public void run(){
								try {
									jvdb.AddMovie(m);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});

					}
				}
			};
		//	SwingUtilities.invokeLater(addTheMovie);

		//}

	//}
}
