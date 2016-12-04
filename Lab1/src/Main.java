package src;


import src.view.*;
import java.sql.SQLException;
import java.util.List;

import src.model.*;


public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		
		JvdbInterface jvdb = new JVDB();
		Menu menu = new Menu(jvdb);
		menu.show();
	}

}
