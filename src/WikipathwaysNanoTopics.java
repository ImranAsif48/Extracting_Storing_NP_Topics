package jenafusekiapp;

import org.nanopub.*;
import org.nanopub.op.topic.WikipathwaysTopics;

import java.net.URL;
import java.sql.*;

public class WikipathwaysNanoTopics {

	private static Connection con;
	private static String runningServer = "http://server.nanopubs.lod.labs.vu.nl/";
	
	public static void main(String[] args) {
		ResultSet rs = getAllWikipathwaysNanoHash();
		try {
			while (rs.next()) {
				String hash = rs.getString("hash");
				String npTrusty = runningServer + hash;
				WikipathwaysTopics wpTopic = new WikipathwaysTopics();
				URL url = new URL(npTrusty);
                NanopubImpl np = new NanopubImpl(url);
				String topic = wpTopic.getTopic(np);
				String t = "";
			}
			closeConnection();
		 } catch (Exception ex) {
	         System.out.println(ex.getMessage());
	    }
	}

	private static ResultSet getAllWikipathwaysNanoHash() {
	    openConnection();
	    String query = "SELECT * FROM nanopubs\n" + 
	    					"WHERE dataset = 'wikipathways' LIMIT 100\n";
	    
	    ResultSet rs = null;
	    try {
		    java.sql.Statement stmt;  
		    stmt = con.createStatement();
	        rs = stmt.executeQuery(query);  
	    } catch (Exception ex) {
	         System.out.println(ex.getMessage());
	    }
	    
	    return rs;
	}
	
	private static void openConnection()
	{
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String OnlineServer = "www.macs.hw.ac.uk/ia48";
            String userName = "ia48";
            String pwd = "7INB446Kle";
            con = DriverManager.getConnection("jdbc:mysql://"+OnlineServer,userName,pwd);
            
      } catch (Exception ex) {
         System.out.println(ex.getMessage());
      }
	}
	
	private static void closeConnection()
	{
		try {
            con.close();
      } catch (Exception ex) {
         System.out.println(ex.getMessage());
      }
	}
}
