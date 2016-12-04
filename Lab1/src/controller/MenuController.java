package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			src.view.AddMovie am = new src.view.AddMovie(jvdb);
			am.show();
			
		}
		
	}
}
