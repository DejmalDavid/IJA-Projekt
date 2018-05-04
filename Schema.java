package projekt.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import projekt.backend.Port.hodnoty;
import projekt.backend.bloky.*;

/**Schema obsahujici vsechny bloky a propoje mezi nimi
 * 
 * @author David Dejmal xdejma00
 *
 */
public class Schema implements Serializable{
	private ArrayList<Block> bloky;	//seznam bloku
      private HashSet<Wire> propoje ;	//mnozina propoju
      private String jmeno;	//jmeno bloku
      private Integer krok;	//krok vypoctu (index seznamu bloku)
      

      public Schema(String jmeno)
      {
           bloky = new ArrayList<Block>();
           propoje = new HashSet<Wire>();
           this.jmeno=jmeno; 
           krok = 0;
      }
      
      /**Jmeno schematu
       * 
       * @return jmeno schematu
       */
      public String see_name()
      {
    	  return jmeno;
      }
      
      /**Nastavi jmeno schematu
       * 
       * @param name
       */
      public void set_name(String name)
      {
    	  jmeno=name;
      }
      
      /**Vyhleda blok
       * 
       * @param name - jmeno bloku
       * @return blok se zadanym nazvem
       */
      public Block find_block(String name)
      {
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
        	  if(objekt.see_name().equals(name))	//zadany nazev se nevyskytuje
        	  {
        		  return objekt;
        	  }
          }
          return null;
      }
      
      /**Vyhleda propoj
       * 
       * @param name - jmeno propoje
       * @return vraci hledany propoj
       */
      public Wire find_wire(String name)
      {
          for(Wire spoj : propoje)	//projde vsechny bloky v zadanem poradi
          {
        	  if(spoj.see_name().equals(name))	//zadany nazev se nevyskytuje
        	  {
        		  return spoj;
        	  }
          }
          return null;
      }
      
      /**Prida vstupni blok
       * 
       * @param nazev - jmeno
       * @param hodnota	- vystupni hodnota
       * @param typ - typ promene (metr,metr_)
       * @return false - jmeno bloku je jiz ve schematu
       */
      public boolean add_start_block(String nazev,double hodnota,hodnoty typ)
      {
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
        	  if(objekt.see_name().equals(nazev))	//zadany nazev se nevyskytuje
        	  {
        		  return false;
        	  }
          }
          //je nutna kontrola vstupu hodnota????
    	 Block jedna  = new Block_start(nazev,bloky.size(),hodnota,typ);	         
         bloky.add(jedna);
    	 return true; 
      }
      
      /**Prida blok s operacei
       * 
       * @param nazev - unikatni jmeno bloku
       * @param typ_bloku - jmeno bloku
       * @param typ	- typ pro aritmeticke bloky
       * @return  false - jmeno bloku je jiz ve schematu
       */
      public boolean add_block(String nazev,String typ_bloku,hodnoty typ)
      {
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
        	  if(objekt.see_name().equals(nazev))	//zadany nazev se nevyskytuje
        	  {
        		  return false;
        	  }
          }
          
    	  Block jedna;
    	  switch(typ_bloku)
    	  {
    	  	case "soucet" :
    	         jedna = new Block_soucet(nazev,bloky.size(),typ);
    	         break;
    	  	case "rozdil" :
    	  		 jedna = new Block_rozdil(nazev,bloky.size(),typ);
    	  		break;
    	  	case "soucin" :
   	  		 jedna = new Block_rozdil(nazev,bloky.size(),typ);
   	  		break;

    	  	default:
    	  		jedna=null;
    	  		System.out.println("Spatny nazev bloku!!!");
    	  }
         bloky.add(jedna);
    	 return true; 
      }
  
      /**Popoji dva porty mezi sebou
       * 
       * @param start_block	- jmeno start bloku
       * @param zacatek	- jmeno pocatecniho portu
       * @param end_block	- jmeno koncoveho bloku
       * @param konec - jmeno koncoveho portu
       * @return false - spatne parametry,spatne porty,porty nejdou propojit
       */
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
	  	  if((help == null) || (help2 == null))
	  	  {
	  			return false;
	  	  }
          Wire start_end = new Wire(); 
          if( start_end.connect_ports(help,help2) == false)
          {
        	  return false;
          }  
          propoje.add(start_end);
          return true;
      }
      
      /**Smaze blok a vsechny jeho propoje
       * 
       * @param nazev - jmeno bloku
       */
      public void delete_blok(String nazev)
      {
    	  label_1:
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
        	  if(objekt.see_name().equals(nazev))	//zadane id nalezeno
        	  {
        		  bloky.remove(objekt);
        		  
        		 Port[] help;
              	 System.out.println(objekt.see_name()+" : "+ objekt.see_poradi());
              	 help = objekt.return_end_ports();         	 
             	 if(help != null)
             	 {
    	         	 for(Port port : objekt.return_end_ports())	//projde vsechny vystupni porty objektu
    	         	 {     		 
    	                 for(Wire propoj : propoje)	//projde vsechny bloky v zadanem poradi
    	                 {
	    	               	  if(propoj.start() == port)	//zadane id nalezeno
	    	               	  {
	    	               		  propoje.remove(propoj);
	    	               		  propoj.start().connect=false;
	    	               		  propoj.end().connect=false;
	    	               		  break;
	    	               	  }
    	                 }
    	         	 }
             	 }
             	 help= objekt.return_start_ports();
             	 if(help != null)
             	 {
    	         	 for(Port port : objekt.return_start_ports())	//projde vsechny vystupni porty objektu
    	         	 {     		 
    	                 for(Wire propoj : propoje)	//projde vsechny bloky v zadanem poradi
    	                 {
	    	               	  if(propoj.end() == port)	//zadane id nalezeno
	    	               	  {
	    	               		  propoje.remove(propoj);
	    	               		  propoj.start().connect=false;
	    	               		  propoj.end().connect=false;
	    	               		  break;
	    	               	  }
    	                 }
    	         	 }
             	 }
  
        		break label_1;
        	  }
          }
      }
      
      /**Smaze propoj
       * 
       * @param jmeno - jmeno propoje
       */
      public void delete_propoj(String jmeno)
      {
          for(Wire propoj : propoje)	//projde vsechny bloky v zadanem poradi
          {
        	  if(propoj.see_name().equals(jmeno))	//zadane id nalezeno
        	  {
        		  propoje.remove(propoj);
        		  propoj.start().connect=false;
        		  propoj.end().connect=false;
        		  break;
        	  }
          }
      }
      
      /**Prohodi poradi bloku
       * 
       * @param a poradi 1. bloku
       * @param b poradi 2. bloku
       * @return false spatne indexy
       */
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
      
      /**Kontroluje smycky v schmeatu
       * 
       * BLoky jsou zpracovany dle jejich poradi a pokud je blok pripojen k bloku
       * vyssim poradim doslo by ve vypoctu k chybe.
       * Take kontroluje zda blok neni zapojen sam do sebe. 
       * 
       * @return false schema obsahuje smycky
       */
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

      /**Provede jeden krok vypoctu
       * 
       * @return vypocet daneho kroku se nezdaril (Nan)
       */
      public boolean step_demo()
      {
    	  Port help;
    	  double hodnota;
    	  Block objekt = bloky.get(krok);
    	  krok=krok+1;
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
    	      
    	     return true;
      }
      
      /**Provede celkovy vypocet
       * 
       * @return false pokud pri vypoctu doslo k chybe (Nan)
       */
      public boolean full_demo()
      {
    	  Port help;
    	  double hodnota;
    	  krok=0;
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
    	    	 krok=krok+1;
    	     } 
    	     return true;
      }
        
      /**Nastavi hodnotu vsech portu na puvodni hodnoty (zadane hodnoty nebo Nan)
       * 
       */
      public void start_mode()
      {
          System.out.println("start mode");  
          Port[] help;
          krok=0;
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
         	 System.out.println(objekt.see_name()+" : "+ objekt.see_poradi());
          	 help = objekt.return_end_ports();         	 
         	 if(help != null)
         	 {
	         	 for(Port port : objekt.return_end_ports())	//projde vsechny vystupni porty objektu
	         	 {     		 
	         		 port.set_hodnota(Double.NaN);
	         	 }
         	 }
         	 help= objekt.return_start_ports();
        	 if(help != null)
        	 {
	         	 for(Port port : objekt.return_start_ports())	//projde vsechny vystupni porty objektu
	         	 {     		 
	         		 port.set_hodnota(Double.NaN);
	         	 }
        	 }
          } 
      }
      
      /**
       * Pomocny vypis stavu schematu do konzole.
       */
      public void help_vypis()
      {
          System.out.println("help vypis");  
          Port[] help;
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
         	 System.out.println(objekt.see_name()+" : "+ objekt.see_poradi());
         	 System.out.print("Vstupni:");
         	 help= objekt.return_start_ports();
         	 if(help != null)
         	 {
	         	 for(Port port : help)	//projde vsechny vystupni porty objektu
	         	 {     		 
	         		System.out.print(port.see_hodnota()+"; ");
	         	 }
         	 }
         	System.out.print("\n"+"Vystupni:");
         	help = objekt.return_end_ports();
        	 if(help != null)
        	 {
	         	 for(Port port : help )	//projde vsechny vystupni porty objektu
	         	 {     		 
	         		System.out.print(port.see_hodnota()+"; ");
	         	 }
        	 }
        	 System.out.print("\n");
          } 
      }
   
      /**Kontroluje zda jsou vsechny vstupni porty pripojeny
       * 
       * @return prvni blok ktery neni pripojen jinak null
       */
      public Block all_connect()
      {
          System.out.println("all connect");  
          Port[] help;
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
         	 help= objekt.return_start_ports();
        	 if(help != null)
        	 {
	         	 for(Port port : objekt.return_start_ports())	//projde vsechny vystupni porty objektu
	         	 {     		 
	         		 if(port.connect==false)
	         		 {
	         			System.out.println("nepripojeny blok:"+objekt.see_name());
	         			 return objekt;
	         		 }
	         	 }
        	 }
          } 
    	  return null;
      }

}
