package src.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.model.*;

import java.awt.Checkbox;
import java.awt.Button;
import java.awt.List;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Albums extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public Albums(final JvdbInterface jvdb) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 351, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAddAlbum = new JButton("Add album");
		btnAddAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Thread t = new Thread()
						{
							public void run()
							{
								AddAlbum au = new AddAlbum(jvdb);
								au.show();
							}
						};
						t.start();

				
			}
		});
		btnAddAlbum.setBounds(10, 11, 156, 23);
		contentPane.add(btnAddAlbum);
		
		JButton btnSearch = new JButton("Search albums");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowAlbums sa = new ShowAlbums(jvdb);
				sa.show();
			}
		});
		btnSearch.setBounds(10, 45, 156, 23);
		contentPane.add(btnSearch);
	}
}

