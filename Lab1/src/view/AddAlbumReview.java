package src.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import src.model.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class AddAlbumReview extends JFrame {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public AddAlbumReview(JvdbInterface jvdb, Movie movie) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtComment = new JTextArea();
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
		final JSlider slider = new JSlider();
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

	}
}
