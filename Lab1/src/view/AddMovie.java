package src.view;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.common.collect.LinkedListModel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import src.controller.MovieController;
import src.model.JVDB;
import src.model.JvdbInterface;
import src.model.MovieGenre;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class AddMovie extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextField releaseDateTextField;
	private JTextField director1TextField;
	private JTextField director2TextField;
	private JTextField titleField;
	private JButton btnAdd;
	private AddMovie frame;
	private JButton btnCancel;
	private JPanel panel;
	private final JvdbInterface jvdb;
	private JFormattedTextField releaseDate_FormattedTextField;
	private String formatString;
	private JLabel lblGenre;
	private JScrollPane scrollPane;
	private JList list;

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public AddMovie(final JvdbInterface jvdb) throws SQLException {
		this.jvdb = jvdb;
		this.setTitle("Add Movie");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int a = 300;
		double c = a * 1.618;
		int b = Integer.valueOf(String.valueOf(c).split("\\.")[0]);

		setBounds(100, 100, 300, 480);
		contentPane = new JPanel();
		TitledBorder title = new TitledBorder("Movie Details ");
		// title.setBorder(new EmptyBorder(20, 5, 40, 5));
		contentPane.setBorder(title);
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("200px:grow"),},
			new RowSpec[] {
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("30px"),
				RowSpec.decode("30px"),
				RowSpec.decode("30px"),
				RowSpec.decode("30px"),
				RowSpec.decode("30px"),
				RowSpec.decode("70px:grow"),
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("30px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblName = new JLabel("Title");
		contentPane.add(lblName, "2, 2, fill, bottom");

		titleField = new JTextField();
		titleField.setColumns(10);
		contentPane.add(titleField, "2, 3, fill, default");

		JLabel lblReleaseDate = new JLabel("Release date");
		contentPane.add(lblReleaseDate, "2, 4, fill, bottom");

		formatString = "yyyy-MM-dd";

		DateFormatter formatter = new DateFormatter(new SimpleDateFormat(formatString));
		DateFormatter displayFormatter = new DateFormatter(new SimpleDateFormat("dd MMMM yyyy"));
		DefaultFormatterFactory factory = new DefaultFormatterFactory(displayFormatter, displayFormatter, formatter);

		releaseDate_FormattedTextField = new JFormattedTextField(factory);
		releaseDate_FormattedTextField.setToolTipText("Year-Month-date: " + formatString.toLowerCase());

		contentPane.add(releaseDate_FormattedTextField, "2, 5, fill, default");

		MovieController mc = new MovieController(jvdb);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(mc.new HideWindow());
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(mc.new AddMovie(this));

		lblGenre = new JLabel("Genre");
		contentPane.add(lblGenre, "2, 6, fill, bottom");
		Object[] a1 = jvdb.getMovieGenres().toArray();
		int i = a1.length;
		String[] genreStrings = new String[i];

		for (Object o : a1) {
			genreStrings[--i] = o.toString();
		}
		DefaultListModel listModel = new DefaultListModel();

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "2, 7, fill, default");

		list = new JList(jvdb.getMovieGenres().toArray());

		scrollPane.setViewportView(list);
						
								JLabel label = new JLabel("Director(s)");
								contentPane.add(label, "2, 9, fill, bottom");
				
						director1TextField = new JTextField();
						director1TextField.setColumns(10);
						contentPane.add(director1TextField, "2, 11, fill, default");
		
				director2TextField = new JTextField();
				director2TextField.setColumns(10);
				contentPane.add(director2TextField, "2, 13, fill, default");

		contentPane.add(btnAdd, "2, 17, right, fill");
		contentPane.add(btnCancel, "2, 17, center, fill");
	}

	@Override
	public void run() {
		try {
			frame = new AddMovie(jvdb);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// frame
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMovie frame = new AddMovie(new JVDB());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
