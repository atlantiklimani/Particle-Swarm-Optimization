import java.util.*;
import java.io.*;
public class Input 
{
 public String path ;
 public int k;
 
 public Input (String path,int k) // k - the number of atributes of the dataset
 {
  this.path = path;
  this.k = k;
 }
 
 
 public Data [] listToArr(ArrayList<Data>  list)
 {
  Data rez [] = new Data[list.size()];
  for(int i = 0;i<list.size();i++)  
  { 
   double position [] = new double [list.get(i).position.length];
   for(int j =0;j<list.get(i).position.length;j++)
    position[j]=list.get(i).position[j];
    
   rez[i] = new Data(position); 
  }
  return rez;
 }
 
//  public Data  getData(String s)
//  {
//   double position [] = new double [k]; // the 4 is dataset related(because irsi dataset has 4 atributes)
//   int count = 0;
//   int i = 0;
//   String pos = "";
//   while(count < s.length())
//   {
//    if(s.charAt(count)==',')
//    {
//     position[i]  =new Double(pos).doubleValue();  // *mf for visualisation purposes
//     i++;
//     pos = "";
//    }
//    else 
//    {
//     pos = pos+s.charAt(count);
//    }
//    count++;
//   }
//   return new Data(position);
//  }
 
  public Data  getData(String s)
 {
  double position [] = new double [k]; // the 4 is dataset related(because irsi dataset has 4 atributes)
  int count = 0;
  String pos = "";
 
  for(int i =0;i<k;i++)
  {pos = "";
   while(s.charAt(count)!=',')
   {
    pos = pos+s.charAt(count);
    count++;
   }
   count++;
   position[i] = new Double(pos).doubleValue(); 
  } 
  return new Data(position);
 }

 
 public Data [] readDataSet () throws FileNotFoundException,IOException // the path shoudl be like : C:\\Program\\Data 
 {
  ArrayList<Data> list = new ArrayList<Data>();
  BufferedReader reader = new BufferedReader(new FileReader(path));
  int i = 0;
  String l = "";
  while((l = reader.readLine())!= null)
  {
   list.add(getData(l));
  }
  Data [] data = listToArr(list);
  return data;
  
 }
}