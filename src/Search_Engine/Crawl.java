package Search_Engine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;

public class Crawl {

	static ArrayList<String> content;		//Website source data stored here
	static LinkedList<String> toVisit;		//Links to visit (not yet visited)
	static PriorityQueue<String> visited;	//Links visited (already visited)

	static String site = "http://www.meningitis.org";
	static URL url;
	static String thisURL;
	static int k = 0;
	static String capturedLink;
	static boolean firstTime = true;
	static String fileName;
	storage Storage;

	public Crawl() throws FileNotFoundException
	{
		content = new ArrayList<String>();
		toVisit = new LinkedList<String>();
		visited = new PriorityQueue<String>();
		crawling();
	}

	/* Read a URL/HTML file & Copy content to ArrayList */
	public static void crawling(){
		try{
			if(firstTime){
				url = new URL(site);
				thisURL = url.toString();
				firstTime = false;
			} else {
				if(visited.contains(url))
				{System.out.println("Crawler Finished");
				System.exit(0);
				}
				else{
					url = new URL(toVisit.removeFirst());
					thisURL = url.toString();
					System.out.println("New URl: "+url);
				}
			}
			Parser parser = new Parser (url.toString());
			Parser parser2 = new Parser (url.toString());
			NodeList sitelist = parser.parse (new TagNameFilter("body"));
			NodeList sitelist2 = parser2.parse (new TagNameFilter("meta"));
			for (int i=0;i<sitelist.size();i++)
			{     
				String body = (((BodyTag)(sitelist.elementAt(i))).getBody());
				String meta = (((MetaTag)(sitelist2.elementAt(i))).getMetaContent());
				//System.out.println("Meta:\n"+meta+"\nBody:\n"+body);
				content.add(meta+"\n"+body);
			}
			storage.storages();
			PrintStream pstream =new PrintStream(new FileOutputStream("webpages/"+fileName));
			pstream.println(content);
			pstream.close();
			findlink.findLinks(site);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main (String[] args) throws Exception {
		new Crawl();
	}
}
