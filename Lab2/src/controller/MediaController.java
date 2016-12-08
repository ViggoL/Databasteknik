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
import src.model.Genre;
import src.model.AlbumGenre;
import src.model.AlbumReview;
import src.model.JvdbInterface;
import src.model.Media;
import src.model.MediaPerson;
import src.model.MediaReview;
import src.model.MediaType;
import src.model.PersonType;
import src.view.*;

public class MediaController {

	
	
	public class AddMediaPerson implements ActionListener {

		private src.view.AddMediaPerson view;
		
		public AddMediaPerson(src.view.AddMediaPerson view)
		{
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(){
				public void run()
				{
					MediaPerson mediaPerson = new Artist(view.txtName.getText(),view.txtBio.getText());
					try {
						jvdb.addMediaPerson(mediaPerson);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					view.setVisible(false);
				}
				
			}.start();
		}
		
		
	}
	
	public class AddRating implements ActionListener {

		private AddMediaReview view;

		public AddRating(AddMediaReview view)
		{
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(){
				public void run()
				{
					MediaReview review = new MediaReview(view.txtComment.getText(),view.slider.getValue(),view.getUser(),((Media) view.media).getId());

					try {
						jvdb.addMediaReview(review);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					view.setVisible(false);
				}
			}.start();

		}

	}

	private JvdbInterface jvdb;
	private JButton b;

	public MediaController(JvdbInterface jvdb) {
		this.jvdb = jvdb;
	}

	public class ShowMedia implements ActionListener {

		private src.view.ShowMedia view;

		public ShowMedia(MediaType media, src.view.ShowMedia view) {
			this.view = view;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread() {
				public void run() {
					try {
						final List<Media> albums = jvdb.getMedia(view.operations, view.textField.getText());
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

	public class ShowAddMediaReview implements ActionListener{

		private ShowMedia view;
		
		
		public ShowAddMediaReview(ShowMedia showMedia)
		{
			this.view = showMedia; 
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Media album = null;
			Object[] arr = new Object[6];
			int row = view.tblMedia.getSelectedRow();
			int column = view.tblAlbums.getColumnCount();
			arr[0] = view.tblAlbums.getValueAt(row, 0);
			arr[1] = view.tblAlbums.getValueAt(row, 1);
			arr[2] = view.tblAlbums.getValueAt(row, 2);
			arr[3] = view.tblAlbums.getValueAt(row, 3);
			arr[4] = view.tblAlbums.getValueAt(row, 4);
			arr[5] = view.tblAlbums.getValueAt(row, 5);
			for (Media a : view.allAlbums)
			{
				if (a.compareArrays(arr)){
					album = a;
					break;
				}
					
			}
			try {
				if (jvdb.mediaReviewExists(jvdb.getUserId(), album.getId()))
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
			
			AddMediaReview aar = new AddAlbumReview(jvdb, album);
			aar.show();
		}
		
	}
	
	public class AddMedia implements ActionListener {

		src.view.AddMedia aa;

		public AddMedia(src.view.AddMedia aa) {
			this.aa = aa;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			final Media album = new Album();
			List<MediaPerson> artists = new ArrayList<>();
			List<Genre> genres = new ArrayList<>();
			List<String> albumValues = new ArrayList<>();
			ArrayList<MediaPerson> aList = null;
			ArrayList<Genre> gList = null;

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
													aList = (ArrayList<MediaPerson>) ((JList<MediaPerson>) c2)
															.getSelectedValuesList();
												} else if (tmpList.getSelectedValuesList()
														.get(0) instanceof AlbumGenre) {
													gList = (ArrayList<Genre>) ((JList<Genre>) c2)
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

			album.setTitle(albumValues.remove(0));
			album.setReleaseDate(Date.valueOf(albumValues.remove(0)));
			for (MediaPerson a : aList)
				album.getMediaPersons().add(a);
			for (Genre g : gList)
				album.getGenres().add(g);

			System.out.println(album.toString());
			
			new Thread() {
				@Override
				public void run() {
					try {
						jvdb.addMedia(MediaType.ALBUM,album);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}.start();

		}

	}

}
