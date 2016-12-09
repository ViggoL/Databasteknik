package src.view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.TextField;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JViewport;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

public class AddMedia extends JFrame {

	protected JPanel contentPane;
	public JTextField txtReleaseDate;
	public JTextField titleTextField;
	private JLabel nameLabel, relDateLabel, genreLabel, profLabel;
	public JFormattedTextField releaseDate_FormattedTextField;
	public JTextField nameTextField;
	public JList genreList;
	private JLabel titleLabel;
	public JComboBox profComboBox;
	
	public AddMedia(JvdbInterface jvdb) {
		System.out.println("I'm open!");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 230, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		nameLabel = new JLabel("Name");
		nameLabel.setBounds(122, 20, 100, 22);
		contentPane.add(nameLabel);
		
		relDateLabel = new JLabel("Release date");
		relDateLabel.setBounds(10, 70, 100, 22);
		contentPane.add(relDateLabel);
		
		String formatString = "yyyy-MM-dd";

		DateFormatter formatter = new DateFormatter(new SimpleDateFormat(formatString));
		DateFormatter displayFormatter = new DateFormatter(new SimpleDateFormat("dd MMMM yyyy"));
		DefaultFormatterFactory factory = new DefaultFormatterFactory(displayFormatter, displayFormatter, formatter);

		releaseDate_FormattedTextField = new JFormattedTextField(factory);
		releaseDate_FormattedTextField.setToolTipText("Year-Month-date: " + formatString.toLowerCase());

		contentPane.add(releaseDate_FormattedTextField);
		
		profLabel = new JLabel("Profession");
		profLabel.setBounds(122, 66, 100, 30);
		contentPane.add(profLabel);
		
		genreLabel = new JLabel("Genres");
		genreLabel.setBounds(10, 121, 98, 22);
		contentPane.add(genreLabel);
		
		JButton btnOK = new JButton("OK");
		
		btnOK.setBounds(122, 223, 100, 23);
		contentPane.add(btnOK);
		
		titleTextField = new JTextField();
		titleTextField.setForeground(Color.DARK_GRAY);
		titleTextField.setText("title");
		titleTextField.setBounds(10, 40, 100, 22);
		contentPane.add(titleTextField);
		titleTextField.setColumns(10);
		
		profComboBox = new JComboBox();
		profComboBox.setEditable(true);
		profComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Artist", "Director"}));
		profComboBox.setBounds(122, 92, 100, 20);
		contentPane.add(profComboBox);
		
		//JFormattedTextField frmtdtxtfldYyyymmdd = new JFormattedTextField();
		//frmtdtxtfldYyyymmdd.setToolTipText("YYYY-MM-DD");
		releaseDate_FormattedTextField.setForeground(Color.LIGHT_GRAY);
		releaseDate_FormattedTextField.setBounds(10, 91, 100, 22);
		contentPane.add(releaseDate_FormattedTextField);
		
		nameTextField = new JTextField();
		nameTextField.setForeground(Color.DARK_GRAY);
		nameTextField.setColumns(10);
		nameTextField.setBounds(122, 40, 100, 22);
		contentPane.add(nameTextField);
		
		titleLabel = new JLabel("Title");
		titleLabel.setBounds(10, 20, 41, 22);
		contentPane.add(titleLabel);
		
		genreList = new JList();
		genreList.setListData(new Object[]{"Rock","Classical","Funk","Comedy","Horror","Drama","Action"});
		JViewport port = new JViewport();
		port.add(genreList, null);
		JScrollPane scrollPane = new JScrollPane(port);
		scrollPane.setViewportBorder(UIManager.getBorder("Button.border"));
		scrollPane.setBounds(10, 148, 100, 98);
		contentPane.add(scrollPane);
		
		JRadioButton rdbtnMovie = new JRadioButton("Movie");
		rdbtnMovie.setBounds(122, 148, 100, 23);
		contentPane.add(rdbtnMovie);
		
		JRadioButton rdbtnAlbum = new JRadioButton("Album");
		rdbtnAlbum.setBounds(122, 172, 100, 23);
		contentPane.add(rdbtnAlbum);
		
		ButtonGroup mediaButtonGroup = new ButtonGroup();
		mediaButtonGroup.add(rdbtnAlbum);mediaButtonGroup.add(rdbtnMovie);
		
		
		MediaController ac = new MediaController(jvdb);
		
		btnOK.addActionListener(ac.new AddMedia(this));
	}
	
	public Object[] getValues(){
		return new Object[]{
				this.titleTextField.getText(),
				Date.valueOf(this.releaseDate_FormattedTextField.getText()),
				new MediaPerson(PersonType.valueOf((String) this.profComboBox.getSelectedItem()), 
						this.nameTextField.getText(),
						""),
				this.genreList.getSelectedValues()
				};
	}
}