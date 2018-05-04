package projekt.backend.bloky;

import projekt.backend.Block;
import projekt.backend.Port;
import projekt.hodnoty;

/**
 * @author David Dejmal (xdejma00)
 * soucet dvou hodnot stejneho zadaneho typu
 */
public class Block_soucet extends Block {

    public Block_soucet(String name,int poradi,hodnoty typ) {
        super(name,poradi);

        port_in = new Port[2];	//mnozstvi in/out portu
        port_out = new Port[1];

        //konstruktor kazdeho portu
        port_in[0]= new Port(this,"vstup-1",typ);
        port_in[1]= new Port(this,"vstup-2",typ);
        port_out[0]= new Port(this,"vystup-1",typ);
    }

    /** Vypocet blocku
     * soucet dvou vstupu a propagace na vystupy
     */
    public void operace()
    {
        double result= port_in[0].see_hodnota() + port_in[1].see_hodnota();
        port_out[0].set_hodnota(result);
    }
}