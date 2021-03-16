/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.dao;
import java.util.List;
import ht.buh.pojo.Category;
import ht.buh.pojo.Persone;
import ht.buh.pojo.CategoryVersion;
/**
 *
 * @author elaverdu@buh.ht
 */
public interface BuhDao {
      List<Category> listCategories() throws DaoException;
      List<CategoryVersion> listCategoriesVersion(Category id) throws DaoException;
      List<Persone> listPersones(String param) throws DaoException;
      Persone searchPersone(String param) throws DaoException;
      void initConnect() throws DaoException;
}
