/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.dao;
import ht.buh.db.DbException;
import ht.buh.db.DbjServicesSql;
import ht.buh.pojo.Category;
import ht.buh.pojo.CategoryVersion;
import ht.buh.pojo.Client;
import ht.buh.pojo.Persone;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
/**
 *
 * @author elaverdu
 */
public class JbDao implements BuhDao,Serializable {
        private final static Logger log=Logger.getLogger(JbDao.class.getName());
        private DbjServicesSql dbj;
        public static JbDao newInstance(){
           return new JbDao();
        }
        @Override
       public void initConnect() throws DaoException{
         dbj=new DbjServicesSql();
           try{
               dbj.createConnection();
               }
            catch(DbException db){
                throw new DaoException(db.toString());
               }        
       }
       @Override
       public List<Category> listCategories() throws DaoException{
          return null;
       }
       @Override
       public List<Persone> listPersones(String param) throws DaoException{
          if(null==param || param.isEmpty()){
             throw new DaoException("Exception come from listpPersones There are one or param(s) null or empty ");
          }
          log.info(param);
          List<Persone> clients=null;
          try{
              Map<String,String> map=new HashMap();
              map.put("CONCAT(SHORT_NAME,NAME_1)",param);
              dbj.executeQueryj("FBNK.CUSTOMER",new String[]{"CUSTOMER_CODE","SHORT_NAME",
                                "NAME_1","LEGAL_ID"},map);
              ResultSet rs=(ResultSet)dbj.readNextLine();
              if(rs!=null){
               clients=new ArrayList();
              while(rs!=null){
                   Client client=new Client();
                   client.setNo(rs.getInt(1));
                   client.setFullName(rs.getString(2).concat(" ").concat(rs.getString(3)));
                   client.setLegalId(rs.getString(4));
                   clients.add(client);
                   rs=(ResultSet)dbj.readNextLine();
               }
              //rs.close();
              }
              dbj.closeConnection();
          }catch(SQLException |DbException db){
              throw new DaoException(db.toString());
          }
          
          return clients;
       }
       @Override
       public Persone searchPersone(String param) throws DaoException{
           Client p=null;
           log.info(param);
           if(param==null || param.isEmpty()){
              throw new DaoException("There are one or mane param(s) null");
           }
           try{
              Map<String,String> map=new HashMap();
              map.put("CONCAT(SHORT_NAME,NAME_1)",param.trim());
              dbj.executeQueryj("FBNK.CUSTOMER",new String[]{"CUSTOMER_CODE","SHORT_NAME",
                                "NAME_1","LEGAL_ID"},map);
              ResultSet rs=(ResultSet)dbj.readNextLine();
              if(rs!=null){
                   p=new Client();
                   p.setNo(rs.getInt(1));
                   p.setFullName(rs.getString(2).concat(" ").concat(rs.getString(3)));
                   p.setLegalId(rs.getString(4));
                   log.info(p.getLegalId().concat("========").concat(p.getFullName()));
              }
              dbj.closeConnection();
          }catch(SQLException |DbException db){
              throw new DaoException(db.toString());
          }
           return p;
       }
       @Override
       public List<CategoryVersion> listCategoriesVersion(Category param) throws DaoException{
           return null;
       }
       /*public static void main(String arg[]){
         try{
            JbDao jb=JbDao.newInstance();
            jb.initConnect();
            List<Persone> list =jb.listPersones("MOISE  JOVENEL".replaceAll(" ",""));
            for(Persone p:list){
                Client c=(Client)p;
                System.out.println(c.getNo()+c.getFullName()+c.getLegalId());
            }
         }catch(DaoException ex){
             System.out.println(ex.toString());
         } 
       }*/
}
