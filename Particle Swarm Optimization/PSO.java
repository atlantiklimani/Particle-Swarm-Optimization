import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;


public class PSO extends JPanel
{
 public Particle2 finalParticle;
 public Data [] data;
 public int n;
 public Particle [] arr;
 public Particle2 [] arr1;
 public double gbest [];
 public double w;
 public double c1;
 public double c2;
 public int max,n0,count; // n0 the number of iterations that have been performed
 public double range ;
 public double rangeLower;
 public double inertiaLower;
 public int paintCount;
 

 public PSO (String path,int generation,int n,double w ,double c1 ,double c2,int max,int n0) throws FileNotFoundException,IOException
                                                // data - the data vectors to be clustered
                                                // generation - the number of particles to be generated ,
                                                // n -the number of clusters ,w - the inertia constant ,c1 - the "cognitive" constant ,c2 - the "social" constant
                                                // max - the maximum initialized speed,n0 the number of iterations for the pso algorithm.
 { 
   JFrame frame = new JFrame();//
   frame.setSize(1000,1000);//
   frame.getContentPane().add(this);//
   frame.setVisible(true);//
   Input input = new Input(path,4);
   this.n = n;
   this.w =w;
   this.c1 =c1;
   this.c2 = c2;
   this.max = max; 
   this.n0 = n0;
   this.data = input.readDataSet();
   initParticles(generation);   
   gbest = new double [data[0].position.length];  // initialization of gbest

 }
 public PSO (String path,int generation,int n,double w ,double c1 ,double c2,int max,int n0,Data [] initialCentroids) 
                                                                                             throws FileNotFoundException,IOException
                                                // data - the data vectors to be clustered
                                                // generation - the number of particles to be generated ,
                                                // n -the number of clusters ,w - the inertia constant ,c1 - the "cognitive" constant ,c2 - the "social" constant
                                                // max - the maximum initialized speed,n0 the number of iterations for the pso algorithm.
 {
   JFrame frame = new JFrame();//
   frame.setSize(1000,1000);//
   frame.getContentPane().add(this);//
   frame.setVisible(true);//
   Input input = new Input(path,4);
   this.n = n;
   this.w =w;
   this.c1 =c1;
   this.c2 = c2;
   this.max = max; 
   this.n0 = n0;
   this.data = input.readDataSet();
   initParticles(generation,initialCentroids);   
   gbest = new double [data[0].position.length];  // initialization of gbest
     
 }

  public PSO (double rangeLower,double inertiaLower,double range,String path,int generation,int n,double w ,double c1 ,double c2,int max,int n0) throws FileNotFoundException,IOException
                                                // data - the data vectors to be clustered
                                                // generation - the number of particles to be generated ,
                                                // n -the number of clusters ,w - the inertia constant ,c1 - the "cognitive" constant ,c2 - the "social" constant
                                                // max - the maximum initialized speed,n0 the number of iterations for the pso algorithm.
 {
   Input input = new Input(path,4);
   this.n = n;
   this.w =w;
   this.c1 =c1;
   this.c2 = c2;
   this.max = max; 
   this.n0 = n0;
   this.data = input.readDataSet();
   initParticles(1,generation); // the number 1 as an argument here  is useless   
   gbest = new double [data[0].position.length];  // initialization of gbest
   JFrame k = new JFrame();
   k.setSize(1000,1000);
   k.setVisible(true);
   k.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   k.getContentPane().add(this);
   this.range = range;
   this.inertiaLower = inertiaLower;
   this.rangeLower = rangeLower;

 }

 public void initParticles(int g)  // g -the number of particles to be generated 
 {
  arr = new Particle[g];
  
  for(int i = 0;i<g;i++)
   {
    arr[i]=new Particle(data,n,max);
   }

 }

 
 public void initParticles(int fi,int g)  // g -the number of particles to be generated 
 {
  arr1 = new Particle2[g];
  
  for(int i = 0;i<g;i++)
   {
    arr1[i]=new Particle2(data,n,max);
   }

 }
 
  public void initParticles(int g,Data [] data1)  // g -the number of particles to be generated,this way of particle initialization uses 
                                                  // a pre calculated particle with another algorithm(for ex. kmeans)
 {
  arr = new Particle[g];
  
  arr[0] = new Particle(data,n,max,data1);
  
  for(int i = 1;i<g;i++)
   {
    arr[i]=new Particle(data,n,max);
   }

 }
 
