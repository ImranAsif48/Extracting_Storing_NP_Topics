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
public class ExtractTopics {
//    public static void main(String[] args) {
//
//        String opt = "";
//        Scanner in = new Scanner(System.in);
//        do{
//           Menu();
//           opt = in.next();
//           switch(opt)
//           {
//               case "1":
//                   neXtProtTopic();
//                   break;
//               case "2":
//                   wikiPathwaysTopic();
//                   break;
//               case "3":
//                   liddiTopic();
//                   break;
//               case "4":
//                   openBELTopic();
//                   break;
//               case "5":
//                   disGetNETTopic();
//                   break;
//           }
//        }while(!opt.equals("6"));
//    }
    
    static void Menu()
    {
        System.out.println("1. neXtProt Topic");
        System.out.println("2. WikiPathways Topic");
        System.out.println("3. LIDDI Topic");
        System.out.println("4. OpenBEL Topic");
        System.out.println("5. DisGeNET Topic");
        System.out.println("6. Exit");
        System.out.println("--------------------------------");
        System.out.print("Please enter your option: ");
    }

    private static void neXtProtTopic() {
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/WikiPathways/query";
        String query = "prefix np: <http://www.nanopub.org/nschema#>\n" +
                                    "\n" +
                                    "select ?np where {\n" +
                                    "  graph ?h {\n" +
                                    "    ?np a np:Nanopublication .\n"+
                                    "  }\n" +
                                    "}Limit 100";
       
        //ld.getTopic(serviceURI, query);
    }
    
    private static void wikiPathwaysTopic() {
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/Wikipathways_20170510/query";
        String query = "prefix np: <http://www.nanopub.org/nschema#>\n" +
                                    "\n" +
                                    "select ?np where {\n" +
                                    "  graph ?h {\n" +
                                    "    ?np a np:Nanopublication .\n"+
                                    "  }\n" +
                                    "}";
       
        ld.getTopic(serviceURI, query, "WikiPathways", "20170510");
    }
    
    private static void openBELTopic() {
        //Liddi has no trusty uri
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/OpenBEL_20131211/query";
        String query = "prefix np: <http://www.nanopub.org/nschema#>\n" +
                                    "\n" +
                                    "select ?np where {\n" +
                                    "  graph ?h {\n" +
                                    "    ?np a np:Nanopublication .\n"+
                                    "  }\n" +
                                    "}";
       
        ld.getTopic(serviceURI, query, "OpenBEL", "20131211");
    }
    
     private static void liddiTopic() {
        //Liddi has no trusty uri
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/LIDDI_v1_01/query";
        String query = "prefix np: <http://www.nanopub.org/nschema#>\n" +
                                    "\n" +
                                    "select ?np where {\n" +
                                    "  graph ?h {\n" +
                                    "    ?np a np:Nanopublication .\n"+
                                    "  }\n" +
                                    "}";
       
        ld.getTopic(serviceURI, query, "LIDDI", "V1.01");
    }
     
     private static void disGetNETTopic() {
        //Liddi has no trusty uri
        LoadDataset ld = new LoadDataset();
        String serviceURI = "http://localhost:3030/DisGeNET_4/query";
        String query = "prefix np: <http://www.nanopub.org/nschema#>\n" +
                                    "\n" +
                                    "select ?np where {\n" +
                                    "  graph ?h {\n" +
                                    "    ?np a np:Nanopublication .\n"+
                                    "  }\n" +
                                    "}";
       
        ld.getTopic(serviceURI, query, "DisGeNET", "V4.0");
    }
}
