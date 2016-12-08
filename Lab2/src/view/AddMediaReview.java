package src.view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class AddMediaReview extends JFrame {

	protected JPanel contentPane;
	public JTextArea txtComment = new JTextArea();
	public final JSlider slider = new JSlider();
	public Object media;

	public AddMediaReview() throws HeadlessException {
		super();
	}

	public AddMediaReview(GraphicsConfiguration gc) {
		super(gc);
	}

	public AddMediaReview(String title) throws HeadlessException {
		super(title);
	}

	public AddMediaReview(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

}