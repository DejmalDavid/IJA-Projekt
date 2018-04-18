package projekt.backend;

import java.util.ArrayList;
import java.util.HashSet;


public class Backend {
   public static void main(String[] args) {
      System.out.println("Start:");
      
      ArrayList<Block> bloky = new ArrayList<Block>();
      HashSet<Wire> propoje = new HashSet<Wire>();
      
//Totok dela uzivatel
      
      Block_plus_jedna jedna = new Block_plus_jedna("cislo 1");
      Block_plus_jedna dva = new Block_plus_jedna("cislo 2");
      Block_plus_jedna tri = new Block_plus_jedna("cislo 3");
      
      //nelze mit 2 bloky se stejnym  jmnenem
      for(Block blok : bloky)
      {
    	  if(blok.see_name() == jedna.see_name())
    	  {
    		  //neco moc skaredeho
    		  System.out.println("Stejne jmeno bloku!");
    	  }
      }
      bloky.add(jedna);
      
      
      bloky.add(dva);
      bloky.add(tri);
      
      //-- 1.propoj
      Port help,help2;
      
      help=jedna.return_end_port("vystup-1");
      help2=dva.return_start_port("vstup-1");
      
      Wire jedna_dva = new Wire(); 
      
      jedna_dva.connect_ports(help,help2);
      
      propoje.add(jedna_dva);
      
      //------2.propoj
      
      help=dva.return_end_port("vystup-1");
      help2=tri.return_start_port("vstup-1");
      
      Wire dva_tri = new Wire();
      
      dva_tri.connect_ports(help,help2);
      
      propoje.add(dva_tri);
      
      
//inicializace      
     
      help=jedna.return_start_port("vstup-1");
      help.set_hodnota(10);
      
//Totok se dela samo      
//demo vnitrni smycky
      
      double hodnota;
      
     for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
     {
    	 // TODO pridat pauzu pro krokovani
    	 
    	 objekt.operace();		//nastavi vypoctenou hodnotu na vystupni porty
    	 //System.out.println(objekt.see_name());
    	 for(Port port : objekt.return_end_ports())	//projde vsechny vystupni porty objektu
    	 {
    		 hodnota=port.see_hodnota();
    		 //System.out.println(hodnota);
    		 
    		 for(Wire propoj : propoje)		//projde vsechny propoje mezi bloky
    		 {
    			 if(propoj.start()== port)	// vystupni port objektu je pripojen k jinemu objektu
    			 {
    				help=propoj.end();
    				help.set_hodnota(hodnota);	
    				//hodnota vystupniho portu je zpropagovana na vstupni port jineho bloku
    			 }
    		 }
    		 
    	 }
     }
//konec programu vse se povedlo
     
     help=tri.return_end_port("vystup-1");
      System.out.println(help.see_hodnota());
   }
}
