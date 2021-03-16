/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db.util;

public class DbUtilException extends Exception {
    public DbUtilException(String message){
        super(message);
    }
    public DbUtilException(Throwable throwable){
        super(throwable);
    }
    public DbUtilException(String message,Throwable throwable){
        super(message,throwable);
    }

}