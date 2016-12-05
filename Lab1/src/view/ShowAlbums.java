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

import src.controller.AlbumController;
import src.controller.AlbumController.AddRating;
import src.model.Album;
import src.model.JvdbInterface;
import src.model.Operations;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.TextField;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.sql.Date;


public class ShowAlbums extends JFrame {

	private JPanel contentPane;
	public JTable tblAlbums;
	public JTextField textField;
	public Operations operations = Operations.NAME;
	public List<Album> allAlbums = new ArrayList<>();


	public void Refresh(List<Album> albums) {
		DefaultTableModel tmodel = new DefaultTableModel();
		tmodel.addColumn("ID");
		tmodel.addColumn("Name");
		tmodel.addColumn("Release date");
		tmodel.addColumn("Artists");
		tmodel.addColumn("Genres");
		tmodel.addColumn("Rating");
		if(albums != null){
			for (Album a : albums)
				tmodel.addRow(a.getArray());
			tblAlbums.setBackground(Color.WHITE);
			tblAlbums.setModel(tmodel);
		}
		
	}
	
	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public ShowAlbums(final JvdbInterface jvdb) {
		setBounds(100, 100, 658, 422);
		
		AlbumController ac = new AlbumController(jvdb);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tblAlbums = new JTable();
		scrollPane.setViewportView(tblAlbums);
		
		JPanel panel = new JPanel();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(0)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
					.addContainerGap())
		);
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
		
		JRadioButton rdbtnName = new JRadioButton("Name");
		rdbtnName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = Operations.NAME;
			}
		});
		rdbtnName.setBounds(6, 147, 68, 23);
		panel.add(rdbtnName);
		rdbtnName.setSelected(true);
		btnGroup.add(rdbtnName);
		
		JRadioButton rdbtnArtist = new JRadioButton("Artist");
		rdbtnArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = Operations.ARTIST;
			}
		});
		rdbtnArtist.setBounds(6, 100, 75, 23);
		panel.add(rdbtnArtist);
		btnGroup.add(rdbtnArtist);
		
		JRadioButton rdbtnReleaseDate = new JRadioButton("Release date");
		rdbtnReleaseDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = Operations.RELEASEDATE;
			}
		});
		rdbtnReleaseDate.setBounds(6, 53, 110, 23);
		panel.add(rdbtnReleaseDate);
		btnGroup.add(rdbtnReleaseDate);
		
		JRadioButton rdbtnGenre = new JRadioButton("Genre");
		rdbtnGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = Operations.GENRE;
			}
		});
		rdbtnGenre.setBounds(6, 124, 68, 23);
		panel.add(rdbtnGenre);
		btnGroup.add(rdbtnGenre);
		
		JRadioButton rdbtnRating = new JRadioButton("Rating");
		rdbtnRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = Operations.RATING;
			}
		});
		rdbtnRating.setBounds(6, 76, 72, 23);
		panel.add(rdbtnRating);
		btnGroup.add(rdbtnRating);
		
		JButton btnRateAlbum = new JButton("Rate Album");
		btnRateAlbum.addActionListener(ac.new ShowAddAlbumReview(this));
		btnRateAlbum.setBounds(50, 220, 117, 29);
		panel.add(btnRateAlbum);
		
		
		contentPane.setLayout(gl_contentPane);
		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField.getText().equals(""))
					{
						allAlbums = jvdb.getAlbums(Operations.ALL, "");
						Refresh(allAlbums);
						return; 
					}
					
					Refresh(jvdb.getAlbums(operations, textField.getText()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		try {
			allAlbums = jvdb.getAlbums(Operations.ALL, "");
			Refresh(allAlbums);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
