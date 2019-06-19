public class Data //Class Data models a data vector
{
 public double [] position ;    // the values of that data vector
 public Data (double [] values)
 {
  position = values;
 }
 public double calculateDistance(Data d)     //this version of calculateDistance method calculates the Chebyshev distance
 {
  double rez [] = new double[d.position.length];
  for(int i =0;i<rez.length;i++)
   rez[i] = Math.abs(position[i]-d.position[i]);
  
  double max = rez[0];
  
  for(int i = 0;i<rez.length;i++)
   if(max < rez[i])
    max = rez[i];
    
  return max;   
 }
 // public double calculateDistance(Data d) //calculates the Euclidian distance between this data point and the 'd' data point
//  {
//   double sum = 0;
//   for(int i = 0;i<position.length;i++)
//    sum += Math.pow((position[i]-d.position[i]),2);
//    
//   sum = Math.sqrt(sum);  
//   
//   return sum;
//  }
}