package src.controller;

import javax.swing.JOptionPane;

class ErrorDialogue extends JOptionPane implements Runnable {
	
	ErrorDialogue(String message){
		this.message = message;
	}
	@Override
	public void run() {
		this.showMessageDialog(null, message);

	}

}
