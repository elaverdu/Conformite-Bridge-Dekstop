/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db;

import java.util.Properties;  
import ht.buh.db.util.DbUtil;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import ht.buh.db.util.DbUtilException;
import java.util.logging.Logger;
public class DbjServicesSql extends DbServices {
   public final static Logger log=Logger.getLogger(DbmServicesSql.class.getName());
    @Override
    public void createConnection() throws DbException{
        try{
             Class.forName("com.jbase.jdbc.driver.JBaseJDBCDriver");
            InputStream in=getClass().getClassLoader().getResourceAsStream("resources/application.properties");
            Properties prop=DbUtil.createProperties(in);
            String preurl=(String)prop.get("jb_url");
            String ip=(String)prop.get("jb_ip");
            String source=(String)prop.get("jb_source");
            //String pass=(String)prop.get("jb_pass");
            String port=(String)prop.get("jb_port");
            String url=preurl+ip+":"+port+":"+source;
            log.info(url);
            con=DriverManager.getConnection(url);
            if(con!=null){
                log.info("COnnection open");
            }
        }catch(ClassNotFoundException |SQLException | DbUtilException ex){
            throw new DbException("Exception come from creatiion Connection ".concat(ex.toString()));
        }
    }
    @Override
    public void createConnection(String source,String ip,String user,String password,String agent) throws DbException{   
         try{
               Class.forName("com.jbase.jdbc.driver.JBaseJDBCDriver");
               InputStream in=getClass().getClassLoader().getResourceAsStream("resources\\application.properties");
               
               Properties prop=DbUtil.createProperties(in);
               String preurl=(String)prop.get("jb_url");
               String url=preurl+""+ip+":"+agent+":"+source;  
               con=DriverManager.getConnection(url,user,password);
         }catch(ClassNotFoundException | DbUtilException | SQLException ex){
             throw new DbException("Exception from createConnection ".concat(ex.toString()));
         }
    }
   /* public static void main(String arg[]) {
        try{
            DbjServicesSql db=new DbjServicesSql();
            db.createConnection();
            db.executeQuery("FBNK.CUSTOMER ORDER BY NAME_1",new String[]{"CUSTOMER_NO,SHORT_NAME,NAME_1"});
            java.sql.ResultSet rs=(java.sql.ResultSet)db.readNextLine();
            long i=1;
             while(rs!=null){
                 //System.out.println("Record "+i);
                 log.info(("Row "+i).concat(" ").concat(rs.getString("NAME_1")).concat(" ").concat(rs.getString("SHORT_NAME")));
                 rs=(java.sql.ResultSet)db.readNextLine();
                 i++;
             }
     
        }catch(SQLException |DbException ex){
            log.info(ex.toString());
        }
    }*/
}
