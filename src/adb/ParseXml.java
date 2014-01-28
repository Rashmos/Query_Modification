package adb;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParseXml {
	NodeList Title;
    NodeList Description;
    NodeList URL;
	
	public NodeList getTitle() {
		return Title;
	}

	public void setTitle(NodeList title) {
		Title = title;
	}

	public NodeList getDescription() {
		return Description;
	}

	public void setDescription(NodeList description) {
		Description = description;
	}

	public NodeList getURL() {
		return URL;
	}

	public void setURL(NodeList uRL) {
		URL = uRL;
	}

	public void parseXml(String xml) throws ParserConfigurationException, SAXException, IOException
	{
        
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is =new InputSource();

		is.setCharacterStream(new StringReader(xml));

		Document doc = db.parse(is);

		doc.getFirstChild();

		
		URL = doc.getElementsByTagName("d:Url");
		//System.out.println("URL is:  "+URL.item(0).getFirstChild().getNodeValue());
		
		Title = doc.getElementsByTagName("d:Title");
		//System.out.println("Title is:  "+Title.item(0).getLastChild().getNodeValue());
			
		Description= doc.getElementsByTagName("d:Description");
		//System.out.println("Description is:  "+Description.item(0).getFirstChild().getNodeValue());
		

			
		}

}
