/**
 * 
 */
package src.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


/**
 * @author jlipecki
 *
 */
public class MovieController {
	public class CloseWindow implements ActionListener{

		public void actionPerformed(ActionEvent e) {
		    final Action exit = new AbstractAction("exit") {
	
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	JFrame f = new JFrame();
					if(e.getSource() instanceof JFrame) f = (JFrame) e.getSource();
	            	
	                f.dispatchEvent(new WindowEvent(
	                    f, WindowEvent.WINDOW_CLOSING));
	            }
	        };
		}
	}
	
	public class addMovie implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
