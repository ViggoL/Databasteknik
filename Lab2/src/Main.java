package src;


import src.view.*;

import java.net.UnknownHostException;
import java.sql.SQLException;

import src.model.*;


public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, UnknownHostException, InterruptedException {
		
		JvdbInterface jvdb = new MongoJVDB("db");
		LogIn lokalServer = new LogIn(jvdb);
		lokalServer.setVisible(true);
		
		Thread.sleep(10000);
		
		
		jvdb = new MongoJVDB("viggolunden.com", "jvdb");
		LogIn li = new LogIn(jvdb);
		li.setVisible(true);
	}

}
