
 import java.util.*;
public class Particle2{
 
   public double position [];   // the position of the particle
   public double pbest []; //particles best position so far
   public double pbestfit ;
   public double lbest []; //local best position achieved so far by  particles within a specific range
   public double lbestFit ; // the fitness of the best local position
   public Data centroids []; //the centers around which the clusters will form
   public Data data [];  // the data vectors that are being clustered
   public ArrayList<Data> clusters []; 
   public double [] velocity; //the velocity of the particle
   public int n; // the number of cluster to be formed
   public double range  = 3.5;
   public boolean isNotDrawable; // if isNotDrawable is false than the particle is drawn.
  
 
   public void setRange (double range)
   { 
    this.range = range;
   } 
   public void setNotDrawableTrue()
   {
    isNotDrawable = true;
   }
   public Particle2(Data [] data,int n,int max)
   {  
      this.data = data;
      this.n = n;
      clusters = new ArrayList[n];
      int length = data[0].position.length; //the number of atributes in the dataset
      position  = new double [length];
      pbest = new double [length];
      lbest = new double [length];
      velocity = new double [length];
   
      
      for(int i=0;i<n;i++)
       clusters[i]=new ArrayList<Data>();
      
      initCentroids();
      initPosition();
      int vel = (int)(Math.random()*max);
      initVelocity(vel);
      cluster();    
      initPbest();
   }
   
   
    public void cluster(int y) //the purpose of y is simply to overload the method cluster
   {
      for(int i = 0;i< n;i++)           //creates the arraylist of the clusters array so they can be used later
         clusters[i] = new ArrayList<Data>();
   
    
      for(Data p : data)
      {
         double [] d = new double[n];
             
         for(int j = 0;j<n;j++)
            d[j] =  p.calculateDistance(centroids[j]); 
      
         double min = d[0];
         int k = 0;
      
         for(int j = 0;j<n;j++)
            if(min > d[j])
            {
               k = j;
               min = d[j];
            } 
      
         clusters[k].add(p); 
       
      }
   
   }

   
   
   public void cluster () //selects the data points that are within the 'range'
   {
    clusters[0].clear();
    
    for(Data d : data)
     if(centroids[0].calculateDistance(d) < range)
      clusters[0].add(d);
   }
   
   public void updateLbest (Particle2 [] arr) // updates the Lbest based upon the particles within a 'range' 
   {
    ArrayList<Particle2> list = new ArrayList<Particle2>();
    ArrayList<Double> fitValues = new ArrayList<Double>();
   
    
    for(Particle2 pa : arr)
     if(centroids[0].calculateDistance(pa.centroids[0])<range)
    {
     fitValues.add(pa.fitness());  
     list.add(pa);
    }
    
    if(fitValues.size() == 0)
     return;
    int index = 0;
    double min = fitValues.get(0);
    
    for(int i = 0;i<fitValues.size();i++)
     if(min>fitValues.get(i).doubleValue())
     {
      index = i;
      min = fitValues.get(i).doubleValue();     
     }  
   
        
    double position [] = new double[data[0].position.length];
    for(int i = 0;i<position.length;i++)
    {
     lbest[i] = list.get(index).position[i];
    }
     
     lbestFit = min;
    
   }
  
     public void initCentroids ()  // initializes the centroids with random data vectors
   {
    
    centroids = new Data[n];
    ArrayList <Data> list = new ArrayList<Data>();
    int count = 0;
    while(count < n)
    {
     int rand = (int)(Math.random()*data.length-0.0000001);
     
     if(list.contains(data[rand]))
      continue;
     
     list.add(data[rand]);
     
     {                                                 //This scope of code creates a Data object without copying the address of                                         
     double position [] = new double[data[0].position.length]; // the real Data object that  it is copying.
     for(int i = 0;i<data[0].position.length;i++)
      position[i] = data[rand].position[i];
     
     centroids[count] = new Data(position);
     }
      
     count++;
      
    }   
     
   } 
   public void initPosition ()
   { 
      int length = data[0].position.length;
      position = new double [length];
 
   
      for(int i = 0;i<length;i++)
      {
         for(int j =0;j<n;j++)
            position[i] += centroids[j].position[i]/n;
      }
   }
   
   public void initPbest()
   {
      for(int i = 0;i<position.length;i++)
         pbest[i]=position[i];
         
      pbestfit = fitness();   
   }
   
   public double fitness()
   {
      double sum = 0;
   
      for(int i = 0;i<n;i++)
      {
         double sum1 = 0;
         for(int j = 0;j<clusters[i].size();j++)
         {
            Data d = (Data)clusters[i].get(j);
            sum1 += d.calculateDistance(centroids[i])/clusters[i].size(); 
         }
         sum += sum1;
      }
      sum = sum/n;
      return sum;
   }

   public void updatePbest()
   {  
      if(fitness()<pbestfit)     
         for(int i = 0;i<position.length;i++)
         {
            pbest[i]= position[i];
            pbestfit = fitness();
         }
   }
   
   public void initVelocity(int max) // randomly initializes the velocity of the particle accordin to a maximum 
   {
      for(int i = 0;i<velocity.length;i++)
         velocity[i]= (Math.random()*max);
   }

   
   
}      