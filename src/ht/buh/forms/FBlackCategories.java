/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.forms;

import ht.buh.dao.DaoException;
import ht.buh.dao.MsDao;
import ht.buh.db.util.Constant;
import ht.buh.pojo.Category;
import ht.buh.pojo.CategoryVersion;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author elaverdu
 */
public class FBlackCategories extends JPanel implements ActionListener,ItemListener {
        private JComboBox<Category> categories;
        private JComboBox<CategoryVersion> categoriesVersion;
        private JCheckBox synchro;
       // private FlowLayout layout;
        private JPanel panelProfiles;
        private JLabel labelProfiles;
        private JButton bSearch;
        private Thread t;
        private Component mainComponent;
        private FBlackPersones fbl;
        private GridBagConstraints gbc;
        private GridBagLayout gbl;
        public FBlackCategories(){
        }
        public void initComponent(){
          this.setBackground(Color.GRAY);
          this.setOpaque(true);
          this.categories=new JComboBox();
          categories.addItemListener(this);
         // this.categories.addItem(null);
          this.categoriesVersion=new JComboBox();
          CategoryVersion categ=new CategoryVersion();
          categ.setId(10022);
          Category cat=new Category();
          cat.setId(7);
          cat.setDescription("AutreListInterne");
          categ.setLastModify("2020-11-12 10:03:21.127");
          categ.setCateg(cat);
          this.categoriesVersion.addItem(categ);
          this.categories.setFont(Constant.FONTTEXT);
          this.synchro=new JCheckBox();
          this.synchro.setText("Synchro With T24");
          this.synchro.setFont(Constant.FONTTEXT);
          this.labelProfiles=new JLabel("Black Categories");
          this.labelProfiles.setFont(Constant.FONTTEXT);
          this.bSearch=new JButton("Search BlackPersones");
          this.bSearch.setFont(Constant.FONTTEXT);
          //layout=new FlowLayout();
          this.gbc=new GridBagConstraints(); 
          this.gbl=new GridBagLayout();
          this.setLayout(gbl);
          gbl.setConstraints(this, gbc);
          
          DisposeComponent.add(this,labelProfiles,gbl,gbc,0,0,1,1,1,1,GridBagConstraints.HORIZONTAL);
          DisposeComponent.add(this,categories,gbl,gbc,1,0,1,1,1,1,GridBagConstraints.HORIZONTAL);
          DisposeComponent.add(this,categoriesVersion,gbl,gbc,2,0,1,1,1,1,GridBagConstraints.HORIZONTAL);
          DisposeComponent.add(this,synchro,gbl,gbc,3,0,1,1,1,1,GridBagConstraints.HORIZONTAL);
          DisposeComponent.add(this,bSearch,gbl,gbc,4,0,1,1,1,1,GridBagConstraints.HORIZONTAL);
          
          categories.addActionListener(this);
          bSearch.addActionListener(this);
        }
        public void addCategroiesToComponent(List<Category> listCategories){
           for(Category categ:listCategories){
               categories.addItem(categ);
           }
           //this.addCategoriesVersionToComponent(listCategories.get(0));
        }
        public void addCategoriesVersionToComponent(Category param){
          if(param!=null){
             this.categoriesVersion.removeAllItems();
            MsDao msdao=MsDao.newInstance();
            try{
              msdao.initConnect();
              List<CategoryVersion> categsVersion=msdao.listCategoriesVersion(param);
              for(CategoryVersion categoryVersion:categsVersion){
                 this.categoriesVersion.addItem(categoryVersion);
              }
             }catch(DaoException ex){
               JOptionPane.showMessageDialog(this, ex.toString());
             }
          }
        }
        public void setComponentPanel(JPanel panelProfiles){
           this.panelProfiles=panelProfiles;
        }
        public void setMainComponent(Component mainComponent){
          this.mainComponent=mainComponent;
        }
      @Override
      public void actionPerformed(ActionEvent evt){
         Object obj=evt.getSource();
        
         if(obj.equals(bSearch)){
             if(fbl!=null){
               this.remove(fbl);
             }
             CategoryVersion categVersion=this.categoriesVersion.getItemAt(this.categoriesVersion.getSelectedIndex());
             
             fbl=new FBlackPersones(categVersion);
             fbl.setBackground(Color.red);
             fbl.synchroWithT24(this.synchro.isSelected());
             DisposeComponent.add(this,fbl,gbl,gbc,0,1,4,1,1,1,GridBagConstraints.BOTH);
             this.mainComponent.repaint();
             this.mainComponent.setVisible(true);
             if(t!=null){
               t=null;
            }
            t=new Thread(fbl);
           t.start();
            //fbl.start();
         }
      }
     @Override
     public void itemStateChanged(ItemEvent evt){
       
       if(evt.getStateChange()==ItemEvent.SELECTED){
           Category categ=this.categories.getItemAt(this.categories.getSelectedIndex());
           this.addCategoriesVersionToComponent(categ);
       }
     }
}
