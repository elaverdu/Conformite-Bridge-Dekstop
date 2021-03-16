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
public class Category {
    private final static long serialVersionUID=1L;
    private int id;
    private String description;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString(){
       return "[ ".concat(id+"").concat(" ").concat(description).concat(" ]");
    }
    
            
}
