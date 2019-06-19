import weka.core.*;
import weka.clusterers.*;
import java.io.*;
import java.util.*;
public class Test
{
 public Data[] doNothing() throws FileNotFoundException,IOException,Exception
 {
   BufferedReader read = new BufferedReader(new FileReader("C:\\Program Files\\Weka-3-8\\data\\iris.arff"));
  Instances in = new Instances(read);
  SimpleKMeans kmeans = new SimpleKMeans();
  kmeans.setSeed(30);
  kmeans.setPreserveInstancesOrder(false);
  kmeans.setNumClusters(3);
  kmeans.buildClusterer(in);
  Instances centroids = kmeans.getClusterCentroids();
  Input input = new Input("abc",4);
  Data [] data = new Data[3];// 2 is the current number of clusters
  for ( int i = 0; i < centroids.numInstances(); i++ ) {
    // for each cluster center
    Instance inst = centroids.instance( i );
    // as you mentioned, you only had 1 attribute
    // but you can iterate through the different attributes
   
   System.out.println(" Centroid: " + inst);
     
  
   data[i] = input.getData(inst+"");
  }
   return data;

  
 }

 
 public static void main (String [] args)
 {
  try
  {
    Input input = new Input("iris.txt",4);
   Data [] data = input.readDataSet();
   Data [] ic = new Test().doNothing();
   
   Particle kmeans = new Particle(data,3,10,ic);
   System.out.println("First cluster size : "+kmeans.clusters[0].size()+", second  cluster size :"
                     +kmeans.clusters[1].size()+", third cluster size : "+kmeans.clusters[2].size()+"  "+ kmeans.fitness());
  

    PSO pso2 = new PSO(0.2,0.1,0.8,"iris.txt",50,1,0.74,2,1.5,3,120);
    pso2.pso(10); 
   
 //  for(int i = 0;i<9;i++)
 //  {
//    PSO pso = new PSO("iris.txt",50,3,0.74,Math.random()*2,Math.random()*2,5,200,ic);
 //   pso.pso();
 //  }
//    System.out.println();
//    for(double a : pso.gbest)
//     System.out.print(a+" , ");
   
  }
   catch(Exception e){System.out.println(e);}
 }
}