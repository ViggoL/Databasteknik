
import view.*;
import java.sql.SQLException;
import java.util.List;

import model.*;


public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		JVDB jvdb = new JVDB();
		Albums a = new Albums(jvdb);
		a.show();
	}

}
