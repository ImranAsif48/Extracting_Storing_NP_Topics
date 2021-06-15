/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jenafusekiapp;
import java.util.Scanner;

/**
 *
 * @author IMRAN ASIF
 */
public class ExtractLabels {
    public static void main(String[] args) {

        String opt = "";
        Scanner in = new Scanner(System.in);
        do{
           Menu();
           opt = in.next();
           switch(opt)
           {
               case "1":
                   //neXtProtTopic
                   break;
               case "2":
                   //ExtractWikiPathways_Label();
                   break;
               case "3":
                   //ExtractLIDDI_Label();
                   break;
               case "4":
                   //ExtractOpenBEL_Label();
                   break;
               case "5":
                   //countNP();
                   break;
           }
        }while(!opt.equals("6"));
    }
    
    static void Menu()
    {
        System.out.println("1. neXtProt Label");
        System.out.println("2. WikiPathways Label");
        System.out.println("3. LIDDI Label");
        System.out.println("4. OpenBEL Label");
        System.out.println("5. DisGeNET Label");
        System.out.println("6. Exit");
        System.out.println("--------------------------------");
        System.out.print("Please enter your option: ");
    }

    private static void ExtractOpenBEL_Label() {
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/OpenBEL_20131211/query";
        String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                     + "prefix np: <http://www.nanopub.org/nschema#>\n "
                     + "select ?np ?o where {\n "
                        + "graph ?h {\n "
                        + "?np a np:Nanopublication ;\n "
                        + "np:hasAssertion ?a\n "
                        + "}\n"
                        + " graph ?a {?s rdfs:label ?o}.\n "
                    + "}";
        ld.getLabel(serviceURI, query);
        System.out.print("Done....\n\n");
    }
    
    private static void ExtractLIDDI_Label() {
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/LIDDI_v1_01/query";
        String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                     + "prefix np: <http://www.nanopub.org/nschema#>\n "
                     + "select ?np ?o where {\n "
                        + "graph ?h {\n "
                        + "?np a np:Nanopublication ;\n "
                        + "np:hasAssertion ?a\n "
                        + "}\n"
                        + " graph ?a {?s rdfs:label ?o}.\n "
                    + "}";
        ld.getLabel(serviceURI, query);
        System.out.print("\nDone....\n\n");
    }
    
    private static void ExtractWikiPathways_Label() {
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/Wikipathways_20170510/query";
        ld.getWikiPathwaysLabel(serviceURI);
        System.out.print("\nDone....\n\n");
    }
    
    private static void ExtractDisGeNET_Description() {
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/DisGeNET_4/query";
        String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                     + "prefix np: <http://www.nanopub.org/nschema#>\n "
                     + "prefix dcterms: <http://purl.org/dc/terms/>\n"
                     + "select ?np ?o where {\n "
                        + "graph ?h {\n "
                        + "?np a np:Nanopublication ;\n "
                        + "np:hasProvenance ?prov\n "
                        + "}\n"
                        + " graph ?prov {?s dcterms:description ?o}.\n "
                    + "}";
        ld.getDescription(serviceURI, query);
        System.out.print("\nDone....\n\n");
    }
    
    private static void countNP() {
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/DisGeNET_4/query";
        String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                     + "prefix np: <http://www.nanopub.org/nschema#>\n "
                     + "prefix dcterms: <http://purl.org/dc/terms/>\n"
                     + "select (COUNT(?np) as ?count) where {\n "
                        + "graph ?h {\n "
                        + "?np a np:Nanopublication ;\n "
                        + "np:hasProvenance ?prov\n "
                        + "}\n"
                        + " graph ?prov {?s dcterms:description ?o}.\n "
                    + "}";
        ld.SimpleQuery(serviceURI, query);
        System.out.print("\nDone....\n\n");
    }
}