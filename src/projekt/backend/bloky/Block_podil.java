package projekt.backend.bloky;

import java.io.Serializable;

import projekt.backend.Block;
import projekt.backend.Port;
import projekt.backend.Port.hodnoty;
/**
 * @author David Dejmal (xdejma00)
 * soucet dvou hodnot stejneho zadaneho typu
 */
public class Block_podil extends Block implements Serializable{

    public Block_podil(String name,int poradi,hodnoty typ) {
        super(name,poradi);

        port_in = new Port[2];	//mnozstvi in/out portu
        port_out = new Port[1];

        //konstruktor kazdeho portu
        port_in[0]= new Port(this,"vstup-1",hodnoty.Metr_2);
        port_in[1]= new Port(this,"vstup-2",hodnoty.Metr);
        port_out[0]= new Port(this,"vystup-1",hodnoty.Metr);
    }

    /** Vypocet blocku
     * rozdil dvou vstupu a propagace na vystupy
     */
    public void operace()
    {
    	if(port_in[1].see_hodnota()==0)
    	{
    		port_out[1].set_hodnota(Double.NaN);
    		return;
    	}
        double result= port_in[0].see_hodnota() / port_in[1].see_hodnota();
        port_out[0].set_hodnota(result);
    }
}