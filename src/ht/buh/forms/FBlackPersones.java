/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.forms;

import ht.buh.dao.DaoException;
//import ht.buh.dao.DaoUtil;
//import ht.buh.dao.JbDao;
import ht.buh.dao.MsDao;
import ht.buh.db.util.Constant;
import ht.buh.db.util.DbUtilException;
import ht.buh.db.util.T24Util;
//import ht.buh.pojo.Category;
import ht.buh.pojo.CategoryVersion;
import ht.buh.pojo.Client;
import ht.buh.pojo.Persone;
//import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elaverdu
 */
public class FBlackPersones extends JPanel implements ActionListener, Runnable {
    private DefaultTableModel dtm;
    private JTable tablePersones;
    //private Object headers[]=new Object[]{"Number","FullName","Birth Date","Legal Id","Customer Buh"};
    private JButton bSearch,bcreateFile;
    private CategoryVersion category=null;
    private boolean synchro;
    private GridBagConstraints gbc;
    private GridBagLayout gbl;
    private JTextArea tClients;
    //private Thread t;
    public FBlackPersones(CategoryVersion category){
        this.category=category;
       // this.setPreferredSize(new Dimension(500,500));
        this.initComponent();
    }
    private void initComponent(){
        dtm=FormsUtil.BridgeDefaultTableModelCustomer(Constant.HEADERS);
        tablePersones=new JTable(dtm);
        gbl=new GridBagLayout();
        gbc=new GridBagConstraints();
        this.setLayout(gbl);
        gbl.setConstraints(this,gbc);
        this.tClients=new JTextArea(10,80);
        
        
        for(int i=0;i<tablePersones.getColumnCount();i++){
          tablePersones.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
         this.bSearch=new JButton("Search On T24");
         this.bSearch.addActionListener(this);
         
         this.bcreateFile=new JButton("Create File BlackPersones");
          this.bcreateFile.setFont(Constant.FONTTEXT);
        DisposeComponent.add(this,new JScrollPane(tablePersones) , gbl, gbc, 0, 0, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
       
        DisposeComponent.add(this,this.bSearch , gbl, gbc, 0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER);
        //DisposeComponent.add(this,this.bcreateFile , gbl, gbc, 1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER);
        DisposeComponent.add(this,new JScrollPane(this.tClients,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS) ,
                             gbl, gbc, 0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER);
    }
    public void synchroWithT24(boolean synchro){
       this.synchro=synchro;
       if(this.synchro){
         this.bSearch.setEnabled(false);
       }
    }
    private void addLisBlackPersonesFromCategoies(){
         this.tClients.setText("");
       try{
           MsDao ms=MsDao.newInstance();
           ms.initConnect();
           List<Persone> persones=ms.listPersones(String.valueOf(category.getId()));
           int noRow=0;
            int noCust=0;
           if(persones!=null){
              while(noRow<persones.size()){
                  noCust=0;
                 if(this.synchro){
                           if(persones.get(noRow).getFullName()!=null && !persones.get(noRow).getFullName().isEmpty()){
                                //String[] legalNos=DaoUtil.legalIds(persones.get(noRow).getLegalId());
                                String full_name=persones.get(noRow).getFullName();
                                //if(legalNos!=null && legalNos.length>1){
                                 if(full_name!=null && full_name.length()>0){
                                   // String legalNo=legalNos[1];
                                   // legalNo=DaoUtil.replaceHyphenByEmpty(legalNo);
                                   // if(legalNo!=null){
                                        //JbDao jbDao=JbDao.newInstance();
                                        //jbDao.initConnect();
                                       // Client p=(Client)jbDao.searchPersone(full_name.replaceAll(" ","").replaceAll("\\.",""));
                                       Persone tmpPersone=persones.get(noRow);
                                       List<Client> clients=T24Util.searchPersoneFromTmpDbT24Customers(tmpPersone);
                                        if(clients!=null){
                                           persones.get(noRow).setSelected(true);
                                            noCust=1;//;p.getNo();
                                            for(Client p:clients){
                                             String line=(String.valueOf(p.getNo())).concat(",").
                                                         concat(String.valueOf(persones.get(noRow).getFullName())).concat(",").
                                                         concat(p.getFullName() != null ? p.getFullName() : "None "  ).concat(",").
                                                         concat(p.getLegalId() !=null ? p.getLegalId() : "None ");
                                                         //concat(p.getBirthDate() !=null ? p.getBirthDate() : "None ").concat(","). 
                                                         
                                            this.tClients.append(line.concat("\n"));
                                            }
                                           }
                                   // }
                               }
                            }
                   }
                this.repaint();
                dtm.insertRow(noRow, new Object[]{noRow+1,noCust,persones.get(noRow).getFullName().replaceAll("\\.",""),persones.get(noRow).getBirthDate()
                             ,persones.get(noRow).getLegalId(), persones.get(noRow).isSelected()});
                noRow++;
               
              }
           }
       }catch(DaoException | DbUtilException ex){
         //JOptionPane.showMessageDialog(this, ex.toString());
           System.out.println(ex.toString());
       }
    }
    private void updLIstBlackPersonesFromCategories(){
         int tot=this.dtm.getRowCount();
         this.tClients.setText("");
        for(int i=0;i<tot;i++){
            boolean stat=(boolean)this.dtm.getValueAt(i, 5);
           // String legalIds=(String)this.dtm.getValueAt(i,4);
            String full_name=(String)this.dtm.getValueAt(i,2);
            
            if(stat){
                   try{
                      //String[] legalNos=DaoUtil.legalIds(legalIds);
                               // if(legalNos!=null && legalNos.length>1){
                                    if(full_name!=null && full_name.length()>1){
                                    //String legalNo=legalNos[1];
                                    //legalNo=DaoUtil.replaceHyphenByEmpty(legalNo);
                                    //if(legalNo!=null)
                                    {
                                        //JbDao jbDao=JbDao.newInstance();
                                       // jbDao.initConnect();
                                        Persone tmpPersone=new Persone();
                                        tmpPersone.setFullName(full_name);
                                        //Client p=(Client)jbDao.searchPersone(full_name.replaceAll(" ","").replaceAll("\\.",""));
                                        List<Client> clients=T24Util.searchPersoneFromTmpDbT24Customers(tmpPersone);
                                        if(clients==null){
                                            this.dtm.setValueAt(false, i, 5);
                                           }else{
                                            for(Client p:clients){
                                            this.dtm.setValueAt(clients.size(), i, 1);
                                            
                                            String line=(String.valueOf(p.getNo())).concat(",").
                                                         concat(String.valueOf(this.dtm.getValueAt(i, 2))).concat(",").
                                                         concat(String.valueOf(p.getFullName())).concat(","). 
                                                         concat(String.valueOf(p.getLegalId()));
                                            this.tClients.append(line.concat("\n"));
                                            
                                           }}
                                    }
                               }
                   }catch(DbUtilException ex){
                     System.out.println(ex.toString());
                   }         
            }
            
        }
        
    }
    @Override
    public void actionPerformed(ActionEvent evt){
       Object obj=evt.getSource();
       if(obj==this.bSearch){
       new Thread( () -> {
           updLIstBlackPersonesFromCategories();
       }).start();
       }
    }
    @Override
    public void run() {
       addLisBlackPersonesFromCategoies();
    }
    
         
    
}
