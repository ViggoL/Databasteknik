package src.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.controller.MediaController;
import src.controller.MovieController;
import src.model.JvdbInterface;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddDirector extends JFrame {

	private JPanel contentPane;
	public JTextField txtName;
	public JTextArea txtBio;
	public JvdbInterface jvdb;

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public AddDirector(JvdbInterface jvdb) {
		MediaController controller = new MediaController(jvdb);
		setBounds(100, 100, 192, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAristName = new JLabel("Director name");
		lblAristName.setBounds(10, 11, 97, 14);
		contentPane.add(lblAristName);
		
		txtName = new JTextField();
		txtName.setBounds(10, 30, 163, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(controller.new AddMediaPerson(this));
		btnAdd.setBounds(84, 181, 89, 23);
		contentPane.add(btnAdd);
		
		JLabel lblArtistBio = new JLabel("Director bio");
		lblArtistBio.setBounds(10, 61, 156, 14);
		contentPane.add(lblArtistBio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 77, 163, 93);
		contentPane.add(scrollPane);
		
		txtBio = new JTextArea();
		scrollPane.setViewportView(txtBio);
	}
}
