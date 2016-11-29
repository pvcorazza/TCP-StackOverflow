package domain;

import database.MySQLConnect;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MySQLConnect connect = new MySQLConnect();
		connect.connect("as","dfgbs");
	}

}
