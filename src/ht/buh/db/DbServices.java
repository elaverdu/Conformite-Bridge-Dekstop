/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db;
import java.sql.PreparedStatement;
import java.sql.Statement; 
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet; 
import ht.buh.db.util.DbUtil;
import ht.buh.db.util.DbUtilException;
import java.util.Map; 
import java.util.Iterator;
abstract class DbServices {
     protected Connection con;
     protected ResultSet rs;
     protected Statement stmt;
     protected PreparedStatement pstmt;
     abstract void createConnection(String source,String ip,String user,String password,String agent) throws DbException;
     abstract void createConnection()throws DbException;
    public void executeQuery(String param) throws DbException{
         try{
             this.stmt=con.createStatement();
            String query=DbUtil.buildQuery(param);
             rs=stmt.executeQuery(query);
          }catch(DbUtilException | SQLException ex){
             throw new DbException("Exception come from executeQuery ".concat(ex.toString()));
          }
    }
    public void executeQueryj(String table,String[] fields,Map<String,String> params) throws DbException{
        try{
            this.stmt=con.createStatement();
            String query=DbUtil.buildQueryj(table, fields, params);
           
            rs=stmt.executeQuery(query);
        }catch(SQLException | DbUtilException ex){
          throw new DbException("Exception come from executeQueryJ".concat(ex.toString()));   
        }
    }
    public void executeQuery(String[] tables,String[]fields,Map<String,?> params) throws DbException{
       if(null==tables || tables.length==0 || null==fields || fields.length==0
          || null==params || params.isEmpty()){
           throw new DbException("There are one or many param(s) null or empty");
       } 
       
    } 
    public void executeQuery(String query,int id) throws DbException{
       // System.out.println(query);
        try{
            this.pstmt=con.prepareStatement(query);
            System.out.println(query);
            this.pstmt.setInt(1,id);
            System.out.println(query);
            rs=this.pstmt.executeQuery();
            System.out.println(query);
          }catch(SQLException ex){
            throw new DbException("Exception come from executeQuery ".concat(ex.toString()));
          }
       // System.out.println(query);
    }
     public void executeQuery(String query,String id) throws DbException{
       System.out.println(query+"====="+id);
        try{
            this.pstmt=con.prepareStatement(query);
            System.out.println(query);
            this.pstmt.setString(1,id);
            System.out.println(query);
            rs=this.pstmt.executeQuery();
            //System.out.println(query);
          }catch(SQLException ex){
            throw new DbException("Exception come from executeQuery ".concat(ex.toString()));
          }
       // System.out.println(query);
    }

    public void executeQuery(String table,String[]fields) throws DbException{
         try{
            stmt=con.createStatement();
            String query=DbUtil.buildQuery(table,fields);
            rs=stmt.executeQuery(query);
         }catch(DbUtilException | SQLException ex){
            throw new DbException("Exception come From executeQuery ".concat(ex.toString()));
         }
    }
    public void executeQuery(String table,String[] fields,Map<String,?> params) throws DbException{
         try{
             String query=DbUtil.buildQuery(table,fields, params);
             pstmt=con.prepareStatement(query);
             Iterator it=params.entrySet().iterator();
             int i=1;
             while(it.hasNext()){
                   Object key=it.next();
                   Object obj=params.get((String)key);
                   if(obj instanceof Integer){
                     pstmt.setInt(i,(Integer)obj);
                   }else if(obj instanceof String){
                     pstmt.setString(i,(String)obj);
                   }else if(obj instanceof Float){
                     pstmt.setFloat(i,(Float)obj);
                   }else if(obj instanceof Long){
                     pstmt.setLong(i,(Long)obj);
                   }else if(obj instanceof Double){
                     pstmt.setDouble(i,(Double)obj);
                   }else if(obj instanceof Character){
                     pstmt.setByte(i,(byte)obj);
                   }else{
                     throw new DbException("Exception come from executeQuery type invalide");
                   }
                 i++;
             }
             rs=pstmt.executeQuery();
         }catch(DbUtilException | SQLException ex){
             throw new DbException("Exception come from executeQuery ".concat(ex.toString()));
         }
    }
    public void  closeQuery() throws DbException{
        if(rs==null){
            throw new DbException("ResulSet was not instantiate");
        }
        try{
           rs.close();
        }catch(SQLException ex){
           throw new DbException(ex.toString());
        }      
    }
    public void closeConnection() throws DbException{
        if(con==null){
            throw new DbException("Connection was not initiate");
        }
        try{
          con.close();
        }catch(SQLException ex){
            throw new DbException(ex.toString());
        }
    }
    public Object readNextLine() throws DbException{
       ResultSet _rs=null;
        if(rs==null){
            throw new DbException("Connection was not instantiate");
        }
        try{
            _rs= rs.next() ? rs : null;
           }catch(SQLException ex){
               throw new DbException("Impossible to get the resultset ".concat(ex.toString()));
            }
        return _rs;
    }
}