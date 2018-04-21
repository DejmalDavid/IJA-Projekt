package backend;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Assert;

/**
 *
 * @author David Dejmal xdejma00
 */
public class Test_propoj {
    
    ArrayList<Block> bloky = new ArrayList<Block>();	//list s bloky
    HashSet<Wire> propoje = new HashSet<Wire>();		//set s propoji
    
    //bloky
    Block_plus_jedna jedna = new Block_plus_jedna("cislo 1");	
    Block_plus_jedna dva = new Block_plus_jedna("cislo 2");
    //propoj  
    Wire jedna_dva = new Wire();
    //pomocne promene pro porty
    Port help,help2;
    
    @Before
    public void setUp() {
    	
    	//pridani bloku
   
        bloky.add(jedna);
        bloky.add(dva);

        //propoj
        help=jedna.return_end_port("vystup-1"); 
        help2=dva.return_start_port("vstup-1");
            
        jedna_dva.connect_ports(help,help2);	//propojeni
        	
        propoje.add(jedna_dva);	//pridani do setu
        
    }
    
    /**
     * Bloky jsou v seznamu
     */
    @Test
    public void test01() {
    	Assert.assertTrue("Vyhledani bloku", bloky.contains(jedna));
    	Assert.assertTrue("Vyhledani bloku", bloky.contains(dva));
 
    	
    	Assert.assertEquals(2, bloky.size());
    }
    
    /**
     * Propoje jsou v seznamu
     */
    @Test
    public void test02() {
    	Assert.assertTrue("Vyhledani propoje", propoje.contains(jedna_dva));
  
    	
    	Assert.assertEquals(1, propoje.size());
    }
    
    /**
     *  Operace vramci jednoho bloku
     */
    @Test
    public void test03() {
        help=jedna.return_start_port("vstup-1");	//nalezeni portu
        help.set_hodnota(10);		//nastaveni hodnoty
        
        jedna.operace();	//operace
        
        help=jedna.return_end_port("vystup-1");		//nalezeni portu
        assertEquals( 11.0, help.see_hodnota(),0.1);	
        
    }
    
    /**
     *  Propojeni dvou bloku a propagovani hodnoty
     */
    @Test
    public void test04() {
        help=jedna.return_start_port("vstup-1");	//nalezeni portu
        help.set_hodnota(10);		//nastaveni hodnoty
        
        jedna.operace();	//operace
        
        help=jedna.return_end_port("vystup-1");		//nalezeni portu
        
		 for(Wire propoj : propoje)		//projde vsechny propoje mezi bloky
		 {
			 if(propoj.start()== help)	// vystupni port objektu je pripojen k jinemu objektu
			 {
				help2=propoj.end();
				help2.set_hodnota(help.see_hodnota());	
				//hodnota vystupniho portu je zpropagovana na vstupni port jineho bloku
			 }
		 }
		 
		 dva.operace();
		 
	     help=dva.return_end_port("vystup-1");		//nalezeni portu
         assertEquals( 12.0, help.see_hodnota(),0.1);	//10+1+1=12   
    }
}
