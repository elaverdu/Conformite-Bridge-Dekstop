/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.dao;

/**
 *
 * @author elaverdu
 */
public class DaoException extends Exception{ 
     public DaoException(String message){
         super(message);
      }
     public DaoException(Throwable throwable){
        super(throwable);
     }
     public DaoException(String message,Throwable throwable){
       super(message,throwable);
     }
}
