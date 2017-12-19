package com.exadatum.websiteanalysis;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProfileReader {

	public static void readProfile(String str) {
		try {
			File fXmlFile = new File(str);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("customer");

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://35.198.199.48:3306/website_data", "vishal",
					"vishal@54");
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("insert into cust_profile value (?,?,?,?,?)");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					ps.setString(1, eElement.getElementsByTagName("id").item(0).getTextContent());
					ps.setString(2, eElement.getElementsByTagName("name").item(0).getTextContent());
					ps.setString(3, eElement.getElementsByTagName("age").item(0).getTextContent());
					ps.setString(4, eElement.getElementsByTagName("zipcode").item(0).getTextContent());
					ps.setString(5, eElement.getElementsByTagName("gender").item(0).getTextContent());
					ps.addBatch();
				}
			}
			ps.executeBatch();
			con.commit();
			System.out.println("Inserted successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
