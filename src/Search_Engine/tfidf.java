package Search_Engine;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;

public class tfidf {
	Search search;
	
	public static void tfidfs(String searchTerm) throws Exception {
		String[] termWord = searchTerm.split(" ");
		double tfidf;
		ArrayList<String> Sresult = new ArrayList<String>();
		ArrayList<Double> Dresult = new ArrayList<Double>();
		
		for(int s=0;s<searchTerm.split(" ").length;s++){

			TermDocs df =Search.indexReader.termDocs(new Term("Content",termWord[s].toLowerCase()));
			int z=0;
			
			while(df.next())
			{	
				Search.indexDoc = Search.indexSearcher.doc(Search.thisDoc[z].doc);
				double tf = (1+Math.log(df.freq()));
				double st = Search.indexSearcher.maxDoc() ; //num of all docs
				double nd = Search.topDocHits.totalHits; //num of all docs with term
				double num = st/nd;
				double idf = Math.log(num);
				tfidf = tf*idf;
				System.out.println((z+1)+":"+Search.indexDoc.get("Title")+" Search Term:"+termWord[s]
				+"\n"+"The tf(Doc Freq:"+df.freq()+" Doc Inx Num:"+Search.thisDoc[z].doc+"): "+tf
				+"\n"+"The idf(Num of all Docs:"+st+" Num of all Docs with Term:"+nd+"): "+idf
				+"\n"+"The tf-idf score: "+tfidf+"\n");
				Dresult.add(tfidf);
				Sresult.add(Search.indexDoc.get("Title").replace("%", "/"));
				z++;
			}
		}

		for(int q=0;q<Dresult.size();q++){
			for(int p=0; p<Dresult.size();p++){
				if(Dresult.get(q)<Dresult.get(p)){
					Collections.swap(Dresult, q, p);
					Collections.swap(Sresult, q, p);
				}
			}
		}
		for(int q=0;q<Dresult.size();q++){
			System.out.println(" TF-IDF Score: "+Dresult.get(q)+"   "+Sresult.get(q));
			Search.theResult.add("http://"+Sresult.get(q));
		}
	}
}
