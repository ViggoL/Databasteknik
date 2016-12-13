package src.controller;

import java.awt.Component;
import java.awt.Event;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import javax.swing.SwingUtilities;

import com.mongodb.MongoException;

import src.controller.MediaController.AddMedia;
import src.model.Genre;
import src.model.JvdbInterface;
import src.model.Media;
import src.model.MediaPerson;
import src.model.MediaReview;
import src.model.MediaType;
import src.model.MediaPersonType;
import src.view.*;

public class MediaController {
	private JvdbInterface jvdb;
	
	public MediaController(JvdbInterface jvdb) {
		this.jvdb = jvdb;
	}

	public class AddMediaPerson implements ActionListener {

		private src.view.AddMediaPerson view;

		public AddMediaPerson(src.view.AddMediaPerson view) {
			this.view = view;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread() {
				public void run() {
					MediaPerson mediaPerson = 
							new MediaPerson(
									null,
									view.txtName.getText(), 
									MediaPersonType.valueOf(view.profComboBox.getSelectedItem().toString().toUpperCase()), 
									view.txtBio.getText());
					try {
						if (!jvdb.addMediaPerson(mediaPerson)) SwingUtilities.invokeLater(new ErrorDialogue("Person not added"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
					finally{
						view.setVisible(false);
					}
				}

			}.start();
		}
	}

	public class AddRating implements ActionListener {

		private AddMediaReview view;

		public AddRating(AddMediaReview view) {
			this.view = view;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread() {
				public void run() {
					MediaReview review = new MediaReview(view.txtComment.getText(), view.slider.getValue(),
							jvdb.getUser(), ((Media) view.media).getId());

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
						final List<Media> media = jvdb.getMedia(view.operations, view.textField.getText());
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								try {
									view.Refresh(media);
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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

	public class ShowAddMediaReview implements ActionListener {

		private src.view.ShowMedia view;

		public ShowAddMediaReview(src.view.ShowMedia showMedia) {
			this.view = showMedia;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Media media = null;
			Object[] arr = new Object[6];
			int row = view.tblMedia.getSelectedRow();
			int column = view.tblMedia.getColumnCount();
			arr[0] = view.tblMedia.getValueAt(row, 0);
			arr[1] = view.tblMedia.getValueAt(row, 1);
			arr[2] = view.tblMedia.getValueAt(row, 2);
			arr[3] = view.tblMedia.getValueAt(row, 3);
			arr[4] = view.tblMedia.getValueAt(row, 4);
			arr[5] = view.tblMedia.getValueAt(row, 5);
			for (Media a : view.allMedia) {
				if (a.compareArrays(arr)) {
					media = a;
					break;
				}

			}
			try {
				if (jvdb.mediaReviewExists(jvdb.getUserId(), media.getId())) {
					SwingUtilities.invokeLater(new src.view.ErrorDialogue("You have already reviewed this album."));					
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			AddMediaReview aar = new AddMediaReview();
			aar.setVisible(true);
		}

	}

	public class AddMedia implements ActionListener {
		src.view.AddMedia aa;
		public AddMedia(src.view.AddMedia aa) {
			super();
			this.aa = aa;
		}

		@Override
		public void actionPerformed(ActionEvent e) throws IllegalArgumentException{
			try{
				new SubmitMediaFormEvent(e, aa);
			}
			catch(IllegalArgumentException illa){
				illa.printStackTrace();
				throw illa;
			}

		}
	}

	public class SubmitMediaFormEvent {

		private ActionEvent e;
		private src.view.AddMedia view;

		public SubmitMediaFormEvent(ActionEvent e,src.view.AddMedia view) throws IllegalArgumentException{
			this.e = e;
			this.view = view;
			java.util.Date utilDate = (java.util.Date) view.releaseDate_FormattedTextField.getValue();
			if(utilDate == null) {
				SwingUtilities.invokeLater(new src.view.ErrorDialogue("No date value entered!"));
				return;
			}
			
			MediaPersonType profession = Enum.valueOf(MediaPersonType.class,((String) view.profComboBox.getSelectedItem()).toUpperCase());
			List<Genre> list = new ArrayList<>();
			for(Object s: view.genreList.getSelectedValuesList()){
				if(s instanceof String && ((String) s).length() > 0) list.add(new Genre( (String) s));
			}
			final Media media = new Media(
					view.titleTextField.getText(),
					new java.sql.Date(utilDate.getTime()),
					new MediaPerson(null, view.nameTextField.getText(), profession, ""),
					list
					);
			
			
			if(this.view.rdbtnAlbum.isSelected()) media.setType(MediaType.ALBUM);
			else if(this.view.rdbtnMovie.isSelected()) media.setType(MediaType.MOVIE);
			
			System.out.println(media.toString());
			
			new Thread() {
				@Override
				public void run() {
					try {
						jvdb.addMedia(media);
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (final MongoException e2){
						e2.printStackTrace();;
						SwingUtilities.invokeLater(new ErrorDialogue(e2.getLocalizedMessage()));
					}
				}
			}.start();
		}
	}
	

}
