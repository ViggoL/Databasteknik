package src.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import java.awt.Color;

public class AddMovie extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTextField releaseDateTextField;
	private JTextField director1TextField;
	private JTextField director2TextField;
	private JTextField titleField;
	private JTextField director3TextField;
	private JSplitPane splitPane;
	private JButton btnAdd;
	private JButton btnCancel;
	private AddMovie frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMovie frame = new AddMovie();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddMovie() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int a = 300;
		double c =  a*1.618;
		int b = Integer.valueOf(String.valueOf(c).split("\\.")[0]);
		
		setBounds(100, 100, 300, 375);
		contentPane = new JPanel();
		TitledBorder title = new TitledBorder("Add Movie ");
		//title.setBorder(new EmptyBorder(20, 5, 40, 5));
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
				RowSpec.decode("30px"),
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("30px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),}));
		
		JLabel lblName = new JLabel("Title");
		contentPane.add(lblName, "2, 2, fill, bottom");
		
		titleField = new JTextField();
		titleField.setColumns(10);
		contentPane.add(titleField, "2, 3, fill, default");
		
		JLabel lblReleaseDate = new JLabel("Release date");
		contentPane.add(lblReleaseDate, "2, 4, fill, bottom");
		
		releaseDateTextField = new JTextField();
		contentPane.add(releaseDateTextField, "2, 5, fill, default");
		releaseDateTextField.setColumns(10);
		
		JLabel label = new JLabel("Director(s)");
		contentPane.add(label, "2, 6, fill, bottom");
		
		director1TextField = new JTextField();
		director1TextField.setColumns(10);
		contentPane.add(director1TextField, "2, 7, fill, default");
		
		director2TextField = new JTextField();
		director2TextField.setColumns(10);
		contentPane.add(director2TextField, "2, 8, fill, default");
		
		director3TextField = new JTextField();
		director3TextField.setColumns(10);
		contentPane.add(director3TextField, "2, 9, fill, default");
		
		splitPane = new JSplitPane();
		splitPane.setBorder(null);
		contentPane.add(splitPane, "2, 13, right, default");
		
		btnAdd = new JButton("Add");
		splitPane.setRightComponent(btnAdd);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(255, 255, 255));
		splitPane.setLeftComponent(btnCancel);
	}

	@Override
	public void run() {
		try {
			frame = new AddMovie();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}	finally{
			frame.setVisible(false);
		}
		
	}
}
