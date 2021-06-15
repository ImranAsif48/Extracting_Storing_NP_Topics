/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jenafusekiapp;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.nio.charset.Charset;


import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.JSONArray;

import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;

/**
 *
 * @author ia48
 */
public class ExtractLabelFromAPI {
    
    public static String label;
    public static String description;
    public static ChebiWebServiceClient client;

    public void setLabel(String l)
    {
        label = l;
    }
    
    public void setDescription(String des)
    {
        description = des;
    }
    
    public String GetLabel()
    {
        return label;
    }
    
    public String GetDescription()
    {
        return description;
    }
    
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
   }
  
    //The following method is used to fetch label from nextprot/uniprot
   public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      description = json.getJSONObject("entry").getJSONObject("overview").getJSONObject("proteinExistence").getString("description");
      label = json.getJSONObject("entry").getJSONObject("overview").get("mainProteinName").toString();
      //label = json.getJSONObject("entry").getJSONObject("overview").get("mainProteinName").toString() + " ( " + json.getJSONObject("entry").getJSONObject("overview").get("mainGeneName").toString() + ")";
      return json;
    } finally {
      is.close();
    }
  }
  
   // The following method is used to fetch label from ensemble
   
   public static void readJsonFromUrlEnsembl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      description = json.getString("description");
	      label = json.getString("display_name");
	      
	    } finally {
	      is.close();
	    }
	  }
   
   // The following method is used to fetch label from ncbigene
   public static void readRDFFromUrlNCBIGENE(String url, String IRI) throws IOException {
	   InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      JSONArray map = (JSONArray) json.getJSONObject(IRI).get("http://purl.org/dc/terms/description");
	      JSONObject jObj = (JSONObject) map.get(0);
	      description = "";//jObj.getString("value");
	      label = jObj.getString("value");
	      
	    } finally {
	      is.close();
	    }
 }
   
   public static void readRDFFromUrlWormbase(String url, String Id) throws IOException {
	   InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      String IRI = "http://bio2rdf.org/wormbase:"+Id;
	      JSONArray map = (JSONArray) json.getJSONObject(IRI).get("http://purl.org/dc/terms/title");
	      JSONObject jObj = (JSONObject) map.get(0);
	      description = "";//jObj.getString("value");
	      label = jObj.getString("value");
	      
	    } finally {
	      is.close();
	    }
 }
   
   public static void Init_CHEBIServe()
   {
	   // Create client
       client = new ChebiWebServiceClient();
   }
   
   public static void readCHEBIWebService(String chebiID) throws IOException, ChebiWebServiceFault_Exception {
	
       Entity entity = client.getCompleteEntity(chebiID);
       label = entity.getChebiAsciiName();
       description = entity.getDefinition();
 }
   
   public static void webScrapFromURL(String url) throws IOException {
	   Document doc = Jsoup.connect(url).get();
	   
	   // For Wikipathways page
	   //label = doc.select("h1#pageTitle").text().split("\\(")[0].trim();
	   //description = doc.select("div#descr").text();
	   
	    //For mirebase.muture and mirbase
	   //label = doc.select("tr.sectionTitle td h2").first().text();
	   //description = "";
	   
	   //For pubchem.compound
	   //label = doc.select("meta[property=og:title]").attr("content");
	   //description = "";
	   
	   // For RGD
	   //label = doc.select("td.label").eq(1).next().text();
	   //description = doc.select("td.label").eq(2).next().text();
	   
	   // For tair locus
	   //label = doc.select("#content > table:nth-of-type(2) > tbody > tr:nth-of-type(3) > td:nth-of-type(2) > table > tbody > tr > td").text();
	   //label = label.replace("\"", "").replaceFirst(",", "");
	   //description = "";
	   
	   // For reactome
	   //label = doc.select("#fav-lead1 > div > div > div > h3").text().trim();
	   //description = "";
	   
	   //For ncbi gene
	   label = doc.select("#summaryDl > dd:nth-of-type(2)").text().trim();
       label = label.split("provided")[0].trim();
       description = "";
	 }
}
