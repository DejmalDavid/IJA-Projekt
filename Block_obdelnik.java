package projekt.backend.bloky;

import java.io.Serializable;

import projekt.backend.Block;
import projekt.backend.Port;
import projekt.backend.Port.hodnoty;

/**
 * @author David Dejmal (xdejma00)
 * vypocet obdelniku
 */
public class Block_obdelnik extends Block implements Serializable{

    public Block_obdelnik(String name,int poradi) {
        super(name,poradi);

        port_in = new Port[2];	//mnozstvi in/out portu
        port_out = new Port[2];

        //konstruktor kazdeho portu
        port_in[0]= new Port(this,"vstup-1",hodnoty.Metr);
        port_in[1]= new Port(this,"vstup-2",hodnoty.Metr);
        port_out[0]= new Port(this,"vystup-1",hodnoty.Metr);
        port_out[1]= new Port(this,"vystup-2",hodnoty.Metr_2);
    }

    /** Vypocet blocku
     * 
     * 1.port obvod obdelniku
     * 2.port obsah obdelniku
     */
    public void operace()
    {
    	double result= port_in[0].see_hodnota()*2+port_in[1].see_hodnota()*2;
            port_out[1].set_hodnota(result);

    	result= port_in[0].see_hodnota()*port_in[1].see_hodnota();
            port_out[0].set_hodnota(result);
    }
}