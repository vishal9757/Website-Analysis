package com.exadatum.websiteanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LogReader {

	public static void readLog(String str) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(str));
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://35.198.199.48:3306/website_data", "vishal",
				"vishal@54");
		con.setAutoCommit(false);

		String s;
		PreparedStatement ps = con.prepareStatement("insert into customer_access_log value (?,?,?,?)");
		while ((s = br.readLine()) != null) {
			String[] split = s.split("[\t]");
			ps.setString(1, split[0]);
			ps.setString(2, split[1]);
			ps.setString(3, split[2]);
			ps.setString(4, split[3]);
			ps.addBatch();
		}
		ps.executeBatch();
		con.commit();
		br.close();
	}
}