 public int calculateGbest() 
 {
  double v [] = new double [arr.length];
  for(int i = 0;i<v.length;i++)
   v[i] = arr[i].fitness();
   
  double min = v[0];
  int k = 0;
  
  for(int i =0;i<v.length;i++) 
   if(min>v[i])        
   {
    min = v[i];
    k =i;  
   }
  
  for(int i =0;i<arr[k].position.length;i++)
   gbest[i]=arr[k].position[i];
    
  return k; 
   
 }
 
 public double [] sumVectors (double [] a,double [] b) 
 {
  double [] rez = new double [a.length];
  for(int i = 0;i<a.length;i++)
   rez[i]=a[i]+b[i];
  
  return rez;
 }

 public double [] subtractVectors (double [] a,double [] b) 
 {
  double [] rez = new double[a.length];
  for(int i = 0;i<a.length;i++)
   rez[i]=a[i]-b[i];
  
  return rez;
 }
 
 public double [] multiplyVectors (double [] a,double c)
 {
  double rez [] = new double [a.length];
  
  for(int i =0;i<a.length;i++)
   rez[i]= a[i]*c;
   
  return rez;
 }
 
 public void changeDirection()
 {
   for(Particle p : arr)
   {
    double [] v1 = multiplyVectors(p.velocity,w);
    double [] v2 = multiplyVectors(subtractVectors(p.pbest,p.position),(c1*Math.random()));
    double [] v3 = multiplyVectors(subtractVectors(p.gbest,p.position),(c2*Math.random()));
   
    p.velocity=sumVectors(sumVectors(v1,v2),v3);
    
    p.position = sumVectors(p.position,p.velocity);
    
    for(Data c : p.centroids)
     c.position = sumVectors(c.position,p.velocity);
   }
 }
 
 public void changeDirection(int fi)
 {
   for(Particle2 p : arr1)
   {
    double [] v1 = multiplyVectors(p.velocity,w);
    double [] v2 = multiplyVectors(subtractVectors(p.pbest,p.position),(c1*Math.random()));
    double [] v3 = multiplyVectors(subtractVectors(p.lbest,p.position),(c2*Math.random()));
   
    p.velocity=sumVectors(sumVectors(v1,v2),v3);
    
    p.position = sumVectors(p.position,p.velocity);
    
    for(Data c : p.centroids)
     c.position = sumVectors(c.position,p.velocity);
   }
 }
 
 public boolean stopCriteria (double tol) //return true if the stopping criteria is not fullfilled
 {
  boolean rez = false;
  if(arr1!=null)
  {
   for(Particle2 p : arr1)
    for(int i = 0;i<p.position.length;i++)
     if(!(p.position[i]-gbest[i]<tol))
      return true; 
  }
  else 
  {
   for(Particle p : arr)
    for(int i = 0;i<p.position.length;i++)
     if(!(p.position[i]-gbest[i]<tol))
      return true; 
  
  }
  return rez;
 }
 
