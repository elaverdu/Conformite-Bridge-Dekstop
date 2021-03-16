/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author elaverdu
 */
public class DisposeComponent {
    
      private static void position(GridBagConstraints gbc,int x,int y,int gw,int gh,int wx,int wy,int fill){
        gbc.gridx=x;
        gbc.gridy=y;
        gbc.weightx=wx;
        gbc.weighty=wy;
        gbc.gridheight=gh;
        gbc.gridwidth=gw;
        gbc.fill=fill;//the comportement for the grid horizontal
        //gbc.anchor=anchor;
    }
    public static void add(JPanel parent,JComponent child,GridBagLayout gbl,GridBagConstraints gbc,int x,int y,int gw,int gh,int wx,int wy,int fill){
         position(gbc,x,y,gw,gh,wx,wy,fill);
         parent.add(child,gbc);
    }
     public static void add(JFrame parent,JComponent child,GridBagLayout gbl,GridBagConstraints gbc,int x,int y,int gw,int gh,int wx,int wy,int fill){
         position(gbc,x,y,gw,gh,wx,wy,fill);
         parent.add(child,gbc);
    }
}
