package src.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.controller.AlbumController;
import src.controller.MenuController;
import src.model.JvdbInterface;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddArtist extends JFrame {

	private JPanel contentPane;
	public JTextField txtName;
	public JTextArea txtBio;

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public AddArtist(JvdbInterface jvdb) {
		AlbumController controller = new AlbumController(jvdb);
		
		setBounds(100, 100, 192, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAristName = new JLabel("Arist name");
		lblAristName.setBounds(10, 11, 97, 14);
		contentPane.add(lblAristName);
		
		txtName = new JTextField();
		txtName.setBounds(10, 30, 163, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(controller.new AddArtist(this));
		btnAdd.setBounds(84, 181, 89, 23);
		contentPane.add(btnAdd);
		
		
		JLabel lblArtistBio = new JLabel("Artist bio");
		lblArtistBio.setBounds(10, 61, 156, 14);
		contentPane.add(lblArtistBio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 77, 163, 93);
		contentPane.add(scrollPane);
		
		txtBio = new JTextArea();
		scrollPane.setViewportView(txtBio);
	}
}
