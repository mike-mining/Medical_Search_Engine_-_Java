package Search_Engine;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import javax.swing.JOptionPane;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Index {
	static File indexFile = new File("D:/Java Programming/Search Engine Test/index");
	static Directory index;															
	static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_35); 	//analyzer for tokenizing text used for indexing and searching
	static IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
	static IndexWriter w;
	static File dir = new File("D:/Java Programming/Search Engine Test/webpages");
	static File[] files = dir.listFiles();
	static Document doc;

	public static void main(String args[]) throws Exception {

		index = FSDirectory.open(indexFile);	//create the index and store index in File System
		  w = new IndexWriter(index,config);

		for(File file:files){

			doc = new Document();
			String path = file.getName();
			doc.add(new Field("Title", path,Field.Store.YES,Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS));

			Reader reading = new FileReader(file);
			doc.add(new Field("Content", reading, Field.TermVector.WITH_POSITIONS));

			w.addDocument(doc); System.out.println("Indexed: "+path);
		}
		w.close();
		index.close();System.out.println("Indexing Finished");
		//JOptionPane.showMessageDialog(null,"Indexing Finished","Complete",JOptionPane.PLAIN_MESSAGE);
	}
}
