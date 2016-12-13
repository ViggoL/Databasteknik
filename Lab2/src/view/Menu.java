package src.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import src.controller.MediaController;
import src.controller.MenuController;
import src.model.JvdbInterface;
import src.model.MongoJVDB;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	private JPanel contentPane;
	private final JvdbInterface jvdb;
	private MenuController controller;
	private MediaController mc;
	
	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public Menu(final JvdbInterface jvdb) {
		this.jvdb = jvdb;
		this.controller = new MenuController(jvdb);
		
		setTitle("JVDB Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 140);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		mc = new MediaController(jvdb);
		JButton btnSearchMedia = new JButton("Search media");

		btnSearchMedia.setBounds(10, 10, 184, 23);
		contentPane.add(btnSearchMedia);
		
		JButton btnAddMedia = new JButton("Add media");
		btnAddMedia.setBounds(10, 45, 184, 23);
		contentPane.add(btnAddMedia);
		
		JButton btnAddMediaPerson = new JButton("Add media person");
		btnAddMediaPerson.setBounds(10, 80, 184, 23);
		contentPane.add(btnAddMediaPerson);
		
		this.addWindowListener(controller.new Close());
		btnAddMedia.addActionListener(controller.new AddMedia());
		btnSearchMedia.addActionListener(controller.new SearchMedia());
		btnAddMediaPerson.addActionListener(controller.new ShowAddMediaPerson());
	}
}
