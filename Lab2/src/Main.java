package src;


import src.view.*;

import java.sql.SQLException;
import src.controller.MongoJVDB;
import src.model.*;


public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		JvdbInterface jvdb = new MongoJVDB();
		LogIn li = new LogIn(jvdb);
		li.setVisible(true);
	}

}
