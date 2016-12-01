package src.view;
import src.model.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Label;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.TextField;
import java.awt.Window;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Choice;
import java.awt.Component;

import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddAlbum extends JFrame {

	private JPanel contentPane;
	private TextField txtName, txtReleaseDate;
	private JScrollPane scrollPane, scrollPane_1;
	private Label label, label_1, label_2, label_3;

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public AddAlbum(JvdbInterface jvdb) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 346, 206);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new Label("Name");
		label.setBounds(10, 10, 41, 22);
		contentPane.add(label);
		
		txtName = new TextField();
		txtName.setBounds(10, 38, 98, 22);
		contentPane.add(txtName);
		
		label_1 = new Label("Release date");
		label_1.setBounds(10, 66, 98, 22);
		contentPane.add(label_1);
		
		txtReleaseDate = new TextField();
		txtReleaseDate.setBounds(10, 94, 98, 22);
		contentPane.add(txtReleaseDate);
		DefaultListModel<Artist> alm = new DefaultListModel<Artist>();
		DefaultListModel<Genre> glm = new DefaultListModel<Genre>();
		
		try {
			List<Artist> artists = jvdb.getArtists();
			for (Artist a : artists) 
				alm.addElement(a);
			List<Genre> genres = jvdb.getGenres();
			for (Genre g : genres)
				glm.addElement(g);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		label_2 = new Label("Artists");
		label_2.setBounds(114, 14, 98, 22);
		contentPane.add(label_2);
		

		
		label_3 = new Label("Genres");
		label_3.setBounds(221, 14, 98, 22);
		contentPane.add(label_3);
		
		JButton btnOK = new JButton("OK");
		
		btnOK.setBounds(226, 131, 89, 23);
		contentPane.add(btnOK);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(114, 38, 98, 78);
		contentPane.add(scrollPane);
		JList<Artist> lstArtists = new JList();
		scrollPane.setViewportView(lstArtists);
		lstArtists.setModel(alm);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(221, 38, 98, 78);
		contentPane.add(scrollPane_1);
		JList<Genre> lstGenres = new JList();
		scrollPane_1.setViewportView(lstGenres);
		lstGenres.setModel(glm);
		
		
		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Album album = new Album();
				List<Artist> artists = new ArrayList<>();
				List<Genre> genres = new ArrayList<>();
				album.setName(txtName.getText());
				album.setReleaseDate(Date.valueOf(txtReleaseDate.getText()));
				for (Artist a : lstArtists.getSelectedValuesList())
					album.getArtists().add(a);
				for (Genre g : lstGenres.getSelectedValuesList())
					album.getGenres().add(g);
				try {
					jvdb.addAlbum(album);
					((JButton) e.getSource()).getParent().getParent().hide();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});


		

	}
}
