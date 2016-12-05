package src.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import src.controller.AlbumController;
import src.controller.MenuController;
import src.model.JVDB;
import src.model.JvdbInterface;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JvdbInterface jvdb;
	private MenuController controller;
	
	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public Menu(JvdbInterface jvdb) {
		this.jvdb = jvdb;
		this.controller = new MenuController(jvdb);
		setTitle("JVDB Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 116);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		AlbumController bc = new AlbumController(jvdb);
		JButton btnSearchAlbums = new JButton("Search albums");

		btnSearchAlbums.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable(){

					@Override
					public void run() {
						if (e.getSource() instanceof JButton) {
							JButton b = (JButton) e.getSource();
						}
						ShowAlbums a = new ShowAlbums(jvdb);
						a.show();
					}
				});
			}
		});

//		btnSearchAlbums.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				SwingUtilities.invokeLater(new Runnable(){
//
//					@Override
//					public void run() {
//						if (e.getSource() instanceof JButton) {
//							JButton b = (JButton) e.getSource();
//						}
//						ShowAlbums a = new ShowAlbums(jvdb);
//						a.show();
//						
//						
//					}
//					
//				});
//			}
//		});

		btnSearchAlbums.setBounds(10, 11, 133, 23);
		contentPane.add(btnSearchAlbums);
		
		JButton btnAddAlbum = new JButton("Add album");
//		btnAddAlbum.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				SwingUtilities.invokeLater(new Runnable(){
//
//					@Override
//					public void run() {
//						if (e.getSource() instanceof JButton) {
//							JButton b = (JButton) e.getSource();
//						}
//						AddAlbum a = new AddAlbum(jvdb);
//						a.show();
//						
//						
//					}
//					
//				});
//			}
//		});
		btnAddAlbum.setBounds(10, 45, 133, 23);
		contentPane.add(btnAddAlbum);
		
		JButton btnSearchMovies = new JButton("Search movies");
//		btnSearchMovies.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
		btnSearchMovies.setBounds(154, 11, 132, 23);
		contentPane.add(btnSearchMovies);
		
		JButton btnAddMovie = new JButton("Add movie");
//		btnAddMovie.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
		btnAddMovie.setBounds(153, 45, 133, 23);
		contentPane.add(btnAddMovie);
		this.addWindowListener(controller.new Close());
		btnAddMovie.addActionListener(controller.new AddMovie());
		btnAddAlbum.addActionListener(controller.new AddAlbum());
		btnSearchAlbums.addActionListener(controller.new SearchAlbums());
		btnSearchMovies.addActionListener(controller.new SearchMovies());
	}
}
