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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
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
import javax.swing.AbstractButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class AddMedia extends JFrame implements ActionListener {
	
	private JFrame me;
	protected JPanel contentPane;
	private JLabel nameLabel, relDateLabel, genreLabel, profLabel, titleLabel;
	public JList movieGenreList,albumGenreList, genreList;
	public JComboBox profComboBox;
	private AbstractButton rdbtnAlbum, rdbtnMovie;
	private JViewport port;
	
	public JTextField txtReleaseDate, titleTextField, nameTextField;
	public JFormattedTextField releaseDate_FormattedTextField;

	public AddMedia(JvdbInterface jvdb) {
		this.me = this;
		System.out.println("I'm open!");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 230, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nameLabel = new JLabel("Name");
		nameLabel.setBounds(122, 55, 100, 22);
		contentPane.add(nameLabel);

		relDateLabel = new JLabel("Release date");
		relDateLabel.setBounds(10, 110, 100, 22);
		contentPane.add(relDateLabel);

		String formatString = "yyyy-MM-dd";

		DateFormatter formatter = new DateFormatter(new SimpleDateFormat(formatString));
		DateFormatter displayFormatter = new DateFormatter(new SimpleDateFormat("dd MMMM yyyy"));
		DefaultFormatterFactory factory = new DefaultFormatterFactory(displayFormatter, displayFormatter, formatter);

		releaseDate_FormattedTextField = new JFormattedTextField(factory);
		releaseDate_FormattedTextField.setToolTipText("Year-Month-date: " + formatString.toLowerCase());

		contentPane.add(releaseDate_FormattedTextField);

		profLabel = new JLabel("Profession");
		profLabel.setBounds(122, 110, 100, 22);
		contentPane.add(profLabel);

		genreLabel = new JLabel("Genres");
		genreLabel.setBounds(10, 165, 98, 22);
		contentPane.add(genreLabel);

		JButton btnOK = new JButton("OK");

		btnOK.setBounds(122, 261, 100, 23);
		contentPane.add(btnOK);

		titleTextField = new JTextField();
		titleTextField.setForeground(Color.DARK_GRAY);
		titleTextField.setText("title");
		titleTextField.setBounds(10, 75, 100, 22);
		contentPane.add(titleTextField);
		titleTextField.setColumns(10);

		profComboBox = new JComboBox();
		profComboBox.setEditable(true);
		profComboBox.setModel(new DefaultComboBoxModel(new String[] { "", "Artist", "Director" }));
		profComboBox.setBounds(122, 131, 100, 20);
		contentPane.add(profComboBox);

		releaseDate_FormattedTextField.setForeground(Color.LIGHT_GRAY);
		releaseDate_FormattedTextField.setBounds(10, 131, 100, 22);
		contentPane.add(releaseDate_FormattedTextField);

		nameTextField = new JTextField();
		nameTextField.setForeground(Color.DARK_GRAY);
		nameTextField.setColumns(10);
		nameTextField.setBounds(122, 75, 100, 22);
		contentPane.add(nameTextField);

		titleLabel = new JLabel("Title");
		titleLabel.setBounds(10, 55, 41, 22);
		contentPane.add(titleLabel);

		movieGenreList = new JList();
		albumGenreList = new JList();
		genreList = new JList();
		
		String [] movieGenres = new String []{"Comedy", "Horror", "Drama", "Action", "RomCom", "SciFi" };
		String [] albumGenres = new String [] { "Rock", "Classical", "Funk","House","Electronica","HipHop"};
		
		movieGenreList.setListData(movieGenres);
		albumGenreList.setListData(albumGenres);
		
		port = new JViewport();
		port.add(genreList, null);	
		JScrollPane scrollPane = new JScrollPane(port);
		scrollPane.setViewportBorder(UIManager.getBorder("Button.border"));
		scrollPane.setBounds(10, 186, 100, 98);
		contentPane.add(scrollPane);

		rdbtnMovie = new JRadioButton("Movie");
		rdbtnMovie.setMnemonic(KeyEvent.VK_M);
		rdbtnMovie.addActionListener(this);
		rdbtnMovie.setBounds(10, 20, 100, 23);
		contentPane.add(rdbtnMovie);

		rdbtnAlbum = new JRadioButton("Album");
		rdbtnAlbum.setMnemonic(KeyEvent.VK_A);
		rdbtnAlbum.addActionListener(this);
		
		rdbtnAlbum.setBounds(122, 20, 100, 23);
		contentPane.add(rdbtnAlbum);

		ButtonGroup mediaButtonGroup = new ButtonGroup();
		mediaButtonGroup.add(rdbtnAlbum);
		mediaButtonGroup.add(rdbtnMovie);

		MediaController ac = new MediaController(jvdb);

		btnOK.addActionListener(ac.new AddMedia(this));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (rdbtnAlbum.isSelected()) {
			nameLabel.setText("Artist Name");
			profComboBox.setSelectedItem("Artist");
			genreList = albumGenreList; port.add(genreList);
			SwingUtilities.updateComponentTreeUI(me);

		} else if (rdbtnMovie.isSelected()){
			nameLabel.setText("Director Name");
			profComboBox.setSelectedItem("Director");
			genreList = movieGenreList; port.add(genreList);
			SwingUtilities.updateComponentTreeUI(me);

		}
		
	}
}