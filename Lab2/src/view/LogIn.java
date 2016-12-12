package src.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.controller.MenuController;
import src.model.JvdbInterface;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LogIn extends JFrame {

	private JPanel contentPane;
	public JTextField txtUsername;
	public JPasswordField txtPassword;
	JvdbInterface jvdb;
	private JLabel databaseLabel;
	private JTextField databaseTextField;

	/**
	 * Create the frame.
	 */
	public LogIn(final JvdbInterface jvdb) {
		this.jvdb = jvdb;
		MenuController controller = new MenuController(jvdb);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 150, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(6, 65, 91, 16);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(6, 115, 61, 16);
		contentPane.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(6, 80, 134, 28);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		

		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(6, 130, 134, 28);
		contentPane.add(txtPassword);
		
		JButton btnOK = new JButton("Log in");
		btnOK.addActionListener(controller.new LogIn(this));
		btnOK.setBounds(40, 166, 70, 41);
		contentPane.add(btnOK);
		
		databaseLabel = new JLabel("Database");
		databaseLabel.setBounds(6, 15, 91, 16);
		contentPane.add(databaseLabel);
		
		databaseTextField = new JTextField();
		databaseTextField.setForeground(new Color(102, 102, 102));
		databaseTextField.setText("localhost");
		databaseTextField.setColumns(10);
		databaseTextField.setBounds(6, 30, 134, 28);
		contentPane.add(databaseTextField);
	}
}
