package jenafusekiapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class GetLabelHMDB_Wikipathways {
	public static void main(String[] args) {
		 Connection con;
		 try {  
			 Class.forName("com.mysql.jdbc.Driver").newInstance();
             String OnlineServer = "www.macs.hw.ac.uk/ia48";
             String userName = "ia48";
             String pwd = "7INB446Kle";
             con = DriverManager.getConnection("jdbc:mysql://"+OnlineServer,userName,pwd);
	    		 
             String query = "";
             //query = "Update topic SET rdfsLabel = ?, rdfsDescription = ? "
	    		// 		+ "Where IRI = ?";
             
             query = "Update new_topic SET rdfsLabel = ?"
	    		 		+ "Where IRI = ?";
             
	    	 PreparedStatement pstmt = con.prepareStatement(query);
	    	 
             File myObj = new File("out_hmdb_25_07_2020.txt");
             Scanner myReader = new Scanner(myObj);
             while (myReader.hasNextLine()) {
               String[] data = myReader.nextLine().split(",");
                 pstmt.setString(1, data[1]);
	             //pstmt.setString(2, ""); for description
	             pstmt.setString(2, data[0]);
	             pstmt.addBatch();
                 //System.out.println(data);
             }
             myReader.close();
             pstmt.executeBatch();
             System.out.println("Done");
		 }catch (Exception ex) {
		        	System.err.println(ex.getMessage());
		 }
	}
}
