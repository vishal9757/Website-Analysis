package com.exadatum.websiteanalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {

		try {
			// PageReader.readPage("data/pages.txt");
			// ZipcodeReader.readZipcode("data/zipcode.txt");
			// LogReader.readLog("data/log_3.txt");
			// ProfileReader.readProfile("data/customers.xml");
			query(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String TEST_QUERY = "Select * from cust_profile limit 10;";
	private static final String LOCALITY_PERCENTAGE = "SELECT cz.locality AS locality, (count(*)/ (SELECT COUNT(*) FROM customer_access_log))*100 AS percentage FROM customer_access_log AS l JOIN (SELECT c.id,z.locality  FROM cust_profile AS c JOIN zipcode AS z WHERE c.zipcode=z.zipcode) AS cz WHERE l.id=cz.id GROUP BY locality;";
	private static final String TOP_10_BY_GENDER = "SELECT l.id,c.gender, count(c.gender) as count FROM customer_access_log AS l JOIN cust_profile AS c WHERE l.id=c.id and gender like 'male' AND times>(NOW()-INTERVAL 6 DAY) group by l.id order by count desc limit 10;";
	private static final String DISTINCT_VISITORS = "select count(DISTINCT id) from customer_access_log where times>(NOW()-INTERVAL 6 DAY) ORDER BY times;";

	private static void query(int queryNum) {
		String query = "";
		switch (queryNum) {
		case 0:
			query = TEST_QUERY;
			break;
		case 1:
			query = LOCALITY_PERCENTAGE;
			break;
		case 2:
			query = TOP_10_BY_GENDER;
			break;
		case 3:
			query = DISTINCT_VISITORS;
			break;
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://35.198.199.48:3306/website_data", "vishal",
					"vishal@54");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(rsmd.getColumnName(i) + "->" + columnValue);
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
