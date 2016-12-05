package src.view;

import src.controller.AlbumController;
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
import javax.swing.SwingUtilities;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddAlbum extends JFrame {

	private JPanel contentPane;
	public TextField txtName, txtReleaseDate;
	public JScrollPane scrollPane, scrollPane_1;
	public Label label, label_1, label_2, label_3;
	private final JList<AlbumGenre> lstGenres = new JList();
	private final JList<Artist> lstArtists = new JList();
	public JSlider sldrRating = new JSlider();

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	
	private void refresh(List<Artist> artists, List<AlbumGenre> genres)
	{
		DefaultListModel<Artist> alm = new DefaultListModel<Artist>();
		DefaultListModel<AlbumGenre> glm = new DefaultListModel<AlbumGenre>();
		
		for (AlbumGenre g : genres)
			glm.addElement(g);
		for (Artist a : artists) 
			alm.addElement(a);
		lstGenres.setModel(glm);
		lstArtists.setModel(alm);
	}
	
	public AddAlbum(final JvdbInterface jvdb) {
		System.out.println("I'm open!");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 346, 274);
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
		new Thread(){
			public void run()
			{
				try {
					refresh(jvdb.getArtists(), jvdb.getAlbumGenres());
						
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();
		
		label_2 = new Label("Artists");
		label_2.setBounds(114, 14, 98, 22);
		contentPane.add(label_2);
		

		
		label_3 = new Label("Genres");
		label_3.setBounds(221, 14, 98, 22);
		contentPane.add(label_3);
		
		JButton btnOK = new JButton("OK");
		
		btnOK.setBounds(230, 189, 89, 23);
		contentPane.add(btnOK);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(114, 38, 98, 138);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(lstArtists);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(221, 38, 99, 138);
		contentPane.add(scrollPane_1);
		
		scrollPane_1.setViewportView(lstGenres);
		
		final Label lblRating = new Label("Rating - 5");
		lblRating.setBounds(10, 122, 98, 22);
		contentPane.add(lblRating);
		
		
		sldrRating.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				lblRating.setText("Rating - " + sldrRating.getValue());
			}
		});
		sldrRating.setValue(5);
		sldrRating.setMaximum(10);
		sldrRating.setBounds(12, 150, 96, 26);
		contentPane.add(sldrRating);
		
		AlbumController ac = new AlbumController(jvdb);
		
		btnOK.addActionListener(ac.new AddAlbum(this));
	}
}
			
