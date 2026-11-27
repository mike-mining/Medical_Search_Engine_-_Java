package Search_Engine;

import java.io.File;
import java.io.IOException;

public class storage {
	Crawl crawl;
	public static void storages(){

		try {			
				Crawl.fileName = Crawl.url.getHost()+Crawl.url.getPath();
				//Windows OS does not like / so converted to %
				Crawl.fileName = Crawl.fileName.replace("/", "%"); 
				System.out.println("ref: "+Crawl.fileName);

			//tries to create a new file
			File savedURLs = new File("webpages/"+Crawl.fileName);

			// create file if it does not exist
			boolean success = savedURLs.createNewFile(); 

			//returns true of the new file was created
			if (success) {
				// file did not exist and was created
			} else {
				System.out.println("file "+Crawl.fileName+" already exists");
				Crawl.crawling();
			}
		} catch (IOException e) { System.out.print(e);}
	}
}
