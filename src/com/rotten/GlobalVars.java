package com.rotten;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class GlobalVars {
	
	public static String getAttributeValue(String element, String attribute){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			Document dom = db.parse("config.xml");
			
			System.out.println("Root element :" + dom.getDocumentElement().getNodeName());
			Element rootElement = dom.getDocumentElement();
			
			Node e = rootElement.getElementsByTagName("netflix").item(0);
			Element elem = (Element)e;
			String key = elem.getAttribute(attribute);
			return key;


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		throw new IllegalStateException("Value cannot be extracted from config file.  Ensure you have a config.xml file");
	}
	
	public static String getFlixsterKey(){
		return getAttributeValue("flixster", "key");
	}
	
	public static String getNetflixKey(){
		return getAttributeValue("netflix", "key");
	}
	
	public static String getNetflixSecret(){
		return getAttributeValue("netflix", "secret");
	}
}
