package src.view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.TextField;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import src.model.AlbumGenre;
import src.model.Genre;
import src.model.MediaPerson;

public class AddMedia extends JFrame {

	protected JPanel contentPane;
	public TextField txtName;
	public TextField txtReleaseDate;
	public JScrollPane scrollPane;
	public JScrollPane scrollPane_1;
	public Label label;
	public Label label_1;
	public Label label_2;
	public Label label_3;
	protected final JList<Genre> lstGenres = new JList();
	protected final JList<MediaPerson> lstMediaPersons = new JList();

	public AddMedia() throws HeadlessException {
		super();
	}

	public AddMedia(GraphicsConfiguration gc) {
		super(gc);
	}

	public AddMedia(String title) throws HeadlessException {
		super(title);
	}

	public AddMedia(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	/**
	 * Create the frame.
	 * @param jvdb 
	 */
	private void refresh(List<MediaPerson> plist, List<Genre> glist) {
		ListModel<MediaPerson> alm = new DefaultListModel<>();
		DefaultListModel<Genre> glm = new DefaultListModel<>();
		
		for (Genre g : glist)
			glm.addElement(g);
		for (MediaPerson a : plist) 
			((DefaultListModel<MediaPerson>) alm).addElement(a);
		lstGenres.setModel(glm);
		lstMediaPersons.setModel(alm);
	}

}