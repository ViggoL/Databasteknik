package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.JVDB;

import javax.swing.JTextField;
import java.awt.Label;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.TextField;

public class AddAlbum extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public AddAlbum(JVDB jvdb) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
	}
}
