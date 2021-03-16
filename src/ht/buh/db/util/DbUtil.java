/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db.util;

import java.util.Map;
import java.util.Iterator;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
public class DbUtil{
     
    public static String buildUrlDbS(String preUrl,String ip,String port,String source,String user,String pass ) throws DbUtilException{
      if ((preUrl==null || preUrl.isEmpty())||
         (ip==null || ip.isEmpty()) ||
         (source==null || source.isEmpty())
         ){
            throw new DbUtilException("There are one or many param(s) null or Empty"); 
         }
         String url=preUrl+ip+";database="+source+";user="+user+";password="+pass;
         if(port!=null && !port.isEmpty()){
            url=preUrl+ip+":"+port+";database="+source+";user="+user+";password="+pass;
         }
         return url;
    }
    public static String buildUrlDbJ(String preUrl,String ip,String port,String source ) throws DbUtilException{
        if ((preUrl==null || !preUrl.isEmpty())||
           (ip!=null && !ip.isEmpty()) ||
           (port!=null && !port.isEmpty()) ||
           (source!=null && !source.isEmpty())
           ){
              throw new DbUtilException("There are one or many param(s) null or Empty"); 
           }
           return preUrl+ip+":"+port+":"+source;
      }
    public static String buildQuery(String table) throws DbUtilException{
        if(table==null || table.isEmpty()){
            throw new DbUtilException("There are one or many param(s) null or empty");
        }
        return "SELECT *FROM "+table;
    }
    public static String buildQueryj(String table) throws DbUtilException{
        if(table==null || table.isEmpty()){
            throw new DbUtilException("There are one or many param(s) null or empty");
        }
        return "SELECT FROM "+table;
    }
    public static String buildQueryj(String table,String fields[],Map<String,String> params) throws DbUtilException{
      if(null==table || table.isEmpty() || 
         null==fields || fields.length==0 || 
         null==params || params.isEmpty()){
          throw new DbUtilException("There are one or many param(s) null or empty");    
      }
      String query="";
      for(String field:fields){
         query=query.isEmpty() ? "SELECT ".concat(field): query.concat(",").concat(field) ;
      }
      query=query.concat(" FROM ").concat(table).concat(" WHERE ");
      Iterator it=params.keySet().iterator();
      String filter="";
      while(it.hasNext()){
         Object key=it.next();
         Object value=params.get(String.valueOf(key));
         System.out.println(key);
         filter=filter.isEmpty() ? filter.concat(String.valueOf(key)).concat(" LIKE \"%").concat(String.valueOf(value)).concat("%\" ") :
                   filter.concat("AND ").concat(String.valueOf(key)).concat(" LIKE \"%").
                    concat(String.valueOf(value)).concat("%\"");  
      }
      query=query.concat(filter);
      return query;
    }
    public static String buildQuery(String table,String[] fields) throws DbUtilException{
        if(table==null || table.isEmpty() || fields==null || fields.length==0){
            throw new DbUtilException("There are one or many param(s) null or empty");
        }
        String query="";
        for(String field:fields){
            if(query.isEmpty()){
                query=query.concat("Select ").concat(field);
            }else{
                query=query.concat(",").concat(field);
            }
        }
        return !query.isEmpty() ? query.concat(" from ").concat(table) : null;
    }
    public static String buildQuery(String table,String[]fields,Map<String,?> params) throws DbUtilException{
         if(null==table ||table.isEmpty() || null==params || params.isEmpty() ){
              throw new DbUtilException("There are one or many param(s) null or empty");
         }
          String query=buildQuery(table,fields);
         if(query==null){
             throw new DbUtilException("pre query is null impossible to continue");
         }
         query=query.concat("where ");
         Iterator<?>it=params.keySet().iterator();
         while(it.hasNext()){
               Object key=it.next();
               Object value=params.get(String.valueOf(key));
               query=query.concat(String.valueOf(value)).concat("= ? ");
               if(it.hasNext()){
                   query=query.concat(" and ");
               }
         }
         return query;
    }
    
    public static  Properties createProperties(InputStream in) throws DbUtilException{
         Properties prop=null;
         if(in==null){
             throw new DbUtilException("The param of this method is null");
          }
          try{
             prop=new Properties();
             prop.load(in);
          }catch(IOException ex){
             throw new DbUtilException(ex.toString());
          }
       return prop;
    }
    
}