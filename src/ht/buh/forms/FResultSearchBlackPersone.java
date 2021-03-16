/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.forms;

import ht.buh.dao.DaoException;
import ht.buh.dao.MsDao;
import ht.buh.db.util.Constant;
import ht.buh.db.util.DbUtilException;
import ht.buh.db.util.T24Util;
import ht.buh.pojo.Client;
import ht.buh.pojo.Persone;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elaverdu
 */
public class FResultSearchBlackPersone extends JPanel implements Runnable {
     private Component mainComponent;
     private GridBagLayout gbl;
     private GridBagConstraints gbc;
     private DefaultTableModel dtm;
     private JTable tbl;
     private String paramSearch=null;
     private Boolean cSynchro=false;
     private JButton bSearch;
     private JTextArea tcClient;
     public FResultSearchBlackPersone(String paramSearch,boolean cSynchro){
        this.paramSearch=paramSearch;
        this.cSynchro=cSynchro;
        this.initComponent();
     }
     public void setMainComponent(Component mainComponent){
          this.mainComponent=mainComponent;
        }
     private void initComponent(){
         this.removeAll();
         this.gbc=new GridBagConstraints();
         this.gbl=new GridBagLayout();
         this.setLayout(this.gbl);
         this.bSearch=new JButton("Search On T24");
         this.tcClient=new JTextArea(10,80);
         if(this.cSynchro){
            this.bSearch.setEnabled(false);
          }
         gbl.setConstraints(this,gbc);
          this.dtm=FormsUtil.BridgeDefaultTableModelCustomer(Constant.HEADERS);
         this.tbl=new JTable(this.dtm);
          DisposeComponent.add(this, new JScrollPane(tbl), gbl, gbc, 0, 0, 5, 1, 1, 1, GridBagConstraints.HORIZONTAL);
          DisposeComponent.add(this, new JScrollPane(this.tcClient), gbl, gbc, 0, 1, 5, 1, 1, 1, GridBagConstraints.HORIZONTAL);
          this.repaint();
     }
     @Override 
     public void run(){
           MsDao msDao=MsDao.newInstance();
             try{
                 msDao.initConnect();
                 List<Client> clients;
                 List<Persone> persones=msDao.listPersones(this.paramSearch.trim(), Constant.SQLLISTBLACKPERSONES);
                 if(persones!=null && persones.size()>0){
                      int noRow=0;
                     int custNo=0;
                     if(this.cSynchro){
                         for(Persone p:persones){
                                clients=T24Util.searchPersoneFromTmpDbT24Customers(p);
                                if(clients!=null){
                                    persones.get(noRow).setSelected(Boolean.TRUE);
                                    for(Client client:clients){
                                            String line=Integer.toString(client.getNo()).concat(",")
                                                .concat(client.getFullName()).concat(",")
                                                .concat(client.getLegalId());
                                            this.tcClient.append(line+"\n");
                                    //this.repaint();
                                   }
                               }
                            dtm.insertRow(noRow, new Object[]{noRow+1,custNo,persones.get(noRow).getFullName(),persones.get(noRow).getBirthDate()
                             ,persones.get(noRow).getLegalId(), persones.get(noRow).isSelected()});  
                             noRow++;
                             this.repaint();
                          }     
                      }
                      try{
                         Thread.sleep(500);
                      }catch(InterruptedException ex){
                         
                      }
                 }
             }catch(DaoException | DbUtilException ex){
                JOptionPane.showMessageDialog(this,ex.toString());   
             }
     }
}
