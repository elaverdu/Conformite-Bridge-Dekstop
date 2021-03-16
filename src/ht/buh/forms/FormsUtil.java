/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.forms;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elaverdu
 */
public class FormsUtil {
    
      public static DefaultTableModel BridgeDefaultTableModelCustomer(Object[] headers){
          DefaultTableModel dtm=new DefaultTableModel(){
            public Class<?> getColumnClass(int column)
             {
                switch(column)
                 {
                   case 0:
                     return String.class;
                   case 1:
                     return String.class;
                   case 2:
                     return String.class;
                   case 3:
                     return String.class;
                   case 4:
                     return String.class;
                   case 5:
                     return Boolean.class;

                   default:
                     return String.class;
        }
      }
          };
          for(Object header:headers){  
            dtm.addColumn(header);
         }
          return dtm;
      }
}
