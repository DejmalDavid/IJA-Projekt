package projekt.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class Schema {
      private ArrayList<Block> bloky;
      private HashSet<Wire> propoje ;
      private String jmeno;
      
      public Schema(String jmeno)
      {
           bloky = new ArrayList<Block>();
           propoje = new HashSet<Wire>();
           // zkontrolovat jmeno jestli je validni s File system???
           this.jmeno=jmeno;        
      }
      
      
      
//Totok dela uzivatel
      public Block find_block(String name)
      {
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
        	  if(objekt.see_name() == name)	//zadany nazev se nevyskytuje
        	  {
        		  return objekt;
        	  }
          }
          return null;
      }
      
      public boolean add_start_block(String nazev,double hodnota)
      {
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
        	  if(objekt.see_name() == nazev)	//zadany nazev se nevyskytuje
        	  {
        		  return false;
        	  }
          }
          //je nutna kontrola vstupu hodnota????
    	 Block jedna  = new Block_start_meter(nazev,bloky.size(),hodnota);	         
         bloky.add(jedna);
    	 return true; 
      }
      
      public boolean add_block(String nazev,int typ_bloku)
      {
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
        	  if(objekt.see_name() == nazev)	//zadany nazev se nevyskytuje
        	  {
        		  return false;
        	  }
          }
          
    	  Block jedna;
    	  switch(typ_bloku)
    	  {
    	  	case 0 :
    	         jedna = new Block_soucet(nazev,bloky.size());
    	         break;
    	  	case 1 :
    	  		jedna = new Block_plus_jedna(nazev,bloky.size());
    	  		break;
    	  	//dodelat
    	  	default:
    	  		jedna=null;
    	  }
         bloky.add(jedna);
    	 return true; 
      }
  
      public boolean add_propoj(String start_block,String zacatek,String end_block,String konec)
      {
    	  Block start,end;
    	  
    	  start=find_block(start_block);
    	  end=find_block(end_block);
	  	  if((start == null) || (end == null))
	  	  {
	  			return false;
	  	  }
          Port help,help2;
          help=start.return_end_port(zacatek);
          help2=end.return_start_port(konec);
          Wire start_end = new Wire(); 
          if( start_end.connect_ports(help,help2) == false)
          {
        	  return false;
          }  
          propoje.add(start_end);
          return true;
      }
      
      //problem s velikosti pole potreba odchytit vyjimku 
      //
      public boolean swap_items(int a,int b)
      {
    	  try {
              Collections.swap(bloky, a, b);
              bloky.get(a).set_poradi(a);
              bloky.get(b).set_poradi(b); 
    	  }
          catch(IndexOutOfBoundsException e)
    	  {
        	  return false;
    	  }
          return true;
      }
      
      public boolean have_cykles()
      {
    	  Port help;
          System.out.println("test smycek");  
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
         	 System.out.println(objekt.see_name()+" : "+ objekt.see_poradi());
         	 for(Port port : objekt.return_end_ports())	//projde vsechny vystupni porty objektu
         	 {     		 
         		 for(Wire propoj : propoje)		//projde vsechny propoje mezi bloky
         		 {
         			 if(propoj.start()== port)	// vystupni port objektu je pripojen k jinemu objektu
         			 {
         				help=propoj.end();
         				if( objekt.see_poradi() >= help.see_parent().see_poradi() )	//musi sedet poradi bloku
         				{
         					 System.out.println("Chyba");
         					 return true;
         				}     			
         			 }
         		 }
         		 
         	 }
          } 
          return false;
      }     

      public boolean full_demo()
      {
    	  Port help;
    	  double hodnota;
    	     for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
    	     {  	 
    	    	 objekt.operace();		//nastavi vypoctenou hodnotu na vystupni porty
    	     	 System.out.println(objekt.see_name()+" : "+ objekt.see_poradi());
    	    	 for(Port port : objekt.return_end_ports())	//projde vsechny vystupni porty objektu
    	    	 {
    	    		 hodnota=port.see_hodnota();
    	    		 if(Double.isNaN(hodnota))	//NAN je spatne a zastavi vypocet
    	    		 {
    	    			 return false;
    	    		 }
    	    		 System.out.println(hodnota);
    	    		 
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
    	     return true;
      }
      
   
}
