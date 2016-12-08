package src.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import src.controller.MovieController;
import src.model.Album;
import src.model.JvdbInterface;
import src.model.Media;
import src.model.Movie;
import src.model.MediaAttributes;
import src.model.MediaType;
import src.model.MediaAttributes;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.TextField;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.sql.Date;


public class ShowMovies extends JFrame {

	private JPanel contentPane;
	public JTable tblMovies;
	public JTextField textField;
	public MediaAttributes operations = MediaAttributes.TITLE;
	private String title;
	public List<Media> allMovies;

	public void Refresh(List<Media> list) {
		DefaultTableModel tmodel = new DefaultTableModel();
		tmodel.addColumn("Title");
		tmodel.addColumn("Release date");
		tmodel.addColumn("Directors");
		tmodel.addColumn("Genres");
		tmodel.addColumn("Rating");
		tmodel.addColumn("Added by");
		
		if (list != null)
			for (Media a : list)
				tmodel.addRow(a.toArray());
		
		tblMovies.setModel(tmodel);
	}
	
	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public ShowMovies(final JvdbInterface jvdb) {
		MovieController controller = new MovieController(jvdb);
		setBounds(100, 100, 658, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tblMovies = new JTable();
		scrollPane.setViewportView(tblMovies);
		
		JPanel panel = new JPanel();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(0)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel.setLayout(null);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setBounds(6, 6, 41, 16);
		panel.add(lblSearch);
		
		textField = new JTextField();
		textField.setBounds(6, 25, 133, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(controller.new SearchMovie(this));
		btnOK.setBounds(133, 26, 75, 29);
		panel.add(btnOK);
		ButtonGroup btnGroup = new ButtonGroup();
		
		JRadioButton rdbtnTitle = new JRadioButton("Title");
		rdbtnTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = MediaAttributes.TITLE;
			}
		});
		rdbtnTitle.setBounds(6, 123, 200, 23);
		panel.add(rdbtnTitle);
		rdbtnTitle.setSelected(true);
		btnGroup.add(rdbtnTitle);
		
		JRadioButton rdbtnDirector = new JRadioButton("Director");
		rdbtnDirector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = MediaAttributes.DIRECTOR;
			}
		});
		rdbtnDirector.setBounds(6, 100, 200, 23);
		panel.add(rdbtnDirector);
		btnGroup.add(rdbtnDirector);
		
		JRadioButton rdbtnReleaseDate = new JRadioButton("Release date");
		rdbtnReleaseDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = MediaAttributes.RELEASE_DATE;
			}
		});
		rdbtnReleaseDate.setBounds(6, 53, 200, 23);
		panel.add(rdbtnReleaseDate);
		btnGroup.add(rdbtnReleaseDate);
		
		JRadioButton rdbtnRating = new JRadioButton("Rating");
		rdbtnRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations = MediaAttributes.RATING;
			}
		});
		rdbtnRating.setBounds(6, 76, 72, 23);
		panel.add(rdbtnRating);
		btnGroup.add(rdbtnRating);
		
		JButton btnRateMovie = new JButton("Rate movie");
		btnRateMovie.addActionListener(controller. new ShowAddMovieReview(this));
		btnRateMovie.setBounds(6, 350, 133, 23);
		panel.add(btnRateMovie);
		
		
		contentPane.setLayout(gl_contentPane);
		
		new Thread(){
			public void run()
			{
				try {
					allMovies = jvdb.getMedia(MediaAttributes.ALL,MediaType.MOVIE, title);
					Refresh(allMovies);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}.start();
		
		
	}
}