 public void pso () throws InterruptedException
 {
  
  for(int i = 0;i<n0;i++) // n0 calculations of the particles
  {
   
   for(Particle p : arr)
   {
    p.cluster();
    p.updatePbest();
   
   }
 
    
   calculateGbest();
  
    
   for(Particle pa : arr)
    {pa.updateGbest(gbest);
    //   System.out.print(pa.fitness()+ "  ");
    }
   changeDirection(); 
   
   /// 
   try{Thread.sleep(100);}catch(Exception e){System.out.println(e);}
   repaint();
   count++;
   //paintCount++;
   
   
   //System.out.println(i);
   if(!stopCriteria(0.001))//
    {
     i =n0; 
     count = n0;        //
    }
  }  
   
   {
    System.out.println(count); 
    int k = calculateGbest();
    Particle temp = arr[k];
    System.out.println(temp.fitness());
    
    for(int f = 0;f<temp.centroids.length;f++)
    {

     System.out.print("Centroids : ");
     
     for(double d1 : temp.centroids[f].position)
       System.out.print(d1+" , ");
     
     System.out.print("  "+"size : "+temp.clusters[f].size());
      
     System.out.println();
    }
   
   }
  

 
 }
 public void pso (int x ) throws InterruptedException //x - has the purpose of overloading the 'pso' method.
 {
  
  for(int i = 0;i<n0;i++) // n0 calculations of the particles
  {
   
   for(Particle2 p : arr1)
   {
    p.setRange(range);
    p.cluster();
    p.updatePbest();
    
   }
 
    
  //  calculateGbest();
  
    
   for(Particle2 pa : arr1)
    pa.updateLbest(arr1);
   
   changeDirection(x); 
  
   try{Thread.sleep(10);} catch(Exception e){System.out.println(e);}
   repaint();  
   count++;//
   //paintCount++;
 
   
   if(range>rangeLower)
   range= range -0.005;
   
   if(w>inertiaLower)
    w-=0.01;
   
   System.out.println("Range : "+range+"  Inertia : "+w);
   
  }
  //  ArrayList<Data> d2  = filterParticles2(0.001,filterParticles());
//   // ArrayList<Data> d2 = filterParticles2(0.000001);///////////
//   Data finalArr [] = new Data[d2.size()];
//    for(int i = 0;i<d2.size();i++)
//     finalArr[i] = d2.get(i);
//     
//   finalParticle = new Particle2(data,d2.size(),10);
//   finalParticle.centroids = finalArr;
//   finalParticle.cluster(10); //the 10 is to use cluster method with a parameter
//   
//   for(ArrayList<Data> d : finalParticle.clusters)
//    System.out.println(d.size());
//   
//   int c = 0;                 //////
//   for(Data d : d2)                 /////////
//   {
//    System.out.println(d.position[0]+" "+d.position[1]+" "+d.position[2]+" "+d.position[3]);               ////////
//   }
 }

 public void drawData(Graphics g,int mf)
 {
  g.setColor(Color.BLACK);
  for(Data d : data)
   g.fillRect((int)(d.position[0]*mf),(int)(d.position[1]*mf),5,5);
  
 }
 
 public void drawParticle(Graphics g,int mf)
 {
  
  if(arr1!=null)
  {
  for(Particle2 pa : arr1)
   {
    if(pa.isNotDrawable)
     continue;
    
    if(pa.clusters[0].size()==0 && range > 2*rangeLower) ////do not draw those particles that are away from data points
     {
     pa.setNotDrawableTrue();
     continue;
     }             ////
    g.setColor(Color.GREEN);
    g.fillRect((int)(pa.position[0]*mf),(int)(pa.position[1]*mf),5,5);
    g.setColor(Color.RED);
    for(Data d : pa.centroids)
     g.fillRect((int)(d.position[0]*mf),(int)(d.position[1]*mf),5,5);
   }
  }
  else 
  { /////////////
  for(Particle pa : arr)
   {
    g.setColor(Color.GREEN);
    g.fillRect((int)(pa.position[0]*mf),(int)(pa.position[1]*mf),5,5);
    g.setColor(Color.RED);
    for(Data d : pa.centroids)
     g.fillRect((int)(d.position[0]*mf),(int)(d.position[1]*mf),5,5);
   }     /////////////
  g.setColor(Color.BLACK); 

  } 
  g.setColor(Color.BLACK); 
 }
 
 public void drawClusteredData(Graphics g ,int mf,int overload)
 {
  if(arr==null)
   return;
  Particle finalParticle = arr[0];
  
  for(int i =0;i<arr.length;i++)
  {
   if(finalParticle.fitness()>arr[i].fitness())
     finalParticle = arr[i];
  }
 
  g.setColor(Color.white);
  g.fillRect(0,0,1000,1000);
  Color [] colors = {Color.BLUE,Color.YELLOW,Color.PINK,Color.CYAN,Color.MAGENTA};

  for(int i = 0;i<finalParticle.clusters.length;i++)
  {
  g.setColor(colors[i]);
  for(int j =0;j<finalParticle.clusters[i].size();j++)
   g.fillRect((int)(finalParticle.clusters[i].get(j).position[0]*mf),(int)(finalParticle.clusters[i].get(j).position[1]*mf),5,5);
  
  }
 }
 
