package src.controller;

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
	public class SearchMovies implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ShowMovies sm = new ShowMovies(jvdb);
			sm.setVisible(true);
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
	
	public class SearchAlbums implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			ShowMedia sa = new ShowMedia(jvdb);
			sa.setVisible(true);
			
		}
		
	}
	
	public class ShowAddArtist implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			AddMediaPerson aa = new AddMediaPerson(jvdb);
			aa.setVisible(true);
		}
		
	}
	
	public class ShowAddDirector implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			AddDirector ad = new AddDirector(jvdb);
			ad.setVisible(true);
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
	
	public class AddAlbum implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			AddMedia aa = new src.view.AddAlbum(jvdb);
			aa.setVisible(true);
			
		}
	}
	public class AddMovie implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			src.view.AddMovie am;
			try {
				am = new src.view.AddMovie(jvdb);
				am.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
	}
}
