package Search_Engine;

import java.io.File;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Search {
	static File indexFile = new File("D:/Java Programming/Search Engine Test/index");
	static Directory index;															
	static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_35); 	//analyzer for tokenizing text used for indexing and searching

	static IndexReader indexReader;
	static QueryParser queryParser;
	static Query query;

	static IndexSearcher indexSearcher;
	static TopScoreDocCollector collector;
	static ScoreDoc[] thisDoc;
	static TopDocs topDocHits;
	static Document indexDoc;
	KMeans KMean;
	cosineSimilarity Cosine;
	tfidf tfidfa;

	static ArrayList<String> theResult = new ArrayList<String>();

	public static void search(String searchTerm) throws Exception {

		index = FSDirectory.open(indexFile);	//open the index in File System Directory

		/* query */
		int hitsPerPage = 100000;
		//String must be consistent with document String "Content" (Grabs the words from Content and filters it with stopwords, stemming etc)
		queryParser = new QueryParser(Version.LUCENE_35, "Content", analyzer); 
		query = queryParser.parse(searchTerm);

		/* search */
		collector = TopScoreDocCollector.create(hitsPerPage, true);
		indexReader = IndexReader.open(index);
		indexSearcher = new IndexSearcher(indexReader);
		indexSearcher.search(query, collector);
		thisDoc = collector.topDocs().scoreDocs;
		topDocHits = indexSearcher.search(query, 1);

		/* display results */
		System.out.println("Found " + thisDoc.length + " hits. For Search Term: "+searchTerm + " Total return for document: "+ topDocHits.totalHits+"\n");

		try{
			switch(Display.algorithm){
			case 0:tfidf.tfidfs(searchTerm);break;										//Runs TF-IDF Algorithm
			case 1:KMeans.KMean(searchTerm);break;										//Runs K-Means Algorithm
			case 2:cosineSimilarity.cosinesimilarity(searchTerm);break;					//Runs Cosine Similarity Algorithm
			}
		}catch(Exception e){e.printStackTrace();}
	}
	/*public static void lucene() throws Exception{
		for(int i=0;i<thisDoc.length;i++) {
			indexDoc = indexSearcher.doc(thisDoc[i].doc);
			System.out.println("Top Document Score: "+ topDocHits.getMaxScore());
			System.out.println((i+1) + ": "+ indexDoc.get("Title") + " Document Hit Score:" +thisDoc[i].score); //String "Title" consistent with document title.
			System.out.print(" searcher:" + indexSearcher.explain(query, thisDoc[i].doc)+"\n");
			theResult.add("http://"+indexDoc.get("Title").replace("%", "/"));
		}
	}*/
}
