/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ht.buh.db.util;

import java.awt.Font;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import ht.buh.pojo.Client;

/**
 *
 * @author elaverdu
 */
public class Constant {
    public final static String SQLBLACKLISTENTRIES="select e.remarks,e.birth_date,e.id,n.name,n.normalizedname from ". 
        concat("tBlackListEntries e join tBlackListEntryNames n on ").
		concat("n.entry_id=e.id where e.black_list_id = ? and e.deleted=0");
    //replace(e.remarks,'-','')
    public final static String SQLLISTBLACKPERSONES="select e.remarks,e.birth_date,e.id,n.name,n.normalizedname from ". 
        concat("tBlackListEntries e join tBlackListEntryNames n on ").
		concat("n.entry_id=e.id where replace(n.normalizedname,' ','') like  CONCAT( '%',?,'%')");
    public final static Object HEADERS[]=new Object[]{"Number     ","Customer      ","FullName    ",
                                                      "Birth Date      ","Legal Id         ","Customer Buh          "};
    public final static Font FONTTEXT =new Font("Serif",Font.BOLD,16);
    public final static String SQLLISTVERSIONS="select *from tBlackListVersions where black_list_id = ?";
    public final static char ALPHAS[]={'a','b','c','d','e','f','g','h','i','j','k','l','m',
                                       'n','o','p','q','r','s','t','u','v','w','x','y','z'};
    public static Map<Character,List<Client>> T24Customers=new HashMap();
    public static int DLEV=3;
}
