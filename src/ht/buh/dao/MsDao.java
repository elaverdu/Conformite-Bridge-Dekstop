/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.dao;
import java.util.List;
import ht.buh.pojo.Category;
import ht.buh.pojo.Persone;
import ht.buh.db.DbmServicesSql;
import ht.buh.db.DbException;
import ht.buh.db.util.Constant;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.logging.Logger;
import ht.buh.pojo.Profiles;
import java.io.Serializable;
import ht.buh.pojo.CategoryVersion;
//import java.util.HashMap;
//import java.util.Map;
/**
 *
 * @author elaverdu
 */
public class MsDao implements BuhDao,Serializable {
     private DbmServicesSql dbs;
     private boolean synchro;
     private final static Logger log=Logger.getLogger(MsDao.class.getName());
    public static MsDao newInstance(){
       return new MsDao();
    }
    public void synchroWithT24(boolean synchro){
       this.synchro=synchro;
    }
    @Override
    public void initConnect() throws DaoException{
        try{
            dbs=new DbmServicesSql();
            dbs.createConnection();
        }catch(DbException ex){
            throw new DaoException(ex.toString());
        }
    }
    @Override
    public List<Category> listCategories() throws DaoException{
        List<Category> categs=null;
        try{
            dbs.executeQuery("tBlackLists", new String[]{"id","display_name"});
            ResultSet rs=(ResultSet)dbs.readNextLine();
            if(rs!=null){
                categs=new ArrayList();
                while(rs!=null){
                   Category categ=new Category();
                   categ.setDescription(rs.getString("display_name"));
                   categ.setId(rs.getInt("id"));
                   categs.add(categ);
                   rs=(ResultSet)dbs.readNextLine();
                 }
              dbs.closeQuery();
            }
           dbs.closeConnection();
            
        }catch(DbException | SQLException ex){
           throw new DaoException(ex.toString());
        }
        return categs;
    }
    @Override
    public List<CategoryVersion> listCategoriesVersion(Category param) throws DaoException{
          if(param==null){
            throw new DaoException("There one or many param(s) null");
          }
          List<CategoryVersion> categoriesVersion=new ArrayList();
          try{
              dbs.executeQuery(Constant.SQLLISTVERSIONS ,param.getId());
              ResultSet rs=(ResultSet)dbs.readNextLine();
              if(rs!=null){
                   categoriesVersion=new ArrayList();
                  while(rs!=null){
                    CategoryVersion categ=new CategoryVersion();
                    categ.setId(rs.getLong("id"));
                    categ.setLastModify(rs.getDate("last_modif_date").toString());
                    categ.setCateg(param);
                    categoriesVersion.add(categ);
                    rs=(ResultSet)dbs.readNextLine();
                  }
                  //System.out.println(categoriesVersion.size());
                  dbs.closeQuery();
               }
              dbs.closeConnection();
          }catch(DbException | SQLException ex){
            throw new DaoException(ex.toString());
          }
          
          return categoriesVersion.size() > 0 ? categoriesVersion : null;
    }
    @Override
    public List<Persone> listPersones(String param) throws DaoException{
       if(null==param || param.isEmpty()){
          throw new DaoException("The param is empty or null");
       } 
       log.info(param);
       return listPersones(param,Constant.SQLBLACKLISTENTRIES);
    }
    public List<Persone> listPersones(Object param,String querry) throws DaoException{
      List<Persone> profiles=null;
       try{
             if(param instanceof Integer ){
                 dbs.executeQuery(querry, (Integer)param);
               }
             if(param instanceof String){
                 dbs.executeQuery(querry, (String)param);
             }
             ResultSet rs1=(ResultSet)dbs.readNextLine();
             if(rs1!=null){
                 profiles=new ArrayList();
                 while(rs1!=null){
                   Profiles pros=new Profiles();
                   //pros.setSelected(true);
                   pros.setFullName(rs1.getString(5));
                   pros.setLegalId(rs1.getString(1));
                   pros.setBirthDate(rs1.getString(2));
                   profiles.add(pros);
                   rs1=(ResultSet)dbs.readNextLine();
                 }
                 dbs.closeQuery();
             }
             dbs.closeConnection();
         }catch(DbException| SQLException ex){
             throw new DaoException(ex.toString());
         }
       return profiles;
    }
    @Override
    public Persone searchPersone(String param) throws DaoException{
       
        return null;
        
    }
    
   /* public static void main(String arg[]){
        MsDao ms=MsDao.newInstance();
        try{
            ms.initConnect();
          // List<Persone> ps=ms.listPersones("6");
           //if(ps==null){
               //log.info("Pas de donnees");
               ///return;
          // }
           //for(Persone pe : ps){
              //  Profiles p=(Profiles)pe;
              //  log.info(p.getFullName().concat(" ").concat(p.getLegalId()).concat(" ").concat(p.getBirthDate()));
            //}
           List<Category> categs=ms.listCategories();
            for(Category categ:categs){
               log.info(categ.getDescription().concat(String.valueOf(categ.getId())));
            }
            ms.initConnect();
            List<CategoryVersion> versions=ms.listCategoriesVersion(categs.get(0));
            for(CategoryVersion version:versions){
              log.info(version.getId()+" "+version.getLastModify());
            }
           }catch(DaoException ex){
             log.info(ex.toString());
           }
        //db.closeConnection();
    }*/
}
