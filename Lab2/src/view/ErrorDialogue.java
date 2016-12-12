package src.view;

import javax.swing.JOptionPane;

public class ErrorDialogue extends JOptionPane implements Runnable {
	
	public ErrorDialogue(String message){
		this.message = message;
	}
	@Override
	public void run() {
		JOptionPane.showMessageDialog(null, message);

	}

}
