package src.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.model.JVDB;
import src.model.JvdbInterface;

import javax.swing.JButton;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JvdbInterface jvdb;
	
	
	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public Menu(JvdbInterface jvdb) {
		this.jvdb = jvdb;
		setTitle("JVDB Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 116);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSearchAlbums = new JButton("Search albums");
		btnSearchAlbums.setBounds(10, 11, 133, 23);
		contentPane.add(btnSearchAlbums);
		
		JButton btnAddAlbum = new JButton("Add album");
		btnAddAlbum.setBounds(10, 45, 133, 23);
		contentPane.add(btnAddAlbum);
		
		JButton btnSearchMovies = new JButton("Search movies");
		btnSearchMovies.setBounds(154, 11, 132, 23);
		contentPane.add(btnSearchMovies);
		
		JButton btnAddMovie = new JButton("Add movie");
		btnAddMovie.setBounds(153, 45, 133, 23);
		contentPane.add(btnAddMovie);
	}
}
