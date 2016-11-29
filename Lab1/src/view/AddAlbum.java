package src.view;
import src.model.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Label;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.TextField;
import java.sql.SQLException;
import java.util.List;
import java.awt.Choice;
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

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public AddAlbum(JvdbInterface jvdb) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 341, 219);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Label label = new Label("Name");
		label.setBounds(10, 10, 41, 22);
		contentPane.add(label);
		
		TextField textField = new TextField();
		textField.setBounds(10, 38, 98, 22);
		contentPane.add(textField);
		
		Label label_1 = new Label("Release date");
		label_1.setBounds(10, 66, 98, 22);
		contentPane.add(label_1);
		
		TextField textField_1 = new TextField();
		textField_1.setBounds(10, 94, 98, 22);
		contentPane.add(textField_1);
		JList<String> lstArtists = new JList();
		lstArtists.setBounds(114, 42, 98, 74);
		contentPane.add(lstArtists);
		JList<String> lstGenres = new JList();
		lstGenres.setBounds(221, 42, 98, 74);
		contentPane.add(lstGenres);
		DefaultListModel<String> alm = new DefaultListModel<String>();
		DefaultListModel<String> glm = new DefaultListModel<String>();
		
		try {
			List<Artist> artists = jvdb.getArtists();
			for (Artist a : artists) 
				alm.addElement(a.getName());
			lstArtists.setModel(alm);
			List<Genre> genres = jvdb.getGenres();
			for (Genre g : genres)
				glm.addElement(g.getName());
			lstGenres.setModel(glm);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Label label_2 = new Label("Artists");
		label_2.setBounds(114, 14, 98, 22);
		contentPane.add(label_2);
		

		
		Label label_3 = new Label("Genres");
		label_3.setBounds(221, 14, 98, 22);
		contentPane.add(label_3);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnOK.setBounds(226, 131, 89, 23);
		contentPane.add(btnOK);
		

		

	}
}
