package Search_Engine;

import org.htmlparser.Parser;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

public class findlink {
	Crawl crawl;
	static String ignoreSite = ".*(?=facebook|twitter|flickr|career|wikipedia|sitemap).*$";		
	//The regular expression will ignore matches containing (facebook|twitter|flickr|career)
	static String ignoreEnd = ".*(mov|jpg|gif|pdf|zip|rar)$"; 			//The regular expression will ignore matches ending in (mov|jpg|gif|pdf|zip|rar)
	
	public static void findLinks(String url){
		try{
			/* Find Links Method */
			Parser parser = new Parser (url);
			NodeList sitelist = parser.parse (new LinkStringFilter("http:"));

			for (int i=0;i<sitelist.size();i++)
			{     
				Crawl.capturedLink = (((LinkTag)(sitelist.elementAt(i))).extractLink());
				if(!Crawl.capturedLink.matches(ignoreEnd)&&!Crawl.visited.contains(Crawl.capturedLink)&&!Crawl.capturedLink.matches(ignoreSite)){
					//if link does not contain ignore sites, ignore file types and is not contained in queue then do below 

					//Linked List is a data structure, efficient at inserting/removing from the beginning or end of a list/queue
					Crawl.toVisit.add(Crawl.capturedLink); //Linked List

					//Priority Queue(Heap) is a data structure, efficient at inserting/removing O(log n). Heapsort worst-case O(n log n).
					Crawl.visited.add(Crawl.thisURL.toString()); //Priority Queue(Heap)
					Crawl.k++;
					System.out.println(Crawl.k+" added to visit  :"+Crawl.capturedLink);
					System.out.println(Crawl.k+" added to visited:"+Crawl.thisURL.toString());
					Crawl.crawling();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
