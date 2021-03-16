/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db;

/**
 *
 * @author elaverdu
 */
public class DbException extends Exception {
    public DbException(String message){
        super(message);
    }
    public DbException(Throwable throwable){
        super(throwable);
    }
    public DbException(String message,Throwable throwable){
        super(message,throwable);
    }
}
