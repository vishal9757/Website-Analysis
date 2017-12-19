package com.exadatum.websiteanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PageReader {

	public static void readPage(String str) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(str));
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://35.198.199.48:3306/website_data", "vishal",
				"vishal@54");
		String s;
		while ((s = br.readLine()) != null) {
			PreparedStatement ps = con.prepareStatement("insert into pages value (?)");
			ps.setString(1, s);
			ps.executeUpdate();
		}
		System.out.println("Data entered successfully");
		br.close();

	}

}
