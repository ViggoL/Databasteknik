package src.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.model.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import javax.swing.JPasswordField;

public class AddUser extends JFrame {

	private JPanel contentPane;
	private JPasswordField txtPw;
	private JPasswordField txtPwConfirm;


	/**
	 * Create the frame.
	 */
	public AddUser(final JvdbInterface jvdb) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 139, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 11, 76, 14);
		contentPane.add(lblUsername);
		
		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(10, 59, 76, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 107, 76, 14);
		contentPane.add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password");
		lblConfirmPassword.setBounds(10, 149, 102, 14);
		contentPane.add(lblConfirmPassword);
		
		JButton btnOK = new JButton("OK");
		btnOK.setBounds(10, 227, 102, 23);
		contentPane.add(btnOK);
		
		final TextField txtUsername = new TextField();
		txtUsername.setBounds(10, 31, 102, 22);
		contentPane.add(txtUsername);
		
		final TextField txtEmail = new TextField();
		txtEmail.setBounds(10, 79, 102, 22);
		contentPane.add(txtEmail);
		
		txtPw = new JPasswordField();
		txtPw.setBounds(10, 123, 103, 20);
		contentPane.add(txtPw);
		
		txtPwConfirm = new JPasswordField();
		txtPwConfirm.setBounds(10, 170, 103, 20);
		contentPane.add(txtPwConfirm);
		
		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!jvdb.addUser(txtUsername.getText(), txtPw.getText(), txtEmail.getText()))
						throw new Exception("Could not add user.");
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (Exception e2)
				{
					
				}
			}
		});
	}
}
