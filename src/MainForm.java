
import ht.buh.db.util.Constant;
import ht.buh.forms.MainFormApplication;
import ht.buh.pojo.Client;
import java.util.List;

/*
 * Ce fichier constitue le point d'entree de l'application servant de pont
 * entre T24 et la la base du Filtrage.
 * L'objectife est de verifier si les personnes qui sont dans le filtrage, sont 
 * des clients de la banque.Si l'appication trouve une personne sur T24 il chcecke
 * les informations de cette personne.Elle ecrit les inforomations relatives
 * a cette personne dans un composante TextArea dans le but de permettre aux
 * utilisateurs de faire un copy past en vue de creer un fichier pour ces personnes
 * Cette application vient d'une demande de Conformite. C'est une application 
 * Desktop. Elle doit etre deployée sur la machine de l'utilisateur.
 * Des le lancement de l'application elle se connecte directement avec le filtrage.
 * Elle utilise MSQLCOnnnecteur pour se connecter ave Microsoft Serveur
 * JDBC Jbase data Connecteur. Elle peut aussi se connecter vie Jremote ett
 * TCClient COnnecteur. 
 *L'application a 2 fonctionnalites princiapales
 * 1.Elle permet de rechercher les personnes filtrees par category.
 * 2.Elle permet de faire la recherche a partir de Legal Id de la personne
 * existant dans le filtrage
 */

/**
 *
 * @author   Ernst Laverdu Fabiem
 * @Date     20200827
 * @Company  Banque de l'Union Haitienne
 * @Version  1.0
 */
public class MainForm {
    
     public static void main(String arg[]){
         MainFormApplication mfp=new MainFormApplication();
         List<Client> clients=Constant.T24Customers.get('b');
         mfp.initComponent();
         
      }
}
