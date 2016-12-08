package src.view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.TextField;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import src.controller.MediaController;
import src.model.AlbumGenre;
import src.model.Genre;
import src.model.JvdbInterface;
import src.model.MediaPerson;
import src.model.PersonType;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class AddMedia extends JFrame {

	protected JPanel contentPane;
	public JTextField txtReleaseDate;
	private JTextField nameTextField;
	private JLabel nameLabel, relDateLabel, genreLabel, profLabel;
	private JFormattedTextField releaseDate_FormattedTextField;
	private JComboBox comboBox_1;
	private JTextField textField;
	private JLabel titleLabel;
	
	public AddMedia(JvdbInterface jvdb) {
		System.out.println("I'm open!");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 346, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		nameLabel = new JLabel("Artist Name");
		nameLabel.setBounds(160, 6, 98, 22);
		contentPane.add(nameLabel);
		
		relDateLabel = new JLabel("Release date");
		relDateLabel.setBounds(10, 124, 98, 22);
		contentPane.add(relDateLabel);
		
		String formatString = "yyyy-MM-dd";

		DateFormatter formatter = new DateFormatter(new SimpleDateFormat(formatString));
		DateFormatter displayFormatter = new DateFormatter(new SimpleDateFormat("dd MMMM yyyy"));
		DefaultFormatterFactory factory = new DefaultFormatterFactory(displayFormatter, displayFormatter, formatter);

		releaseDate_FormattedTextField = new JFormattedTextField(factory);
		releaseDate_FormattedTextField.setToolTipText("Year-Month-date: " + formatString.toLowerCase());

		contentPane.add(releaseDate_FormattedTextField);
		
		profLabel = new JLabel("Profession");
		profLabel.setBounds(160, 66, 98, 22);
		contentPane.add(profLabel);
		
		genreLabel = new JLabel("Genres");
		genreLabel.setBounds(10, 66, 98, 22);
		contentPane.add(genreLabel);
		
		JButton btnOK = new JButton("OK");
		
		btnOK.setBounds(230, 189, 89, 23);
		contentPane.add(btnOK);
		
		nameTextField = new JTextField();
		nameTextField.setForeground(Color.DARK_GRAY);
		nameTextField.setText("title");
		nameTextField.setBounds(10, 38, 98, 26);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		JComboBox profComboBox = new JComboBox();
		profComboBox.setEditable(true);
		profComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Artist", "Director"}));
		profComboBox.setBounds(160, 85, 98, 27);
		contentPane.add(profComboBox);
		
		JFormattedTextField frmtdtxtfldYyyymmdd = new JFormattedTextField();
		frmtdtxtfldYyyymmdd.setToolTipText("YYYY-MM-DD");
		releaseDate_FormattedTextField.setForeground(Color.LIGHT_GRAY);
		releaseDate_FormattedTextField.setBounds(10, 147, 98, 22);
		contentPane.add(releaseDate_FormattedTextField);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"", "Rock", "Classical", "Romantic Comedy", "Horror", "Comedy"}));
		comboBox_1.setEditable(true);
		comboBox_1.setBounds(10, 85, 98, 27);
		contentPane.add(comboBox_1);
		
		textField = new JTextField();
		textField.setText("name");
		textField.setForeground(Color.DARK_GRAY);
		textField.setColumns(10);
		textField.setBounds(160, 38, 98, 26);
		contentPane.add(textField);
		
		titleLabel = new JLabel("Title");
		titleLabel.setBounds(10, 9, 41, 22);
		contentPane.add(titleLabel);
		
		MediaController ac = new MediaController(jvdb);
		
		btnOK.addActionListener(ac.new AddMedia(this));
	}
}