package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import src.model.JvdbInterface;
import src.view.*;
import src.view.ShowAlbums;

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
			sm.show();
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
							view.hide();
							Menu menu = new Menu(jvdb);
							menu.show();
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
			ShowAlbums sa = new ShowAlbums(jvdb);
			sa.show();
			
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
			src.view.AddAlbum aa = new src.view.AddAlbum(jvdb);
			aa.show();
			
		}
	}
	public class AddMovie implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			src.view.AddMovie am = null;
			try {
				am = new src.view.AddMovie(jvdb);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			am.setVisible(true);;
			
		}
		
	}
}
