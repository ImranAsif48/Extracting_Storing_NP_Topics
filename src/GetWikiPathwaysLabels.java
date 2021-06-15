package jenafusekiapp;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.jena.sparql.function.library.execTime;
import org.nanopub.op.Extract;

public class GetWikiPathwaysLabels {

	public static void main(String[] args) {
		 Connection con;
		 try {  
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String OnlineServer = "www.macs.hw.ac.uk/ia48";
                String userName = "ia48";
                String pwd = "7INB446Kle";
                con = DriverManager.getConnection("jdbc:mysql://"+OnlineServer,userName,pwd);
                
                String query = "";
            	
//            	query = "SELECT t.IRI, COUNT(t.IRI) as GroupCount FROM topic t INNER JOIN nanopubs np on "
//            			+ "t.npId = np.npId WHERE np.dataset = 'wikipathways' AND t.IRI "
//            			+ "LIKE 'http://identifiers.org/rgd%'\n" + 
//            			"GROUP BY t.IRI";
                
                String datasource = "ncbigene";
                query = "SELECT IRI, COUNT(IRI) as GroupCount FROM new_topic \n" + 
                		"	WHERE dataset = 'disgenet' AND IRI LIKE 'http://identifiers.org/"+datasource+"/%' AND rdfsLabel is null\n" + 
                		"GROUP BY IRI";
            
                 
                 //java.sql.Statement stmt;
                 java.sql.CallableStatement stmt;
                 ResultSet rs = null;
        	    	 stmt = con.prepareCall(query);
        	    	 rs = stmt.executeQuery();  
        	    	 String queryUpdate = "Update new_topic SET rdfsLabel = ?, rdfsDescription = ? "
     	    		 		+ "Where IRI = ?";
     	    		 PreparedStatement pstmt = con.prepareStatement(queryUpdate);
     	    		 //For Chembi
     	    		 //ExtractLabelFromAPI.Init_CHEBIServe();
     	             
     	    		 int count = 0;
     	             System.out.println("Get data from database. Now fetch labels.");
        	    	 while (rs.next()) {
        	    		 String IRI = rs.getString("IRI");
        	    		 count += rs.getInt("GroupCount");
        	    		 try
        	    		 { 
            	    		 String id = rs.getString("IRI").split("/")[4];
            	    		 ///////////////////////////////////////////////////////////////////////////////
            	    		 // For Wikipathways, mirebase.muture and mirbase, pubchem.compound, For RGD
            	    		 //String url = "https://rgd.mcw.edu/rgdweb/report/gene/main.html?id="+id;
            	    		 //String url = "https://www.wikipathways.org/instance/" + id;
            	    		 //String url = "http://www.mirbase.org/cgi-bin/mirna_entry.pl?acc="+id;
            	    		 //String url = "http://www.mirbase.org/cgi-bin/mature.pl?mature_acc="+id;
            	    		 //String url = "https://pubchem.ncbi.nlm.nih.gov/compound/"+id;
            	    		 //ExtractLabelFromAPI.webScrapFromURL(url);
            	    		 
            	    		 //For Chembi
            	    		 //ExtractLabelFromAPI.readCHEBIWebService(id);
            	    		 
            	    		 //For uniprot, nextprot
            	    		 //String url = "https://api.nextprot.org/entry/"+id+"/overview.json";
            	    		 //ExtractLabelFromAPI.readJsonFromUrl(url);
            	    		 
            	    		 //For ensembl
            	    		 //String url = "http://rest.ensembl.org/lookup/id/"+id+"?content-type=application/json";
            	    		 //ExtractLabelFromAPI.readJsonFromUrlEnsembl(url);
            	    		 
            	    		 //For ncbigene
            	    		 //String url =  "https://bio2rdf.org/sparql?query=define%20sql%3Adescribe-mode%20%22CBD%22%20%20DESCRIBE%20%3Chttp%3A%2F%2Fbio2rdf.org%2Fncbigene%3A"+id+"%3E&output=application%2Frdf%2Bjson"; 
            	    		 String url = "https://www.ncbi.nlm.nih.gov/gene/" + id;
            	    		 ExtractLabelFromAPI.webScrapFromURL(url);
            	    		 
            	    		 //For wormbase
            	    		 //String url = "https://bio2rdf.org/sparql?query=define%20sql%3Adescribe-mode%20%22CBD%22%20%20DESCRIBE%20%3Chttp%3A%2F%2Fbio2rdf.org%2Fwormbase%3A"+id+"%3E&output=application%2Frdf%2Bjson";
            	    		 //ExtractLabelFromAPI.readRDFFromUrlWormbase(url, id);
            	    		 
            	    		 //For tair.locus
            	    		 //String url = "https://www.arabidopsis.org/servlets/TairObject?name="+id+"&type=locus";
            	    		 //ExtractLabelFromAPI.webScrapFromURL(url);
            	    		 
            	    		 //For reactome
            	    		 //String url = "https://reactome.org/content/detail/"+id+"#Homo%20sapiens";
            	    		 //ExtractLabelFromAPI.webScrapFromURL(url);
            	    		 ////////////////////////////////////////////////////////////////////////
            	    		 String label = ExtractLabelFromAPI.label;
            	    		 pstmt.setString(1, label);
            	             pstmt.setString(2, ExtractLabelFromAPI.description);
            	             pstmt.setString(3, IRI);
            	             //pstmt.addBatch();
            	             pstmt.executeUpdate();
        	    		 }
        	    		 catch (Exception ex) {
        	 	        	System.err.println(ex.getMessage() + " for IRI: " + IRI);
        	 	        	FileWriter writer = new FileWriter("NotFoundLabels_"+ datasource +".txt", true);
        	 	        	writer.write(""+IRI);
        	 	        	writer.write("\r\n");   // write new line   
        	 	        	writer.close(); 
        	    		 }
        	    	 }
        	    	 
        	    	 //pstmt.executeBatch();
        	    	 System.out.println(count + " Done...");
            
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        catch (SQLException ex) {
        	System.err.println(ex.getMessage());
        }
		catch (Exception ex) {
	        	System.err.println(ex.getMessage());
	    }
	}

}
