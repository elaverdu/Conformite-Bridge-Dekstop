/*
the goal of this class is create a conection jdb for database and Jbase
Ehe will se DbServicesJabas and DbServicesMS parent's
All query will buill by a uttil's method and each class her proper darts
create by Ernst Laverdu Fabien
Date Create :20200824
Company: Banque De l'Union Haitienne
Projetc :Conformite Bridge
*/
package ht.buh.db;
//import java.sql.Connection;
import java.sql.SQLException; 
//import java.sql.Statement;
//import java.sql.PreparedStatement;
import java.util.logging.Logger;
import java.util.Properties;
//import java.sql.Statement;
//import java.sql.ResultSet;
import java.sql.DriverManager;
import ht.buh.db.util.DbUtil;
import java.io.InputStream;
import ht.buh.db.util.DbUtilException;
public class DbmServicesSql extends DbServices {
        private final static long serialVersionUID=1L;
        public final static Logger log=Logger.getLogger(DbmServicesSql.class.getName());
        @Override 
        public void createConnection() throws DbException{
           try{
                InputStream in=getClass().getClassLoader().getResourceAsStream("resources/application.properties");
                Properties p=DbUtil.createProperties(in);
                String preUrl=(String)p.get("ms_url");
                String user=(String)p.get("ms_user");
                String server=(String)p.get("ms_server");
                String pass=(String)p.get("ms_pass");
                String source=(String)p.get("ms_source");
                String port="1433";
                String url=DbUtil.buildUrlDbS(preUrl, server, port, source, user, pass);
                //log.info(url);
                con=DriverManager.getConnection(url);
               }catch(DbUtilException | SQLException ex){
               throw new DbException("Exception come From Create Connection".concat(ex.toString()));
            } 
        }
        @Override
        public void createConnection(String source,String ip,String user,String pass,String port) throws DbException{   
         if(source==null || source.isEmpty() || ip==null || ip.isEmpty()
            || user==null || user.isEmpty() || pass==null || pass.isEmpty()
            ){
                throw new DbException("There are one or more param(s) null or empty ");     
            }
          try{
                InputStream in=getClass().getClassLoader().getResourceAsStream("application.properties");
                Properties prop=DbUtil.createProperties(in);
                String preUrl=(String)prop.get("ms_url");
                String url=DbUtil.buildUrlDbS(preUrl,ip,port,source,user,pass);
                con=DriverManager.getConnection(url);
            }catch(SQLException | DbUtilException ex){
               throw new DbException("Exception come from createConnection ".concat(ex.toString()));
            }
        }
        /*public static void main(String arg[]){
            try{
                DbmServicesSql db=new DbmServicesSql();
                db.createConnection();
                db.executeQuery("tBlackLists");
                java.sql.ResultSet rs=(java.sql.ResultSet)db.readNextLine();
                if(rs!=null){
                  log.info(rs.getString("display_name"));
                }
            }catch(DbException| SQLException ex){
                log.info(ex.toString());
            }
        }*/
     
}
