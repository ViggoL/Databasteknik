package src.view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import src.controller.MediaController;
import src.model.JvdbInterface;
import src.model.Media;

public class AddMediaReview extends JFrame {


	
	private JPanel contentPane;
	public JTextArea txtComment = new JTextArea();
	public final JSlider slider = new JSlider();
	public Media media = null;
	private JvdbInterface jvdb;
	
	/**
	 * Create the frame.
	 */
	public AddMediaReview(JvdbInterface jvdb, Media media) {
		this.jvdb = jvdb;
		MediaController controller = new MediaController(jvdb);
		this.media = media;
		this.setTitle("Reviewing " + media.getTitle());
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		txtComment.setBounds(6, 31, 438, 141);
		contentPane.add(txtComment);

		
		JLabel lblRating = new JLabel("Rating");
		lblRating.setBounds(16, 184, 61, 16);
		contentPane.add(lblRating);
		
		final JLabel labelRating = new JLabel("5");
		labelRating.setHorizontalAlignment(SwingConstants.CENTER);
		labelRating.setBounds(72, 233, 61, 16);
		contentPane.add(labelRating);
		
		JLabel lblReview = new JLabel("Review text");
		lblReview.setBounds(6, 6, 96, 16);
		contentPane.add(lblReview);
		
		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				labelRating.setText(String.valueOf(slider.getValue()));
			}
		});
		slider.setValue(5);
		slider.setMaximum(10);
		slider.setBounds(6, 205, 190, 29);
		contentPane.add(slider);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(controller.new AddRating(this));
		btnSend.setBounds(327, 233, 117, 29);
		contentPane.add(btnSend);

	}

//	public AddMediaReview() throws HeadlessException {
//		super();
//	}
//
//	public AddMediaReview(GraphicsConfiguration gc) {
//		super(gc);
//	}
//
//	public AddMediaReview(String title) throws HeadlessException {
//		super(title);
//	}
//
//	public AddMediaReview(String title, GraphicsConfiguration gc) {
//		super(title, gc);
//	}

}