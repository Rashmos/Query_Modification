package adb;

import java.util.*;

public class Probabilistic
{
private HashMap<String,Integer> hashmap;
private StringTokenizer url;
private StringTokenizer title;
private StringTokenizer desc;
private String stop[]= {"a","able","about","across","after","all","almost","also","am","among","an","and","any","are","as","at",
	"be","because","been","but","by","can","cannot","could","dear","did","do","does","either","else","ever","every","for","from","get","got",
	"had","has","have","he","her","hers","him","his","how","however","i","if","in","into","is","it","its","just","least","let","like","likely",
	"may","me","might","most","must","my","neither","no","nor","not","of","off","often","on","only","or","other","our","own","rather","said","say","says",
	"she","should","since","so","some","than","that","the","their","them","then","there","these","they","this","tis","to","too","twas","us","wants","was",
	"we","were","what","when","where","which","while","who","whom","why","will","with","would","yet","you","your","0","1","2","3","4","5","6","7","8","9",
	"a","b","c","d","e","f","g","h","i","j","encyclopedia","http","www","com","wikipedia","wiki","free","news","headline","amazon","ebay"};
private List<String> stopword = Arrays.asList(stop);

Probabilistic()
{
hashmap = new HashMap<String,Integer>();

}

	public void createHash(String a, String b, String c, String d)
	{
		String Query = d.toLowerCase();
		String[] split = Query.split(" ");
		
		List<String> query = Arrays.asList(split);
		
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		String Url = a.toLowerCase();
		String Title = b.toLowerCase();
		String Desc = c.toLowerCase();
		String delims = "[ ?<>;/://,''-.!|_-]+()[]&@%";
		url = new StringTokenizer(Url,delims);
		title = new StringTokenizer(Title,delims);
		desc = new StringTokenizer(Desc,delims);
	
		
		while(url.hasMoreTokens())
	
        {
        	String x= url.nextToken();
        	
        	if(stopword.contains(x) || query.contains(x))
        		flag1 = false;
        	if(hashmap.containsKey(x) && flag1)
        	{	
        		int p = hashmap.get(x);
        		p+=1;
        		hashmap.put(x,p);
        	}
        	else if (flag1)
        		hashmap.put(x,1);
        	flag1=true;
        }
        
        while(title.hasMoreTokens())
        {
        	String x= title.nextToken();

        	if(stopword.contains(x) || query.contains(x))
        		flag2 = false;
        	if(hashmap.containsKey(x) && flag2)
        	{	
        		int p = hashmap.get(x);
        		p+=2;
        		hashmap.put(x,p);
        	}
        	else if (flag2)
        		hashmap.put(x,2);
        	flag2=true;
        }
        
        while(desc.hasMoreTokens())
        {
        	String x= desc.nextToken();
        	if(stopword.contains(x) || query.contains(x))
        		flag3 = false;
        	if(hashmap.containsKey(x) && flag3)
        	{	
        		int p = hashmap.get(x);
        		p+=1;
        		hashmap.put(x,p);
        	}
        	else if (flag3)
        		hashmap.put(x,1);
        	flag3=true;
        }
	
	
	}
	
	public HashMap<String,Integer> getHashMap()
	{
		
		return hashmap;
	}
	
	public String calcMax()
	{
		int max = 0;
		String str="";
		Iterator<Integer> x = hashmap.values().iterator();
		Iterator<String> z = hashmap.keySet().iterator();
			for(int i=0;i<hashmap.size();i++)
			{
				String s=z.next(); 
				int y=x.next();
				if(y>max)
					{
						max=y;
						str=s;
					}
						
			}
				
		return str;
	}
	
	
}