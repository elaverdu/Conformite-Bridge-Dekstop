/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db;
import java.io.InputStream;
import ht.buh.db.util.DbUtilException;
import ht.buh.db.util.DbUtil;
import java.util.Properties;
import com.jbase.jremote.DefaultJConnectionFactory;
import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.jbase.jremote.JConnection;
import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JResultSet;
import com.jbase.jremote.JStatement;
import java.util.Iterator;
import java.util.logging.Logger;
public class DbjServicesJre extends DbServices {
   private final static long serialVersionUID=1L;
   private final static Logger log=Logger.getLogger(DbjServicesJre.class.getName());
   private String ip,user,pass,port,source;
   private JConnection conn;
   private JSubroutineParameters r_1;
   private JStatement jStmt;
   private JResultSet jrs;
   private Iterator jrsit=null;
   @Override 
   public void createConnection()throws DbException{
       try{
           InputStream in=getClass().getClassLoader().getResourceAsStream("resources/application.properties");
            Properties p=DbUtil.createProperties(in);
            this.source=(String)p.get("jb_source");
            this.ip=(String)p.get("jb_ip");
            this.user=(String)p.get("jb_default_user");
            this.pass=(String)p.get("jb_default_pass");
            this.port=(String)p.get("jb_port");
            initConnectionJre(this.source,this.ip,this.user,this.pass,this.port);
       }catch(DbUtilException ex){
           throw new DbException("Exception come from createConnection ".concat(ex.toString()));
       }
   }
   @Override
   public void createConnection(String source,String ip,String user,String pass,String port) throws DbException{
        initConnectionJre(source,ip,user,pass,port);  
   }
  private void initConnectionJre(String source,String ip,String user,String pass,String port) throws DbException{
       this.source=source;
       this.ip=ip;
       this.user=user;
       this.pass=pass;
       this.port=port;
       if(this.source==null || this.source.isEmpty()||
           this.ip==null || this.ip.isEmpty() ||
            this.pass==null || this.pass.isEmpty()||
            this.user==null || this.user.isEmpty()|| 
            this.port==null || this.port.isEmpty()){
              throw new DbException("Exception from createConnection.There are one or many param(s) null or empty");
            }
          try{ 
              DefaultJConnectionFactory dConn=new DefaultJConnectionFactory();
              dConn.setHost(this.ip);
              dConn.setPort(Integer.valueOf(port));
              log.info(dConn.getHost());
              this.conn=dConn.getConnection();
              
              if(this.conn!=null){
                log.info("Connection reussi");
              }
             }catch(JRemoteException jre){
                 throw new DbException("Exception com from createConnection ".concat(jre.toString()));
             }
  }
  @Override
  public void executeQuery(String table,String[] fields) throws DbException{
       if(table.isEmpty() || fields.length==0){
         throw new DbException("Exception come from executeQuery There are one or many param(s) empty or null");
       }
       try{
            JSubroutineParameters rs1=new JSubroutineParameters();
            JDynArray paramOfs=new JDynArray();
            int index=1;
            for(String field:fields){
                paramOfs.insert(field, index);
                index++;
            }
            rs1.add(paramOfs);
            this.r_1=this.conn.call(table, rs1);
            jrsit=this.r_1.iterator();
       }catch(JRemoteException ex){
           throw new DbException("Exception come from executeQuery ".concat(ex.toString()));
        }    
  }
  @Override
  public void executeQuery(String query) throws DbException{
       if(null==query || query.isEmpty()){
          throw new DbException("Exception come from executeQuery param is null or empty");
       }
       try{
           this.jStmt=this.conn.createStatement();
           this.jStmt.setFetchSize(10);
           this.jrs=this.jStmt.execute(query);
       }catch(JRemoteException ex){
           throw new DbException("Exception come from executeQuery ".concat(ex.toString()));
       }
  }
  @Override
  public Object readNextLine() throws DbException{
      Object _rs=null;
      if(null==this.r_1 && null==this.jrs){
          throw new DbException("Exception come from readNextLine Not response from T24 ");
       }
       try{
           if(jrsit!=null){
            _rs=jrsit.hasNext() ? jrsit.next(): null;
            }else if(this.jrs!=null){
              _rs=this.jrs.next() ? this.jrs.getRow() : null;
            }
        } catch(JRemoteException ex){
            throw new DbException("Exception come from readNextLine ".concat(ex.toString()));
         }
       return _rs;
  }
  @Override
  public void closeConnection() throws DbException{
     if(null==this.conn){
       throw new DbException("Can not close the connection because is null");
     }
     try{
       this.conn.close();
     }catch(JRemoteException ex){
      throw new DbException("Exception come from closeConnection ".concat(ex.toString()));
     }
  }
  /*public static void main(String arg[]){
     try{
         DbjServicesJre db=new DbjServicesJre();
         db.createConnection();
         db.executeQuery("LIST FBNK.CUSTOMER WITH LEGAL.ID LIKE ...0025671387... SHORT.NAME NAME.1 LEGAL.ID");
         JDynArray drs=(JDynArray)db.readNextLine();
         //drs.getNumberOfAttributes();
         if(drs!=null){
             log.info(drs.getNumberOfAttributes()+"");
             log.info(drs.get(2).concat(" ").concat(drs.get(3)).concat(" ").concat(drs.get(4)));
         }else{
             log.info("Null");
         }
     }catch(DbException ex){
         log.info(ex.toString());
     }
  }*/

}
