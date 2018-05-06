package projekt.backend.bloky;

import java.io.Serializable;

import projekt.backend.Block;
import projekt.backend.Port;
import projekt.backend.Port.hodnoty;

import java.lang.*;
/**
 * @author David Dejmal (xdejma00)
 * vypocet koule
 */
public class Block_koule extends Block implements Serializable{

    public Block_koule(String name,int poradi) {
        super(name,poradi);

        port_in = new Port[1];	//mnozstvi in/out portu
        port_out = new Port[3];

        //konstruktor kazdeho portu
        port_in[0]= new Port(this,"vstup-1",hodnoty.Metr);
        port_out[0]= new Port(this,"vystup-1",hodnoty.Metr);
        port_out[1]= new Port(this,"vystup-2",hodnoty.Metr_2);
        port_out[2]= new Port(this,"vystup-3",hodnoty.Metr_3);
    }

    /** Vypocet blocku
     *
     * 1.port obvod koule
     * 2.port povrch koule
     * 3.port objem koule
     */

	public void operace()
	{
		double result= port_in[0].see_hodnota()*2*Math.PI;
	        port_out[0].set_hodnota(result);
	
		result= Math.pow(port_in[0].see_hodnota(),2)*Math.PI*4;
	        port_out[1].set_hodnota(result);
	
		result= Math.pow(port_in[0].see_hodnota(),2)*Math.PI*4/3;
	        port_out[2].set_hodnota(result);
	}
}