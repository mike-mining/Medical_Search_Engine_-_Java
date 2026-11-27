package Search_Engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;

public class KMeans {
	Search search;
	
	//static ArrayList<String> Sresult = new ArrayList<String>();
	//input: 
	static double[] x = new double[10];
	static double[] y  = new double[10];
	
	//temp x & y
	static double tempX;
	static double tempY;
	static double tempXY;

	//normalised results
	static double normalised1;
	static double normalised2;
	static double normalised3;

	//k-points
	static double[][] nKpointx = new double[10][3]; //[iteration number][data at x of point]
	static double[][] nKpointy = new double[10][3]; //[iteration number][data at y of point]

	//list of normalised results per cluster
	static double[][] nm1 = new double[10][10];
	static double[][] nm2 = new double[10][10];
	static double[][] nm3 = new double[10][10];

	static int iteration = 5;
	static boolean firstTime1 = true;
	static boolean firstTime2 = true;

	public static void KMean(String searchTerm) throws Exception{
		
		String[] termWord = searchTerm.split(" ");
		
		for(int s=0;s<searchTerm.split(" ").length;s++){
			TermDocs df = Search.indexReader.termDocs(new Term("Content",termWord[s].toLowerCase()));
			int z=0;
			
			while(df.next())
			{	
				Search.indexDoc = Search.indexSearcher.doc(Search.thisDoc[z].doc);
				double tf = (1+Math.log(df.freq()));
				double tf2 = Search.thisDoc[z].doc;
				System.out.println((z+1)+":"+Search.indexDoc.get("Title")+" Search Term:"+termWord[s]
				+"\n"+"The tf(Doc Freq:"+df.freq()+" Doc Inx Num:"+Search.thisDoc[z].doc+"): "+tf);
				x[z] = tf;
				y[z] = tf2;
				z++;
				if(z==10){break;}
			}
		}
		for(int i=0;i<x.length;i++){
		System.out.println("X: "+x[i]);
		System.out.println("Y: "+y[i]);}

		for(int l=0;l<iteration;l++){										//number of iterations

			if(firstTime1){
				for (int j=0;j<3;j++){
					Random r= new Random();
					int k = r.nextInt(x.length);
					//int[]k = {0,3,6};
					
					System.out.println(k);

					for (int i=0; i<x.length;i++){
						tempX=x[i]-x[k]; //k[j]
						tempY=y[i]-y[k]; //k[j]
						tempX = tempX*tempX;
						tempY = tempY*tempY;
						tempXY = tempX+tempY;
						double normalised = Math.sqrt(tempXY);
						switch (j){
						case 0: nm1[l][i]=normalised;normalised1 = normalised;System.out.println((i+1)+": Normalised:"+normalised1+"	co-ordinates:"+x[i]+","+y[i]+"	knum="+(k+1)+"	Cluster="+(j+1)+"	Iteration="+l);	break; // Euclidian Distance between normalised & K-Point
						case 1: nm2[l][i]=normalised;normalised2 = normalised;System.out.println((i+1)+": Normalised:"+normalised2+"	co-ordinates:"+x[i]+","+y[i]+"	knum="+(k+1)+"	Cluster="+(j+1)+"	Iteration="+l);	break; // Euclidian Distance between normalised & K-Point
						case 2: nm3[l][i]=normalised;normalised3 = normalised;System.out.println((i+1)+": Normalised:"+normalised3+"	co-ordinates:"+x[i]+","+y[i]+"	knum="+(k+1)+"	Cluster="+(j+1)+"	Iteration="+l); break; // Euclidian Distance between normalised & K-Point
						}
					}
				}firstTime1 = false;
			}else{
				for (int j=0;j<3;j++){
					for (int i=0; i<x.length;i++){
						tempX=x[i]-nKpointx[l-1][j]; //k[j]
						tempY=y[i]-nKpointy[l-1][j]; //k[j]
						tempX = tempX*tempX;
						tempY = tempY*tempY;
						tempXY = tempX+tempY;
						double normalised = Math.sqrt(tempXY);
						//System.out.println("New K points: "+nKpointx[l-1][j]+":"+nKpointy[l-1][j]+ " j"+j+"  i"+i);
						switch (j){
						case 0: nm1[l][i]=normalised;normalised1 = normalised;System.out.println((i+1)+": Normalised:"+normalised1+"	co-ordinates:"+x[i]+","+y[i]+"	knum="+(j+1)+"	Cluster="+(j+1)+"	Iteration="+l);	break; // Euclidian Distance between normalised & K-Point
						case 1: nm2[l][i]=normalised;normalised2 = normalised;System.out.println((i+1)+": Normalised:"+normalised2+"	co-ordinates:"+x[i]+","+y[i]+"	knum="+(j+1)+"	Cluster="+(j+1)+"	Iteration="+l);	break; // Euclidian Distance between normalised & K-Point
						case 2: nm3[l][i]=normalised;normalised3 = normalised;System.out.println((i+1)+": Normalised:"+normalised3+"	co-ordinates:"+x[i]+","+y[i]+"	knum="+(j+1)+"	Cluster="+(j+1)+"	Iteration="+l); break; // Euclidian Distance between normalised & K-Point
						}
					}
				}
			}

			double[] x1 = new double[10];
			double[] y1 = new double[10];
			double[] x2 = new double[10];
			double[] y2 = new double[10];
			double[] x3 = new double[10];
			double[] y3 = new double[10];
			int c1length = 0;
			int c2length = 0;
			int c3length = 0;

			for (int i=0;i<10;i++){
				if(nm1[l][i]<nm2[l][i] && nm1[l][i]<nm3[l][i]){
					System.out.println((i+1)+":	nm1<nm2 && nm1<nm3:	Cluster 1:	Normalised:"+nm1[l][i]+"	coord: x:"+x[i]+" y:"+y[i]);
					x1[l] = x1[l] + x[i];
					y1[l] = y1[l] + y[i];
					c1length++;
				}
				else if(nm2[l][i]<nm1[l][i] && nm2[l][i]<nm3[l][i]){
					System.out.println((i+1)+":	nm2<nm1 && nm2<nm3:	Cluster 2:	Normalised:"+nm2[l][i]+"	coord: x:"+x[i]+" y:"+y[i]);
					x2[l] = x2[l] + x[i];
					y2[l] = y2[l] + y[i];
					c2length++;
				}
				else if(nm3[l][i]<nm1[l][i] && nm3[l][i]<nm2[l][i]){
					System.out.println((i+1)+":	nm3<nm1 && nm3<nm2:	Cluster 3:	Normalised:"+nm3[l][i]+"	coord: x:"+x[i]+" y:"+y[i]);
					x3[l] = x3[l] + x[i];
					y3[l] = y3[l] + y[i];
					c3length++;
				}
			}
			//assign 3 new K-Points
			nKpointx[l][0] = x1[l]/c1length;
			nKpointx[l][1] = x2[l]/c2length;
			nKpointx[l][2] = x3[l]/c3length;
			nKpointy[l][0] = y1[l]/c1length;
			nKpointy[l][1] = y2[l]/c2length;
			nKpointy[l][2] = y3[l]/c3length;
			System.out.println("Cluster 1:  x:"+x1[l]+" y:"+y1[l]+"   C1 Length:"+c1length+"   New K Point x:"+nKpointx[l][0]+" y:"+nKpointy[l][0]);
			System.out.println("Cluster 2:  x:"+x2[l]+" y:"+y2[l]+"   C1 Length:"+c2length+"   New K Point x:"+nKpointx[l][1]+" y:"+nKpointy[l][1]);
			System.out.println("Cluster 3:  x:"+x3[l]+" y:"+y3[l]+"   C1 Length:"+c3length+"   New K Point x:"+nKpointx[l][2]+" y:"+nKpointy[l][2]);
			Search.theResult.add("Cluster 1:  x:"+x1[l]+" y:"+y1[l]+"   C1 Length:"+c1length+"   New K Point x:"+nKpointx[l][0]+" y:"+nKpointy[l][0]
			                 +"   Cluster 2:  x:"+x2[l]+" y:"+y2[l]+"   C2 Length:"+c2length+"   New K Point x:"+nKpointx[l][1]+" y:"+nKpointy[l][1]
			                 +"   Cluster 3:  x:"+x3[l]+" y:"+y3[l]+"   C3 Length:"+c3length+"   New K Point x:"+nKpointx[l][2]+" y:"+nKpointy[l][2]);
			if(c1length+c2length+c3length!=x.length)
			{
				System.out.println("\n\n ERROR: Empty Cluster \n\n");
				JOptionPane.showMessageDialog(null,"ERROR: Empty Cluster / Data Incomplete \nCluster Length:"+(c1length+c2length+c3length),"ERROR: Empty Cluster",JOptionPane.PLAIN_MESSAGE);
				break;
			}
			if (firstTime2==false && nKpointx[l][0]==nKpointx[l-1][0] && nKpointy[l][0]==nKpointy[l-1][0] && nKpointx[l][1]==nKpointx[l-1][1] && nKpointy[l][1]==nKpointy[l-1][1] && nKpointx[l][2]==nKpointx[l-1][2] && nKpointy[l][2]==nKpointy[l-1][2])
				{
					System.out.println("\n\n K-MEAN FINISHED \n\n");
					JOptionPane.showMessageDialog(null,"K-MEAN FINISHED","K-MEAN FINISHED",JOptionPane.PLAIN_MESSAGE);
					break;
				} else {
					iteration++;
					System.out.println("\n\n Next ITERATION \n\n");
				}
			firstTime2 = false;
		}
		firstTime1 = true; firstTime2 = true;
	}
}