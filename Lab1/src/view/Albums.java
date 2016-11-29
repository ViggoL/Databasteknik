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
	public Albums(final JVDB jvdb) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 351, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAddAlbum = new JButton("Add album");
		btnAddAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddAlbum au = new AddAlbum(jvdb);
				au.show();
				
			}
		});
		btnAddAlbum.setBounds(10, 11, 89, 23);
		contentPane.add(btnAddAlbum);
	}
}
