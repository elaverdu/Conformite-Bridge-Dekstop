/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db.util;

/**
 *
 * @author elaverdu
 */
public class _Util {
    
    public static int countOccurrences(String str, char ch) {
    int counter = 0;
    for (int i = 0; i < str.length(); i++) {
        if (str.charAt(i) == ch) {
            counter++;
        }
    }
    return counter;
    }
    public static int distanceBetweenToString(String model, String text) throws UtilException{
        if(model==null || text==null || model.isEmpty() || text.isEmpty()){
           throw new UtilException("There are one or more params null or empty");
        }
        int tdistance[][]=new int[model.length()+1][text.length()+1];
        int ins,rep,rev,cost;
        for(int i=0;i<=model.length();i++){
            tdistance[i][0]=i;
          }
        for(int j=0;j<=text.length();j++){
             tdistance[0][j]=j;
          }
        for(int i=1;i<=model.length();i++){
          for(int j=1;j<=text.length();j++){
              cost=model.charAt(i-1)==text.charAt(j-1) ? 0 : 1;
              ins=tdistance[i-1][j]+1;
              rep=tdistance[i][j-1]+1;
              rev=tdistance[i-1][j-1]+cost;
              tdistance[i][j]=Math.min(ins, Math.min(rep,rev));     
          }
        }
        //System.out.println(model+"\t"+text +"\t"+tdistance[model.length()][text.length()]);
        /*for(int i=0;i<=model.length();i++){
          for(int j=0;j<=text.length();j++){
            System.out.print(tdistance[i][j]+"\t");
          }
          System.out.println("");
        }*/
        return tdistance[model.length()][text.length()];  
    }
   /* public static void main(String arg[]){
       try{
           int d=distanceBetweenToString("Jean","ErnstLaverdu");
           System.out.println(d);
       }catch(UtilException ex){
       
       }
    }*/
}
