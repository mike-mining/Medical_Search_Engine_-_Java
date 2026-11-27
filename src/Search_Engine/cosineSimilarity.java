package Search_Engine;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;

public class cosineSimilarity {
	Search search;
	
	public static void cosinesimilarity(String searchTerm) throws Exception{
		
		int k=0;
		int temp;
		double dotproduct = 0;
		double normlise1 = 0;
		double normlise2 = 0;
		double newnorm1;
		double newnorm2;
		double norm;
		ArrayList<String> Sresult = new ArrayList<String>();
		ArrayList<Double> result = new ArrayList<Double>();
		ArrayList<Integer> docID = new ArrayList<Integer>();
		ArrayList<Double> wtd = new ArrayList<Double>(); //term frequency of document wtd (values inside vectors are the term frequency of keywords)
		ArrayList<Integer> wtq = new ArrayList<Integer>();
		//double[] wtq = {1}; //term frequency of document wtq (values inside vectors are the term frequency of keywords)
		String[] termWord = searchTerm.split(" ");
		
		for(int s=0;s<searchTerm.split(" ").length;s++){	
			TermDocs df = Search.indexReader.termDocs(new Term("Content",termWord[s].toLowerCase()));//searchTerm));
			int z=0;
			while(df.next())
			{	
				double tf = (1+Math.log(df.freq()));
				double st = Search.indexSearcher.maxDoc() ; //num of all docs
				double nd = Search.topDocHits.totalHits; //num of all docs with term
				double num = st/nd;
				double idf = (tf+Math.log(1+Math.log(num)));
				double tfidf = tf*idf;
				wtq.add((int)tfidf);//searchTerm.split(" ").length);
				wtd.add(tfidf);//tf);
				Search.indexDoc = Search.indexSearcher.doc(Search.thisDoc[z].doc);
				Sresult.add(Search.indexDoc.get("Title").replace("%", "/"));
				docID.add(df.doc());
				System.out.println(Sresult.get(z)+" "+"Doc Idx Num:"+docID.get(z)+" TF:"+wtd.get(z)+" Searching:"+termWord[s]);
				System.out.println("z:"+z+" S:"+s+" split:"+searchTerm.split(" ").length);
				z++;
			}
		}

		for (int i=0;i<wtd.size();i++){
			dotproduct += wtd.get(i) * wtq.get(i);//wtq[0];
			normlise1 += wtd.get(i)*wtd.get(i);
			normlise2 += wtq.get(i)* wtq.get(i);//wtq[0]*wtq[0];
			
			System.out.println(k+": "+" wtd:"+wtd.get(i)+" wtq:"+ wtq.get(i)/*wtq[0]*/+" dot product:"+dotproduct+" normlise1:"+normlise1+" normlise2:"+normlise2);
			k++;
			
			newnorm1 = Math.sqrt(normlise1);
			newnorm2 = Math.sqrt(normlise2);
			norm = newnorm1 + newnorm2;
			result.add(dotproduct / norm);
			System.out.println(" normalise1:"+newnorm1+"\n normalise2:"+newnorm2+"\n total norm:"+norm);
			System.out.println("Cosine Similarity Score:"+result.get(i)+" Doc Inx Num:"+docID.get(i)+" :"/*+Search.indexDoc.get("Title")*/+"\n");			//all scores are between 0 & 1 (1=most similar)
			
		}
		temp = result.size();
		System.out.println("Descending Cosine Similarity Score:");
		
		for(int q=0;q<result.size();q++){
			for(int p=0; p<result.size();p++){
				if(result.get(q)<result.get(p)){
					Collections.swap(result, q, p);
					Collections.swap(Sresult, q, p);
				}
			}
		}
		
		System.out.println("\n");
		for(int q=0;q<temp;q++){
			System.out.println(q+" Similarity Score: "+result.get(q)+" :"+docID.get(q)+":"+Sresult.get(q));
			Search.theResult.add("http://"+Sresult.get(q));
		}
	}
}