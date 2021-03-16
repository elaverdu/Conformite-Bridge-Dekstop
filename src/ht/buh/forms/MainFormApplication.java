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
import ht.buh.pojo.Category;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author elaverdu
 */
public class MainFormApplication extends JFrame implements ActionListener {
       private JMenuBar menuBar;
       private JMenuItem categItem,searchItem,quitItem;
       private JMenu mMenuItem;
       private FBlackCategories fb=null;
       private FSearchBlackPersone fbp=null;
       private JLabel ltitle;
       private GridBagConstraints gbc;
       private GridBagLayout gbl;
       private JPanel mainPanel;
      public MainFormApplication(){
         try{
            T24Util.buildTmpT24Customers();
           }catch(DbUtilException ex){
               JOptionPane.showMessageDialog(this,"The application Launch out of T24","Message Connection T24",JOptionPane.ERROR_MESSAGE);
           }
      }
      public void initComponent(){
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setBackground(Color.LIGHT_GRAY);
          this.gbc=new GridBagConstraints();
          this.gbl=new GridBagLayout();
          mainPanel=new JPanel();
          mainPanel.setLayout(this.gbl);
         this.gbl.setConstraints(mainPanel, gbc);
          this.menuBar=new JMenuBar();
          this.mMenuItem=new JMenu();
          this.mMenuItem.setText("Conformite Monitor");
          this.mMenuItem.setFont(Constant.FONTTEXT);
          this.categItem=new JMenuItem("Black Categories Filtrage");
          this.categItem.setFont(Constant.FONTTEXT);
          this.categItem.addActionListener(this);
          this.searchItem=new JMenuItem("Search Persone Of Filtrage");
          this.searchItem.setFont(Constant.FONTTEXT);
          this.searchItem.addActionListener(this);
          this.quitItem=new JMenuItem("Exit ");
          this.quitItem.setFont(Constant.FONTTEXT);
          this.quitItem.addActionListener(this);
          this.mMenuItem.add(this.categItem);
          this.mMenuItem.addSeparator();
          this.mMenuItem.add(this.searchItem);
          this.mMenuItem.addSeparator();
          this.mMenuItem.add(this.quitItem);
          this.menuBar.add(this.mMenuItem);
          this.ltitle=new JLabel("BUH Conformite Bridge Between Filtrage T24");
          this.ltitle.setBackground(Color.ORANGE);
          this.ltitle.setForeground(Color.BLUE);
          this.ltitle.setOpaque(true);
          this.ltitle.setFont(new Font("Serif",Font.BOLD,30));
          
          DisposeComponent.add(mainPanel, ltitle, gbl, gbc, 0, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
          FlowLayout flo=new FlowLayout();
          this.setLayout(flo);
          this.add(mainPanel);
          this.setJMenuBar(menuBar);
          Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
          int withScreen=d.width;
          int heightScreen=d.height;
          setSize(withScreen*3/4,heightScreen*3/4);
         
          this.setVisible(true);
      }
      @Override
      public void actionPerformed(ActionEvent evt){
          Object obj=evt.getSource();
          
          if(obj==this.categItem){
              if(fbp!=null){
                 this.mainPanel.remove(fbp);
                 this.fbp=null;
              }
             if(this.fb==null){
             try{ 
                 MsDao ms=MsDao.newInstance();
                 ms.initConnect();
                 List<Category> categories=ms.listCategories();
                 fb=new FBlackCategories();
                 fb.initComponent();
                 fb.setMainComponent(this);
                 fb.addCategroiesToComponent(categories);
                 DisposeComponent.add(mainPanel, fb, gbl, gbc, 0, 1, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
                 this.setVisible(true);
                }catch(DaoException ex){
                   JOptionPane.showMessageDialog(this, ex.toString(),"Message Erreur",JOptionPane.OK_OPTION); 
                }
             }
          }else if(obj==this.searchItem){
              if(fbp==null){
                  if(this.fb!=null){
                     this.mainPanel.remove(this.fb);
                     this.fb=null;
                     this.mainPanel.repaint();
                  }
                  fbp=new FSearchBlackPersone();
                  DisposeComponent.add(mainPanel, fbp, gbl, gbc, 0, 1, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
                  this.setVisible(true);
                  fbp.setMainComponent(this);
              }             
             
          }else if(obj==this.quitItem){
              this.dispose();
              System.exit(0);
          }
      }
     
      
}
