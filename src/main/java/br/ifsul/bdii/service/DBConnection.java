package br.ifsul.bdii.service;

import java.sql.Connection;
import java.sql.DriverManager;

public final class DBConnection {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/Gerente?useSSL=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	public static Connection openConnection() {
		try {
			Class.forName(DRIVER);
			Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
			return c;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
