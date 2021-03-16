/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.pojo;

/**
 *
 * @author elaverdu
 */
public class CategoryVersion {
      private Category categ;
      private String lastModify;
      private long id;

    /**
     * @return the categ
     */
    public Category getCateg() {
        return categ;
    }

    /**
     * @param categ the categ to set
     */
    public void setCateg(Category categ) {
        this.categ = categ;
    }

    /**
     * @return the lastModify
     */
    public String getLastModify() {
        return lastModify;
    }

    /**
     * @param lastModify the lastModify to set
     */
    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    @Override
    public String toString(){
       return "[".concat(id+" ").concat(this.categ.getDescription()).concat(" ").concat(lastModify).concat("]");
    }
      
      
      
}
