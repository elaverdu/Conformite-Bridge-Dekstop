/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.forms;

import ht.buh.db.util.Constant;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author Ernst Laverdu Fabien
 * @Date   20200921
 * @Entreprise : BUH
 */
public class FSearchBlackPersone extends JPanel implements ActionListener{
     private JButton bSearch;
     private JTextField tParamSearch;
     private JCheckBox cSynchro;
     private JLabel lSearch;
     private JPanel pSearch;//,pResult;
     private GridBagLayout gbl;
     private GridBagConstraints gbc;
     private Component mainComponent;
     private FResultSearchBlackPersone fsb;
     public FSearchBlackPersone(){
         this.setSize(900,400);
         this.initComponent();
     }
     private void initComponent(){
         gbc=new GridBagConstraints();
         gbl=new GridBagLayout();
         this.setLayout(gbl);
         gbl.setConstraints(this,gbc);
         
         pSearch=new JPanel();
         pSearch.setLayout(gbl);
         gbl.setConstraints(pSearch, gbc);
         
         this.lSearch=new JLabel("Full Name");
         this.lSearch.setFont(Constant.FONTTEXT);
         tParamSearch=new JTextField(30);
         bSearch=new JButton("Search");
         this.bSearch.setFont(Constant.FONTTEXT);
         cSynchro=new JCheckBox();
         cSynchro.setText("Synchronizer");
       
         DisposeComponent.add(pSearch,lSearch,gbl,gbc,0,0,1,1,1,1,GridBagConstraints.HORIZONTAL);
         DisposeComponent.add(pSearch,tParamSearch,gbl, gbc, 1,0, 1,1, 1, 1,GridBagConstraints.HORIZONTAL);
         DisposeComponent.add(pSearch, bSearch, gbl, gbc,0,1,1,1,1,1,GridBagConstraints.NONE);
         DisposeComponent.add(this,this.pSearch,gbl,gbc,0,0,1,1,1,1,GridBagConstraints.CENTER);
         bSearch.addActionListener(this);    
     }
     public void setMainComponent(Component mainComponent){
          this.mainComponent=mainComponent;
        }
      
     @Override
     public void actionPerformed(ActionEvent evt){
         Object obj=evt.getSource();
         if(obj.equals(bSearch)){
               if(this.tParamSearch.getText().isEmpty()){
                  JOptionPane.showMessageDialog(this, "The field param is empty", "Conformite-Bridge:Message",JOptionPane.INFORMATION_MESSAGE);
                  return;
               }
              if(fsb!=null)
                   this.remove(fsb);
               fsb=new FResultSearchBlackPersone(tParamSearch.getText().replaceAll(" ",""),true);
               fsb.setMainComponent(mainComponent);
               DisposeComponent.add(this, fsb, gbl, gbc, 0,1,1,1,1,1,GridBagConstraints.HORIZONTAL);
               this.mainComponent.setVisible(true);
               Thread t=new Thread(fsb);
               t.start();
         }
     }
     
     
     
     
}
