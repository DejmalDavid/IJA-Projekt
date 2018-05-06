package projekt.backend.bloky;

import java.io.Serializable;

import projekt.backend.Block;
import projekt.backend.Port;
import projekt.backend.Port.hodnoty;

/**
 * 
 * @author David Dejmal
 *
 *vstupni blok
 */
public class Block_start extends Block implements Serializable {
	
	private double hodnota;
	
    public Block_start(String name,int poradi,double vstup,hodnoty typ) {
        super(name,poradi);

        port_in = null;	//mnozstvi in/out portu
        port_out = new Port[1];

        //konstruktor kazdeho portu
        port_out[0]= new Port(this,"vystup-1",typ);
        
        hodnota=vstup;
    }

    /** Vypocet blocku
     *
     *Hodnotu v konstruktoru propaguje na vystup
     */
    public void operace()
    {
        double result= hodnota;
        port_out[0].set_hodnota(result);
    }
}
