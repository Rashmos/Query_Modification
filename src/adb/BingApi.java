package adb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.*;


import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class BingApi {
	private String query; 
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException,NullPointerException
	{
		System.out.println("Enter query");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String query = br.readLine();
		String a = query;
		
		BingApi b = new BingApi();
		
		System.out.println("Enter the precision");
		String precision =br.readLine();
		double target_precision = Double.parseDouble(precision);
		if(target_precision<0.1 || target_precision>1.0)
		{
			System.out.println("Invalid precision, hence exiting");
			System.exit(0);
		}
	
		double trgt = target_precision*10;
		 b.parse(a,trgt);
	
			
			 
	}	
	


public void parse(String x, double pre) throws IOException, ParserConfigurationException, SAXException
{
	double target_precision = pre;
	
	String a = x;
	String z = a;
	a = a.replaceAll(" ", "+");
	String bingUrl = "https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Web?Query=%27"+a+"%27&$top=10&$format=Atom";
	//Provide your account key here. 	
	String accountKey = "ZkXZ70GtdG+AHK6qQT3oqD8xveizExX71G+hGqUFHAU=";

	byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);

		URL url = new URL(bingUrl);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);


		InputStream inputStream = (InputStream) urlConnection.getContent();		
		byte[] contentRaw = new byte[urlConnection.getContentLength()];
		inputStream.read(contentRaw);
		String content = new String(contentRaw);
		Probabilistic hash = new Probabilistic();
		ParseXml p = new ParseXml();
		p.parseXml(content);

		String Url;
		String title;
		String Desc;
		String newQuery = "";
		double count = 0;
		for(int i=0 ; i<10 ; i++)
		{
			Url = p.getURL().item(i).getFirstChild().getNodeValue();
			title = p.getTitle().item(i).getLastChild().getNodeValue();
	
			try	{
				Desc = p.getDescription().item(i).getFirstChild().getNodeValue();
				}catch (NullPointerException e)
				{
				Desc = "No Description available";
				}
	
		System.out.println("the contents of document "+ i + " is as follows:");
		String result = "URL is:            "+Url+"\n"+"Title is:          "+title+"\n"+"Description is:    "+Desc+"\n";
		System.out.println(result);
	
		System.out.println("Is the documnet Relevent or not? Yes = 'Relevent' and No = 'Not-Relevent'");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		input = input.toLowerCase();
		if(input.equalsIgnoreCase("yes"))
			{
			hash.createHash(Url,title,Desc,z);
			count++;
			}
		
	
		
		HashMap<String,Integer> r = new HashMap<String,Integer>();
		r = hash.getHashMap();
		//System.out.println(r.keySet());
		//System.out.println(r.values());
		String q = hash.calcMax();
		//System.out.println(q);
		 newQuery = z+" "+q;
		
		
		}
		System.out.println("count" + count + " target: " + target_precision);
		if(count>=target_precision)
		{
			 
			System.out.println("Reached desired precision, hence exiting");
			System.exit(0);
		}
		
		
		parse(newQuery,target_precision);
		//return newQuery;
		

	}
}