 public void drawClusteredData(Graphics g,int mf) //mf -multiplication factor
 {
  if(arr1==null)
   return;
   ArrayList<Data> d2  = filterParticles2(0.001,filterParticles());
  // ArrayList<Data> d2 = filterParticles2(0.000001);///////////
  Data finalArr [] = new Data[d2.size()];
   for(int i = 0;i<d2.size();i++)
    finalArr[i] = d2.get(i);
    
  finalParticle = new Particle2(data,d2.size(),10);
  finalParticle.centroids = finalArr;
  finalParticle.cluster(10); //the 10 is to use cluster method with a parameter
  System.out.println(finalParticle.fitness()); /////// 
  for(ArrayList<Data> d : finalParticle.clusters)
   System.out.println(d.size());
  
  int c = 0;                 //////
  for(Data d : d2)                 /////////
  {
   System.out.println(d.position[0]+" "+d.position[1]+" "+d.position[2]+" "+d.position[3]);               ////////
  }

  
  //if(finalParticle.clusters.length != n)
  // return;
   
   g.setColor(Color.WHITE);
   g.fillRect(0,0,1000,1000);
   
   Color [] color = {Color.BLUE,Color.YELLOW,Color.PINK,Color.CYAN,Color.MAGENTA,Color.GRAY,Color.LIGHT_GRAY};
   Particle2 p = finalParticle;
   
   
   for(int i = 0;i<p.clusters.length;i++)
   {
    g.setColor(color[i]);
    
    for(int j = 0;j<p.clusters[i].size();j++)
     g.fillRect((int)(p.clusters[i].get(j).position[0]*mf),(int)(p.clusters[i].get(j).position[1]*mf),5,5);
     
   //  System.out.println("Size : "+p.clusters[i].size());
    
   }
 }
 

 public void paintComponent(Graphics g)
 {
  g.setColor(Color.white);
  g.fillRect(0,0,1000,1000);
  //g.translate(500,500);
  
  g.setColor(Color.black);
  drawData(g,100);
  drawParticle(g,100);

  
  //if(paintCount==n0)       ///////////
  if(count==n0)
  {
   drawClusteredData(g,100);     //
   
   if(arr!=null)   ///
   {System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    g.setColor(Color.white);
    g.fillRect(0,0,1000,1000);
    drawData(g,100);         //////////
    int k = calculateGbest();       ///////
    Particle temp = arr[k]; ///////
    g.setColor(Color.green);           ////////
    for(Data d : temp.centroids)         //////
     g.fillRect((int)(d.position[0]*100),(int)(d.position[1]*100),5,5);     ///
     g.setColor(Color.black); ////
   }  ///
  }
  paintCount++; //// 
 
  }
  public ArrayList<Data> filterParticles2(double tol,ArrayList<Data> list)
  { System.out.println(list.size()); //////
    ArrayList<Data> rezList = new ArrayList<Data>();
   for(int i = 0;i<list.size();i++)
   {
    boolean rez = false;
    
   for(int j = 0;j<rezList.size()&&(rez == false);j++)
    {
     if(i==j)
      continue;
    
     rez = true;
     
     double [] v = subtractVectors(list.get(i).position,rezList.get(j).position);
     for(double v1 : v)
      if(Math.abs(v1)>tol)
       {
        rez = false;
        break;
       }
             
    }
    if(!rez)
      rezList.add(list.get(i));

   
   }
   //  for(int i = 0;i<p.position.length;i++)
//      if(!(p.position[i]-p.lbest[i]<tol))
//       rez = false;
//     
//     if(rez)
//      list.add(p.centroids[0]);  
     
   
   return rezList;
  }
  
  public ArrayList<Data> filterParticles () //selects for solution as many as the number specified for clusters 
  {
   ArrayList<Data> list = new ArrayList<Data>();
 Begining :
   for(Particle2 p : arr1)
    {
     if(p.isNotDrawable)
      continue Begining;
       
     int length = p.position.length;
     double position [] = new double[length];
     
     for(int i = 0;i<length;i++)
      position[i] = p.position[i];
    
     Data d1 = new Data(position);
     
     for(Data d : list)
     {
      int count = 0;
      
      for(int i = 0;i<length;i++)
       if(position[i]==d.position[i])
        count++;
      
      if(count == length)
       continue Begining; 
     }
     
     list.add(d1);
    }
    return list;
  }

}