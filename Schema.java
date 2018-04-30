package projekt.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Schema {
      private ArrayList<Block> bloky;
      private HashSet<Wire> propoje ;
      private String jmeno;
      private Integer krok;
      
      public Schema(String jmeno)
      {
           bloky = new ArrayList<Block>();
           propoje = new HashSet<Wire>();
           this.jmeno=jmeno; 
           krok = 0;
      }
      
      public String see_name()
      {
    	  return jmeno;
      }
      
      public void set_name(String name)
      {
    	  jmeno=name;
      }
      
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
      
      public boolean add_start_block(String nazev,double hodnota,hodnoty typ)
      {
          for(Block objekt : bloky)	//projde vsechny bloky v zadanem poradi
          {
        	  if(objekt.see_name() == nazev)	//zadany nazev se nevyskytuje
        	  {
        		  return false;
        	  }
          }
          //je nutna kontrola vstupu hodnota????
    	 Block jedna  = new Block_start(nazev,bloky.size(),hodnota,typ);	         
         bloky.add(jedna);
    	 return true; 
      }
      
      public boolean add_block(String nazev,String typ_bloku,hodnoty typ)
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
    	  	case "soucet" :
    	         jedna = new Block_soucet(nazev,bloky.size(),typ);
    	         break;
    	  	case "rozdil" :
    	  		 jedna = new Block_rozdil(nazev,bloky.size(),typ);
    	  		break;
    	  	//dodelat

    	  	default:
    	  		jedna=null;
    	  		System.out.println("Spatny nazev bloku!!!");
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
      
      public void delete_propoj(String id)
      {
          for(Wire propoj : propoje)	//projde vsechny bloky v zadanem poradi
          {
        	  if(propoj.see_id().equals(id))	//zadane id nalezeno
        	  {
        		  propoje.remove(propoj);
        		  propoj.start().connect=false;
        		  propoj.end().connect=false;
        		  break;
        	  }
          }
      }
      
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
