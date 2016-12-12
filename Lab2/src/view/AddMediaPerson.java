package src.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.controller.MediaController;
import src.controller.MenuController;
import src.model.JvdbInterface;
import src.model.MediaPerson;
import src.model.MediaPersonType;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddMediaPerson extends JFrame {

	private JPanel contentPane;
	private JLabel profLabel;
	public JTextField txtName;
	public JTextArea txtBio;
	public JComboBox profComboBox;

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public AddMediaPerson(JvdbInterface jvdb) {
		MediaController controller = new MediaController(jvdb);
		
		setBounds(100, 100, 192, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 15, 97, 14);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(10, 30, 163, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		profLabel = new JLabel("Profession");
		profLabel.setBounds(10, 55, 100, 22);
		contentPane.add(profLabel);
		
		profComboBox = new JComboBox();
		profComboBox.setBackground(new Color(153, 204, 255));
		profComboBox.setEditable(true);
		profComboBox.setModel(new DefaultComboBoxModel(new String[] { "", "Artist","Composer", "Director" }));
		profComboBox.setBounds(10, 75, 163, 20);
		profComboBox.setEditable(true);
		contentPane.add(profComboBox);
		
		JLabel bioLabel = new JLabel("Biography");
		bioLabel.setBounds(10, 105, 156, 14);
		contentPane.add(bioLabel);
		
		JScrollPane bioScrollPane = new JScrollPane();
		bioScrollPane.setBounds(10, 120, 163, 93);
		contentPane.add(bioScrollPane);
		
		txtBio = new JTextArea();
		bioScrollPane.setViewportView(txtBio);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(controller.new AddMediaPerson(this));
		btnAdd.setBounds(84, 219, 89, 23);
		contentPane.add(btnAdd);		
	}
}
