package src.controller;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import src.model.JvdbInterface;
import src.view.*;

public class MenuController {
	private JvdbInterface jvdb;
	
	public MenuController(JvdbInterface jvdb)
	{
		this.jvdb = jvdb;
	}
	public class SearchMedia implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ShowMedia sm = null;
			try {
				sm = new ShowMedia(jvdb);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(sm != null) sm.setVisible(true);
			}
		}
		
	}
	
	public class LogIn implements ActionListener
	{

		src.view.LogIn view;
		public LogIn(src.view.LogIn view)
		{
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(){
				public void run() {
					try {
						int id = jvdb.logIn(view.txtUsername.getText(), view.txtPassword.getText());
						if (id == -1)
							JOptionPane.showMessageDialog(null, "Incorrect username or password.");
						else
						{
							view.setVisible(false);
							Menu menu = new Menu(jvdb);
							menu.setVisible(true);
						}
							
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}.start();
			
		}
		
	}
	
	
	public class ShowAddMediaPerson implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			AddMediaPerson aa = new AddMediaPerson(jvdb);
			aa.setVisible(true);
		}
		
	}
	
	public class Close implements WindowListener
	{

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			try {
				jvdb.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			try {
				jvdb.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
	}
	
	public class AddMedia implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			src.view.AddMedia aa = new src.view.AddMedia(jvdb);
			aa.setVisible(true);
			
			Object o = e.getSource();
			Button b;
			
			if(o instanceof Button){
				b = (Button) o;
				b.getParent().getParent().setVisible(false);
			}
			
		}
	}
}
