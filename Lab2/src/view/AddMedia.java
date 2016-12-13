package src.view;

import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import src.controller.MediaController;
import src.model.JvdbInterface;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.UIManager;
import javax.swing.JRadioButton;

public class AddMedia extends JFrame implements ActionListener {
	
	private JFrame me;
	protected JPanel contentPane;
	private JLabel nameLabel, relDateLabel, genreLabel, profLabel, titleLabel;
	public JList movieGenreList,albumGenreList, genreList;
	public JComboBox profComboBox;
	public AbstractButton rdbtnAlbum, rdbtnMovie;
	private JViewport port;
	
	public JTextField txtReleaseDate, titleTextField, nameTextField;
	public JFormattedTextField releaseDate_FormattedTextField;

	public AddMedia(JvdbInterface jvdb) {
		try {
			setBackground(new Color(153, 204, 255));
			this.me = this;
			System.out.println("I'm open!");
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			setBounds(100, 100, 310, 324);
			contentPane = new JPanel();
			contentPane.setBorder(UIManager.getBorder("Button.border"));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			nameLabel = new JLabel("Name");
			nameLabel.setBounds(157, 55, 135, 22);
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

			genreLabel = new JLabel("Genres");
			genreLabel.setBounds(10, 165, 98, 22);
			contentPane.add(genreLabel);
			
			Color bg = new Color(153, 204, 255);
			JButton btnOK = new JButton("OK");
			btnOK.setForeground(new Color(0, 0, 153));
			btnOK.setOpaque(true);
			btnOK.setBackground(bg);

			btnOK.setBounds(157, 186, 135, 98);
			contentPane.add(btnOK);

			titleTextField = new JTextField();
			titleTextField.setForeground(Color.DARK_GRAY);
			titleTextField.setText("title");
			titleTextField.setBounds(10, 75, 135, 22);
			contentPane.add(titleTextField);
			titleTextField.setColumns(10);
			
			profLabel = new JLabel("Profession");
			profLabel.setBounds(157, 110, 100, 22);
			contentPane.add(profLabel);

			profComboBox = new JComboBox();
			profComboBox.setBackground(new Color(153, 204, 255));
			profComboBox.setEditable(true);
			profComboBox.setModel(new DefaultComboBoxModel(new String[] { "", "Artist","Band","Composer", "Director" }));
			profComboBox.setBounds(157, 130, 135, 20);
			profComboBox.setEditable(true);
			contentPane.add(profComboBox);

			releaseDate_FormattedTextField.setForeground(new Color(0, 0, 153));
			releaseDate_FormattedTextField.setBounds(10, 130, 135, 22);
			contentPane.add(releaseDate_FormattedTextField);

			nameTextField = new JTextField();
			nameTextField.setForeground(Color.DARK_GRAY);
			nameTextField.setColumns(10);
			nameTextField.setBounds(157, 75, 135, 22);
			contentPane.add(nameTextField);

			titleLabel = new JLabel("Title");
			titleLabel.setBounds(10, 55, 41, 22);
			contentPane.add(titleLabel);

			movieGenreList = new JList();
			movieGenreList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			albumGenreList = new JList();
			albumGenreList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			genreList = new JList();
			genreList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			genreList.setSelectionBackground(new Color(255, 255, 255));
			
			String [] movieGenres = new String []{"Comedy","Horror","Drama","Action","RomCom","SciFi","Crime","Mystery"};
			String [] albumGenres = new String [] { "Rock","Pop","Classical", "Funk","House","Soul","Electronica","HipHop"};
			
			movieGenreList.setListData(movieGenres);
			albumGenreList.setListData(albumGenres);
			
			port = new JViewport();
			port.add(genreList, null);
			port.setBackground(bg);
			JScrollPane scrollPane = new JScrollPane(port);
			scrollPane.setViewportBorder(UIManager.getBorder("Button.border"));
			scrollPane.setBounds(10, 186, 135, 100);
			contentPane.add(scrollPane);

			rdbtnMovie = new JRadioButton("Movie");
			rdbtnMovie.setMnemonic(KeyEvent.VK_M);
			rdbtnMovie.addActionListener(this);
			rdbtnMovie.setBounds(10, 20, 100, 23);
			contentPane.add(rdbtnMovie);

			rdbtnAlbum = new JRadioButton("Album");
			rdbtnAlbum.setMnemonic(KeyEvent.VK_A);
			rdbtnAlbum.addActionListener(this);
			
			rdbtnAlbum.setBounds(157, 20, 100, 23);
			contentPane.add(rdbtnAlbum);

			ButtonGroup mediaButtonGroup = new ButtonGroup();
			mediaButtonGroup.add(rdbtnAlbum);
			mediaButtonGroup.add(rdbtnMovie);

			MediaController ac = new MediaController(jvdb);

			btnOK.addActionListener(ac.new AddMedia(this));
		} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (java.lang.ArrayIndexOutOfBoundsException e1){
			SwingUtilities.invokeLater(new ErrorDialogue(e1.getMessage()));
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (rdbtnAlbum.isSelected()) {
			nameLabel.setText("Name of Artist");
			profComboBox.setSelectedItem("Artist");
			genreList = albumGenreList; port.add(genreList);
			SwingUtilities.updateComponentTreeUI(me);

		} else if (rdbtnMovie.isSelected()){
			nameLabel.setText("Name of Director");
			profComboBox.setSelectedItem("Director");
			genreList = movieGenreList; port.add(genreList);
			SwingUtilities.updateComponentTreeUI(me);

		}
		
	}
}