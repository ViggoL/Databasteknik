package src.controller;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import src.model.Album;
import src.model.Artist;
import src.model.AlbumGenre;
import src.model.AlbumReview;
import src.model.JvdbInterface;
import src.view.AddAlbumReview;
import src.view.ShowAlbums;

public class AlbumController {

	
	
	public class AddArtist implements ActionListener {

		private src.view.AddArtist view;
		
		public AddArtist(src.view.AddArtist view)
		{
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(){
				public void run()
				{
					Artist artist = new Artist();
					artist.setName(view.txtName.getText());
					artist.setBio(view.txtBio.getText());
					try {
						jvdb.addArtist(artist);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					view.hide();
				}
				
			}.start();
		}
		
		
	}
	
	public class AddRating implements ActionListener {

		private AddAlbumReview view;

		public AddRating(AddAlbumReview view)
		{
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(){
				public void run()
				{
					AlbumReview review = new AlbumReview();
					review.setText(view.txtComment.getText());
					review.setRating(view.slider.getValue());
					review.setAlbumId(view.album.getId());
					try {
						jvdb.addAlbumReview(review);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					view.hide();
				}
			}.start();

		}

	}

	private JvdbInterface jvdb;
	private JButton b;

	public AlbumController(JvdbInterface jvdb) {
		this.jvdb = jvdb;
	}

	public class ShowAlbum implements ActionListener {

		private src.view.ShowAlbums view;

		public ShowAlbum(src.view.ShowAlbums view) {
			this.view = view;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread() {
				public void run() {
					try {
						final List<Album> albums = jvdb.getAlbums(view.operations, view.textField.getText());
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								view.Refresh(albums);
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

	public class ShowAddAlbumReview implements ActionListener{

		private ShowAlbums view;
		
		
		public ShowAddAlbumReview(ShowAlbums view)
		{
			this.view = view; 
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Album album = null;
			Object[] arr = new Object[6];
			int row = view.tblAlbums.getSelectedRow();
			int column = view.tblAlbums.getColumnCount();
			arr[0] = view.tblAlbums.getValueAt(row, 0);
			arr[1] = view.tblAlbums.getValueAt(row, 1);
			arr[2] = view.tblAlbums.getValueAt(row, 2);
			arr[3] = view.tblAlbums.getValueAt(row, 3);
			arr[4] = view.tblAlbums.getValueAt(row, 4);
			arr[5] = view.tblAlbums.getValueAt(row, 5);
			for (Album a : view.allAlbums)
			{
				if (a.compareArrays(arr)){
					album = a;
					break;
				}
					
			}
			try {
				if (jvdb.albumReviewExists(jvdb.getUserId(), album.getId()))
				{
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(null, "You have already reviewed this album.");
						}
						
					});
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			AddAlbumReview aar = new AddAlbumReview(jvdb, album);
			aar.show();
		}
		
	}
	
	public class AddAlbum implements ActionListener {

		src.view.AddAlbum aa;

		public AddAlbum(src.view.AddAlbum aa) {
			this.aa = aa;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			final Album album = new Album();
			List<Artist> artists = new ArrayList<>();
			List<AlbumGenre> genres = new ArrayList<>();
			List<String> albumValues = new ArrayList<>();
			ArrayList<Artist> aList = null;
			ArrayList<AlbumGenre> gList = null;

			if (e.getSource() instanceof JButton) {
				b = (JButton) e.getSource();

				Component[] ca = b.getParent().getComponents();
				int i = 0;
				for (Component c : ca) {
					System.out.println("Komponent " + ++i + c.toString() + " ");
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
									if (c2 != null) {
										if (c2 instanceof JList) {
											JList<Object> tmpList = (JList<Object>) c2;
											if (tmpList.getSelectedValuesList().size() > 0) {
												if (tmpList.getSelectedValuesList().get(0) instanceof Artist) {
													aList = (ArrayList<Artist>) ((JList<Artist>) c2)
															.getSelectedValuesList();
												} else if (tmpList.getSelectedValuesList()
														.get(0) instanceof AlbumGenre) {
													gList = (ArrayList<AlbumGenre>) ((JList<AlbumGenre>) c2)
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
			}

			album.setName(albumValues.remove(0));
			album.setReleaseDate(Date.valueOf(albumValues.remove(0)));
			for (Artist a : aList)
				album.getArtists().add(a);
			for (AlbumGenre g : gList)
				album.getGenres().add(g);

			System.out.println(album.toString());
			
			new Thread() {
				@Override
				public void run() {
					try {
						jvdb.addAlbum(album);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}.start();

		}

	}

}
