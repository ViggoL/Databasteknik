package src.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import src.controller.MediaController;
import src.controller.*;
import src.model.JvdbInterface;
import src.model.Media;
import src.model.MediaAttributes;
import src.model.MediaType;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.TextField;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowMedia extends JFrame {

	private JPanel contentPane;
	public JTable tblMedia;
	public JTextField textField;
	public MediaAttributes operations = MediaAttributes.TITLE;
	public List<Media> allMedia = new ArrayList<>();

	public void Refresh(List<Media> media) throws ClassNotFoundException {
		DefaultTableModel tmodel = new DefaultTableModel();
		tmodel.addColumn("Title");
		tmodel.addColumn("Release date");

		if (media != null) {
			if (media.get(0).getType() == MediaType.ALBUM)
				tmodel.addColumn("Artists");
			else if (media.get(0).getType() == MediaType.MOVIE)
				tmodel.addColumn("Directors");
			else
				throw new ClassNotFoundException();
		}

		tmodel.addColumn("Genres");
		tmodel.addColumn("Rating");
		tmodel.addColumn("Added by");
		if (media != null) {
			for (Media a : media)
				tmodel.addRow(a.toArray());
			tblMedia.setBackground(Color.WHITE);
			tblMedia.setModel(tmodel);
		}

	}

	/**
	 * Create the frame.
	 * 
	 * @param jvdb
	 */
	public ShowMedia(final JvdbInterface jvdb) throws ClassNotFoundException {
		setBounds(100, 100, 658, 422);

		MediaController mc = new MediaController(jvdb);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		tblMedia = new JTable();
		scrollPane.setViewportView(tblMedia);

		JPanel panel = new JPanel();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(0)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
						.addContainerGap()));
		panel.setLayout(null);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setBounds(6, 6, 41, 16);
		panel.add(lblSearch);

		textField = new JTextField();
		textField.setBounds(6, 25, 133, 28);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnOK = new JButton("OK");

		btnOK.setBounds(133, 26, 75, 29);
		panel.add(btnOK);
		ButtonGroup btnGroup = new ButtonGroup();

		JRadioButton rdbtnName = new JRadioButton("Title");
		rdbtnName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = MediaAttributes.TITLE;
			}
		});
		rdbtnName.setBounds(6, 147, 68, 23);
		panel.add(rdbtnName);
		rdbtnName.setSelected(true);
		btnGroup.add(rdbtnName);

		JRadioButton rdbtnArtist = new JRadioButton("Person");
		rdbtnArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = MediaAttributes.MEDIA_PERSON;
			}
		});
		rdbtnArtist.setBounds(6, 100, 75, 23);
		panel.add(rdbtnArtist);
		btnGroup.add(rdbtnArtist);

		JRadioButton rdbtnReleaseDate = new JRadioButton("Release date");
		rdbtnReleaseDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = MediaAttributes.RELEASE_DATE;
			}
		});
		rdbtnReleaseDate.setBounds(6, 53, 110, 23);
		panel.add(rdbtnReleaseDate);
		btnGroup.add(rdbtnReleaseDate);

		JRadioButton rdbtnGenre = new JRadioButton("Genre");
		rdbtnGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = MediaAttributes.GENRE;
			}
		});
		rdbtnGenre.setBounds(6, 124, 68, 23);
		panel.add(rdbtnGenre);
		btnGroup.add(rdbtnGenre);

		JRadioButton rdbtnRating = new JRadioButton("Rating");
		rdbtnRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = MediaAttributes.RATING;
			}
		});
		rdbtnRating.setBounds(6, 76, 72, 23);
		panel.add(rdbtnRating);
		btnGroup.add(rdbtnRating);

		JButton btnRateAlbum = new JButton("Rate Media");
		btnRateAlbum.addActionListener(mc.new ShowAddMediaReview(this));
		btnRateAlbum.setBounds(50, 220, 117, 29);
		panel.add(btnRateAlbum);
		// tblAlbums.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent arg0) {
		// btnRateAlbum.setEnabled(true);
		// }
		// });
		contentPane.setLayout(gl_contentPane);

		
		btnOK.addActionListener(mc.new SearchMedia(this));

//		try {
//			allMedia = jvdb.getMedia(MediaAttributes.ALL, "");
//			Refresh(allMedia);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

	}
}
