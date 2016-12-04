package src.controller;

import java.awt.Component;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import src.model.Album;
import src.model.Artist;
import src.model.Genre;
import src.model.JvdbInterface;

public class AlbumController {

	private JvdbInterface jvdb;
	private JButton b;

	public AlbumController(JvdbInterface jvdb) {
		this.jvdb = jvdb;
	}

	public class AddAlbum implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Album album = new Album();
			List<Artist> artists = new ArrayList<>();
			List<Genre> genres = new ArrayList<>();
			List<String> albumValues = new ArrayList<>();
			ArrayList<Artist> aList = null;
			ArrayList<Genre> gList = null;

			if (e.getSource() instanceof JButton) {
				b = (JButton) e.getSource();

				Component[] ca = b.getParent().getComponents();
				int i = 0;
				for (Component c : ca) {
					System.out.println("Komponent " + ++i + c.toString() +	 " ");
					java.sql.Date sqlDate;
					if (c instanceof TextField) {
						
						String string = ((TextField) c).getText();
						albumValues.add(string);
						System.out.println(string);

					} else if (c instanceof JScrollPane) {
						Component[] comps = ((JScrollPane) c).getComponents();
						for (Component c1 : comps) {
							if (c1 instanceof JViewport) {
								JViewport port = (JViewport) c1;
								for (Component c2 : port.getComponents()) {
									if (c2 instanceof JList) {
										JList<Object> tmpList = (JList<Object>) c2;
										if (tmpList.getSelectedValuesList().get(0) instanceof Artist) {
											aList = (ArrayList<Artist>) ((JList<Artist>) c2).getSelectedValuesList();
										} else if (tmpList.getSelectedValuesList().get(0) instanceof Genre) {
											gList = (ArrayList<Genre>) ((JList<Genre>) c2).getSelectedValuesList();
										}
									}
								}
							}
						}
					}
				}
			}
			
			 
			album.setName(albumValues.remove(0));
			album.setReleaseDate(Date.valueOf(albumValues.remove(0))); 
			for(Artist a : aList)
				album.getArtists().add(a); 
			for (Genre g :gList) 
				album.getGenres().add(g);
			
			System.out.println(album.toString());
			
			SwingUtilities.invokeLater(new Runnable() {

				@Override public void run() { 
					try { 
						jvdb.addAlbum(album);
						} 
					catch (SQLException e1) { 
							e1.printStackTrace(); 
						} 		
				}
			});
			 
		}

	}

}
