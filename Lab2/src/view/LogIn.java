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

public class LogIn extends JFrame {

	private JPanel contentPane;
	public JTextField txtUsername;
	public JPasswordField txtPassword;
	JvdbInterface jvdb;

	/**
	 * Create the frame.
	 */
	public LogIn(final JvdbInterface jvdb) {
		this.jvdb = jvdb;
		MenuController controller = new MenuController(jvdb);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 156, 173);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(6, 6, 91, 16);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(6, 53, 61, 16);
		contentPane.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(6, 25, 134, 28);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		

		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(6, 71, 134, 28);
		contentPane.add(txtPassword);
		
		JButton btnOK = new JButton("Log in");
		btnOK.addActionListener(controller.new LogIn(this));
		btnOK.setBounds(23, 111, 117, 29);
		contentPane.add(btnOK);
	}
}
