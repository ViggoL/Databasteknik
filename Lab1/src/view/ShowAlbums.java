package src.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import src.model.Album;
import src.model.JvdbInterface;
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

public class ShowAlbums extends JFrame {

	private JPanel contentPane;
	private JTable tblAlbums;
	private JTextField textField;

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	public ShowAlbums(JvdbInterface jvdb) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		DefaultTableModel tmodel = new DefaultTableModel();
		tmodel.addColumn("Name");
		tmodel.addColumn("Release date");
		tmodel.addColumn("Artists");
		tmodel.addColumn("Genres");
		tmodel.addColumn("Rating");
		try {
			List<Album> albums = jvdb.getAlbums();
			for (Album a : albums)
				tmodel.addRow(a.getArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		tblAlbums = new JTable();
		scrollPane.setViewportView(tblAlbums);
		tblAlbums.setModel(tmodel);
		
		JLabel lblSearch = new JLabel("Search");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JRadioButton rdbtnName = new JRadioButton("Name");
		rdbtnName.setSelected(true);
		
		JRadioButton rdbtnGenre = new JRadioButton("Genre");
		
		JRadioButton rdbtnAuthor = new JRadioButton("Author");
		
		JRadioButton rdbtnReleaseDate = new JRadioButton("Release date");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnReleaseDate);
		btnGroup.add(rdbtnAuthor);
		btnGroup.add(rdbtnGenre);
		btnGroup.add(rdbtnName);
		
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSearch)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnReleaseDate)
						.addComponent(rdbtnAuthor)
						.addComponent(rdbtnGenre)
						.addComponent(rdbtnName))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(0)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSearch)
							.addGap(5)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(rdbtnName)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnAuthor)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnReleaseDate)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnGenre)))
					.addGap(3))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
}
