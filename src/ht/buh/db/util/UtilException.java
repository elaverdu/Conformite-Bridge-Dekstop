/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db.util;

/**
 *
 * @author elaverdu
 */
public class UtilException extends Exception {
   public UtilException(String message){
      super(message);
   }
   public UtilException(Throwable throwable){
      super(throwable);
   }
   public UtilException(String message,Throwable throwable){
      super(message,throwable);
   }
}
