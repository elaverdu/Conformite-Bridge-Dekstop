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
public class DaoUtil {
    
       public static String[] legalIds(String Id){
           return (Id!=null && !Id.isEmpty()) ? Id.split(":") : null;
       }
       public static String replaceHyphenByEmpty(String param){
          return (param!=null && !param.isEmpty()) ? param.replaceAll("-", "") : null;
       }
}
