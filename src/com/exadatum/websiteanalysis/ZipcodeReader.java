package com.exadatum.websiteanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ZipcodeReader {

	public static void readZipcode(String str) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(str));
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://35.198.199.48:3306/website_data", "vishal",
				"vishal@54");
		String s;
		while ((s = br.readLine()) != null) {
			String[] split = s.split("[\t]");
			PreparedStatement ps = con.prepareStatement("insert into zipcode value (?,?)");
			ps.setString(1, split[0]);
			ps.setString(2, split[1]);
			ps.executeUpdate();
		}
		br.close();
	}

}
