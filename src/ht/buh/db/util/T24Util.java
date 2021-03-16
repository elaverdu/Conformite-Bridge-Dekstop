/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db.util;
import ht.buh.db.DbException;
import ht.buh.db.DbjServicesSql;
//import ht.buh.db.util._Util;
//import static ht.buh.db.DbjServicesSql.log;
import ht.buh.pojo.Client;
import ht.buh.pojo.Persone;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author elaverdu
 */
public class T24Util {
    
      private static void initT24Customers(){
         for(char c:Constant.ALPHAS){
             List<Client> clients=new ArrayList();
             Constant.T24Customers.put(c, clients);
         }
      }
      private static void dbT24Customers(Client param) throws DbUtilException{
          if(param==null){
             throw new DbUtilException("The param is null or Empty");  
          }
          for(char c:Constant.ALPHAS){
            String fullName=param.getFullName();
            if(fullName.toLowerCase().charAt(0)==c){
                Constant.T24Customers.get(c).add(param);
            }
          }
      }
      public static void buildTmpT24Customers() throws DbUtilException{
         initT24Customers();
          try{
            DbjServicesSql db=new DbjServicesSql();
            db.createConnection();
            db.executeQuery("FBNK.CUSTOMER ORDER BY NAME_1",new String[]{"CUSTOMER_NO,SHORT_NAME,NAME_1,LEGAL_ID"});
            java.sql.ResultSet rs=(java.sql.ResultSet)db.readNextLine();
            if(rs!=null){
               while(rs!=null){
                  Client cl=new Client();
                  cl.setNo(rs.getInt("CUSTOMER_NO"));
                  
                  cl.setFirstName(rs.getString("SHORT_NAME").trim());
                  cl.setLastName(rs.getString("NAME_1").trim());
                  cl.setFullName(cl.getFirstName().concat(" ").concat(cl.getLastName()));
                  if(cl.getFirstName().equals(cl.getLastName()))
                     cl.setFullName(cl.getFirstName());
                  cl.setLegalId(rs.getString("LEGAL_ID"));
                  dbT24Customers(cl);
                 rs=(java.sql.ResultSet)db.readNextLine();   
             }
            db.closeQuery();
            db.closeConnection();
          }
        }catch(SQLException |DbException ex){
           throw new DbUtilException(ex.toString());
        }
      }
      private static boolean searchStringName(String[] datas,Client p) throws DbUtilException{
         boolean exists=false;
         if(datas==null || datas.length==0 || p==null){
            throw new DbUtilException("There are one or many param(s) null or Empty");
         } 
         int len=datas.length;
         String fullName=p.getFullName().trim().toLowerCase();
         fullName=fullName.replaceAll("\\s+","");
         //String datas2[]=fullName.split(" ");
         switch(len){
             case 1:
                  exists=fullName.contains(datas[0]);
                  if(exists==false){
                      exists=searchString(fullName,datas[0]);
                   }
                  break;
             case 2:
                  exists=fullName.contains(datas[0]) && fullName.contains(datas[1]);
                  if(exists==false){
                      exists=searchString(fullName,datas[0].concat(datas[1]));
                  }
                 break;
             case 3:
                  exists=fullName.contains(datas[0]) && fullName.contains(datas[1])&&
                          fullName.contains(datas[2]);
                  if(exists==false){
                     exists=searchString(fullName,datas[0].concat(datas[1].concat(datas[2]))); 
                  }
                  break;
             case 4:
                 exists=fullName.contains(datas[0]) && fullName.contains(datas[1])&&
                          fullName.contains(datas[2])&& fullName.contains(datas[3]) ;
                  if(exists==false){
                    exists=searchString(fullName,datas[0].concat(datas[1].concat(datas[2]).concat(datas[3])));
                  }
                 break;
             default:
                  if(len>4){
                    exists=fullName.contains(datas[0]) && fullName.contains(datas[1])&&
                          fullName.contains(datas[2]) && fullName.contains(datas[3]) ;
                    if(exists==false){
                      exists=searchString(fullName,datas[0].concat(datas[1].concat(datas[2]).concat(datas[3])));
                    }
                  }
                 
         }
         return exists;
      }
      private static boolean searchString(String  motif,String text) throws DbUtilException{
         boolean exists=false;
         try{
             int d=_Util.distanceBetweenToString(motif, text);
             if(d<=Constant.DLEV) 
                  exists=true;
         }catch(UtilException utilException){
           throw new DbUtilException(utilException.toString());
         }
         return exists;
      }
      public static List<Client> searchPersoneFromTmpDbT24Customers(Persone param) throws DbUtilException{
          List<Client> clients=new ArrayList();
          String charTreat="";
          if(param==null){
             throw new DbUtilException("The param is null");
          }
          String fullName=param.getFullName().trim();  
          String dataString[]=fullName.toLowerCase().split("\\s+");
          for(String data:dataString){
              if(!data.isEmpty()){
                   if(charTreat.isEmpty()){
                     charTreat=charTreat+data.charAt(0);
                   }else{
                      charTreat=charTreat+data.charAt(0);
                    }
                    for(char c:Constant.ALPHAS){
                        if(data.charAt(0)==c && _Util.countOccurrences(charTreat, c)==1){
                             List<Client> customers=Constant.T24Customers.get(c);
                             boolean exists;
                             for(Client customer:customers){
                                exists=searchStringName(dataString,customer);
                               if(exists==true)
                                 clients.add(customer);
                               }
                         }
                    }
                }
          }
          
          return clients.size()> 0 ? clients : null;
      }
     /* public static void main(String arg[]){
        //System.out.println("Jovel Moise".re);
         Persone p =new Persone();
         p.setFullName("Jovenel Moise");
         p.setFirstName("Moise");
         p.setLastName("Jovenel");
        try{
             buildTmpT24Customers();
             System.out.println(Constant.T24Customers.size());
             List<Client> clients=searchPersoneFromTmpDbT24Customers(p);
             if(clients!=null){
                for(Client client:clients) 
                    System.out.println(client.getFirstName()+" "+client.getLastName());
             }
            /*Iterator<Character> keys= Constant.T24Customers.keySet().iterator();
            while(keys.hasNext()){
               Character c=keys.next();
               List<Client> clients=Constant.T24Customers.get(c);
               log.info(c.toString());
               for(Client client:clients){
                 log.info(client.getFullName());
               }
            }
             //log.info("Datas Total "+Constant.T24Customers.size());
         }catch(DbUtilException ex){
           log.info(ex.toString());
         }
      }*/
}
