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

import src.controller.MediaController.AddMedia;
import src.model.Artist;
import src.model.Genre;
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

		public AddMediaPerson(src.view.AddMediaPerson view) {
			this.view = view;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread() {
				public void run() {
					MediaPerson mediaPerson = new Artist(view.txtName.getText(), view.txtBio.getText());
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
								try {
									view.Refresh(albums);
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
			Media album = null;
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
					album = a;
					break;
				}

			}
			try {
				if (jvdb.mediaReviewExists(jvdb.getUserId(), album.getId())) {
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
		public void actionPerformed(ActionEvent e) {
			new SubmitMediaFormEvent(e, aa);

		}
	}

	public class SubmitMediaFormEvent {

		private ActionEvent e;
		private src.view.AddMedia view;

		public SubmitMediaFormEvent(ActionEvent e,src.view.AddMedia view) {
			this.e = e;
			this.view = view;
			java.util.Date utilDate = (java.util.Date) view.releaseDate_FormattedTextField.getValue();
			PersonType profession = Enum.valueOf(PersonType.class,((String) view.profComboBox.getSelectedItem()).toUpperCase());
			
			Media media = 
					new Media(view.titleTextField.getText(),
					new java.sql.Date(utilDate.getTime()),
					new MediaPerson(profession, 
							view.nameTextField.getText(),
							""),
					(List<Genre>) view.genreList.getSelectedValuesList());

				System.out.println(media.toString());

				new Thread() {
					@Override
					public void run() {
						try {
							jvdb.addMedia(media);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}.start();

//			}
		}
	}
	

}
