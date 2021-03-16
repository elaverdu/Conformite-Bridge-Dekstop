/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.beans;


import ht.buh.dao.DaoException;
import ht.buh.dao.MsDao;
import ht.buh.pojo.Category;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author elaverdu
 */
public class BridgeDaoCaBean implements Serializable{
      private final static long serialVersionUID=1L;
      private final static Logger log=Logger.getLogger(BridgeDaoCaBean.class.getName());
      private List<Category> listBlackCategories;
      public BridgeDaoCaBean(){
          if(this.listBlackCategories==null){
           try{ 
               MsDao msDao=MsDao.newInstance();
               msDao.initConnect();
               listBlackCategories=msDao.listCategories();
              }catch(DaoException ex){
                  log.info(ex.toString());
              }
        }
      }
      public List<Category> getListBlackCategories(){
          return this.listBlackCategories;
       }
      
}
