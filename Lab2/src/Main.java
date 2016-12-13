package src;


import src.view.*;

import java.net.UnknownHostException;
import java.sql.SQLException;

import src.model.*;


public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, UnknownHostException {
		
		JvdbInterface jvdb = new MongoJVDB("viggolunden.com", "jvdb");
		System.out.println(jvdb.isOpen());
		LogIn li = new LogIn(jvdb);
		li.setVisible(true);
	}

}
